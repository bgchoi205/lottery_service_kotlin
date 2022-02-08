package com.choi.lottery_service.service

import com.choi.lottery_service.dao.IssuedLotteryRepository
import com.choi.lottery_service.dto.LotteryInfoDto
import com.choi.lottery_service.dto.LotterySaveDto
import com.choi.lottery_service.dto.WonLotteryNumbersWithRankDto
import com.choi.lottery_service.entity.IssuedLottery
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URL
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class LotteryServiceImpl(private val lotteryRepository: IssuedLotteryRepository): LotteryService {

    override fun findById(id: Long): IssuedLottery {
        return lotteryRepository.findById(id).orElseThrow()
    }

    override fun findAll(): List<IssuedLottery> {
        return lotteryRepository.findAll()
    }

    @Transactional
    override fun save(lotterySaveDto: LotterySaveDto) {

        val lottery = IssuedLottery(
            null,
            1001,
            LocalDateTime.now(),
            lotterySaveDto.nums1,
            lotterySaveDto.nums2,
            lotterySaveDto.nums3,
            lotterySaveDto.nums4,
            lotterySaveDto.nums5
        )

        lotteryRepository.save(lottery)
    }

    // 저장된 복권 중 해당 회차의 당첨 정보 불러오기
    fun getWonLotteryInfo(drwNo: Int): List<WonLotteryNumbersWithRankDto>? {

        val wonLotteryInfo = getLotteryInfoByRound(drwNo)

        val wonLotteryNumbers = listOf(
            wonLotteryInfo.drwtNo1,
            wonLotteryInfo.drwtNo2,
            wonLotteryInfo.drwtNo3,
            wonLotteryInfo.drwtNo4,
            wonLotteryInfo.drwtNo5,
            wonLotteryInfo.drwtNo6)

        val bonus = wonLotteryInfo.bnusNo

        val lotteryTicketsByRound: List<IssuedLottery> = lotteryRepository.findByDrwNo(drwNo)

        var wonLotteryTickets = mutableListOf<WonLotteryNumbersWithRankDto>()

        lotteryTicketsByRound.forEach { it ->
            checkNum(wonLotteryNumbers, bonus, it)?.let{wonLotteryTickets.addAll(it)}
        }

        return wonLotteryTickets
    }

    private fun checkNum(wonLotteryNumbers: List<Int>, bonus: Int, lottery: IssuedLottery): List<WonLotteryNumbersWithRankDto> {

        var wonLotteryInfos = mutableListOf<WonLotteryNumbersWithRankDto>()
        
        checkOneNumbers(wonLotteryNumbers, bonus, lottery.id ,lottery.lotteryNumbers1)?.let{wonLotteryInfos.add(it)}
        lottery.lotteryNumbers2?.let { checkOneNumbers(wonLotteryNumbers, bonus, lottery.id , it) }?.let{wonLotteryInfos.add(it)}
        lottery.lotteryNumbers3?.let { checkOneNumbers(wonLotteryNumbers, bonus, lottery.id , it) }?.let{wonLotteryInfos.add(it)}
        lottery.lotteryNumbers4?.let { checkOneNumbers(wonLotteryNumbers, bonus, lottery.id , it) }?.let{wonLotteryInfos.add(it)}
        lottery.lotteryNumbers5?.let { checkOneNumbers(wonLotteryNumbers, bonus, lottery.id , it) }?.let{wonLotteryInfos.add(it)}

        return wonLotteryInfos
    }

    private fun checkOneNumbers(
        wonLotteryNumbers: List<Int>,
        bonus: Int,
        lotteryId: Long?,
        lotteryNumbers: Collection<Int>
    ) : WonLotteryNumbersWithRankDto?{

        var count: Int = 0

        for (lotteryNumber in lotteryNumbers) {
            if(wonLotteryNumbers.contains(lotteryNumber)) count++
        }

        var rank = 0

        when(count){
            6 -> rank = 1
            5 -> rank = if(lotteryNumbers.contains(bonus)){
                2
            }else{
                3
            }
            4 -> rank = 4
            3 -> rank = 5
        }

        if(rank != 0) return WonLotteryNumbersWithRankDto(lotteryId, lotteryNumbers, rank)

        return null
    }

    // 복권 번호 추첨(다다)
    fun drawNumbers(){

        var collectedNumbers = mutableListOf<Int>()

        val lastRound = getLastRound()

        for(i in 0..9){
            collectedNumbers.addAll(getOnlyWonLotteryNumbers(lastRound + i))
        }

        var sortedNumbers = collectedNumbers.sorted()

        println("sortedNumbers : $sortedNumbers")

        var numMap = mutableMapOf<Int, Int>()

        var sortedSize: Int

        while(true){

            sortedSize = sortedNumbers.size

            if(sortedSize == 0){
                break
            }else{
                var size = 0
                for(num in sortedNumbers){
                    if(num == sortedNumbers[0]){
                        size++
                    }else{
                        continue
                    }
                }

                numMap.put(sortedNumbers[0], size)

                sortedNumbers = sortedNumbers.drop(size)
                println("처리중 : $sortedNumbers")
                println("sortedSize : $sortedSize")
                println("numMap : $numMap")
            }

        }

        println("lastNumMap : $numMap")

    }

    // 해당 회차 정보에서 당첨숫자들만 뽑아서 리턴
    fun getOnlyWonLotteryNumbers(round: Int): List<Int>{
        val wonLotteryInfo = getLotteryInfoByRound(round)

        return listOf(
            wonLotteryInfo.drwtNo1,
            wonLotteryInfo.drwtNo2,
            wonLotteryInfo.drwtNo3,
            wonLotteryInfo.drwtNo4,
            wonLotteryInfo.drwtNo5,
            wonLotteryInfo.drwtNo6)
    }

    // 입력 회차의 당첨 정보 불러오기
    fun getLotteryInfoByRound(round: Int): LotteryInfoDto {
        var lottoUrl = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=$round"
        val requestInfo = URL(lottoUrl).readText()
        return jacksonObjectMapper().readValue(requestInfo, LotteryInfoDto::class.java)
    }

    // 마지막 회차 불러오기
    fun getLastRound(): Int{
        var url = "https://m.dhlottery.co.kr/gameResult.do?method=byWin"
        val doc = Jsoup.connect(url).get()
        return doc.select(".numberSelect > option:first-child").`val`().toInt()
    }


}