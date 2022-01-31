package com.choi.lottery_service.service

import com.choi.lottery_service.dao.IssuedLotteryRepository
import com.choi.lottery_service.dto.LotteryInfoDto
import com.choi.lottery_service.dto.LotterySaveDto
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

    fun getWonLotteryInfo(drwNo: Int): List<IssuedLottery> {
        val wonLotteryInfo = getLotteryInfoByRound(drwNo)
        val findLotteryTickets: List<IssuedLottery> = lotteryRepository.findByDrwNo(drwNo)

        var wonLotteryTickets = mutableListOf<IssuedLottery>()

        findLotteryTickets.forEach {
            if(it.getNum1() == wonLotteryInfo.drwtNo1
                && it.getNum2() == wonLotteryInfo.drwtNo2
                && it.getNum3() == wonLotteryInfo.drwtNo3
                && it.getNum4() == wonLotteryInfo.drwtNo4
                && it.getNum5() == wonLotteryInfo.drwtNo5
                && it.getNum6() == wonLotteryInfo.drwtNo6){
                wonLotteryTickets.add(it)
            }
        }
        return wonLotteryTickets
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