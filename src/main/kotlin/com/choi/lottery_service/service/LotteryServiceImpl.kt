package com.choi.lottery_service.service

import com.choi.lottery_service.dao.IssuedLotteryRepository
import com.choi.lottery_service.dto.LotteryInfoDto
import com.choi.lottery_service.dto.WonLotteryNumbersWithRankDto
import com.choi.lottery_service.entity.IssuedLottery
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URL

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
    override fun save(issuedLottery: IssuedLottery) {
        lotteryRepository.save(issuedLottery)
    }

    fun getWonLotteryInfo(drwNo: Int): List<IssuedLottery>? {
        val wonLotteryInfo = getLotteryInfoByRound(drwNo)
        val lotteryTicketsByRound: List<IssuedLottery> = lotteryRepository.findByDrwNo(drwNo)

        var wonLotteryTickets = mutableListOf<IssuedLottery>()

        lotteryTicketsByRound.forEach {
            checkNum(wonLotteryInfo, it)
        }

        return wonLotteryTickets
    }

    private fun checkNum(wonLotteryInfo: LotteryInfoDto, lottery: IssuedLottery) {

        var wonLotteryNumbers = mutableListOf<WonLotteryNumbersWithRankDto>()

        val check1 = checkOneNumbers(wonLotteryInfo, lottery.id ,lottery.lotteryNumbers1)
        if(check1 != null) wonLotteryNumbers.add(check1)

        //메소드 개선필

    }

    private fun checkOneNumbers(
        wonLotteryInfo: LotteryInfoDto,
        lotteryId: Long,
        lotteryNumbers1: Collection<Int>
    ) : WonLotteryNumbersWithRankDto?{

        var count: Int = 0

        if(wonLotteryInfo.drwtNo1 == lotteryNumbers1.elementAt(0)) count++

        if(wonLotteryInfo.drwtNo2 == lotteryNumbers1.elementAt(1)) count++

        if(wonLotteryInfo.drwtNo3 == lotteryNumbers1.elementAt(2)) count++

        if(wonLotteryInfo.drwtNo4 == lotteryNumbers1.elementAt(3)) count++

        if(wonLotteryInfo.drwtNo5 == lotteryNumbers1.elementAt(4)) count++

        if(wonLotteryInfo.drwtNo6 == lotteryNumbers1.elementAt(5)) count++

        var rank: Int

        when(count){
            6 -> rank = 1
            5 -> rank = 3
            4 -> rank = 4
            3 -> rank = 5
            else -> rank = 0
        }

        if(rank != 0) return WonLotteryNumbersWithRankDto(lotteryId, lotteryNumbers1, rank)

        return null
    }


    fun getLotteryInfoByRound(round: Int): LotteryInfoDto {
        var lottoUrl = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=$round"
        val requestInfo = URL(lottoUrl).readText()
        return jacksonObjectMapper().readValue(requestInfo, LotteryInfoDto::class.java)
    }

    fun getLastRound(): Int{
        var url = "https://m.dhlottery.co.kr/gameResult.do?method=byWin"
        val doc = Jsoup.connect(url).get()
        return doc.select(".numberSelect > option:first-child").`val`().toInt()
    }


}