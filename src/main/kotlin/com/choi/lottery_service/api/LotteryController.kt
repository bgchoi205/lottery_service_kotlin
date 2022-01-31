package com.choi.lottery_service.api

import com.choi.lottery_service.dto.LotteryInfoDto
import com.choi.lottery_service.global.response.ResDto
import com.choi.lottery_service.service.LotteryServiceImpl
import com.fasterxml.jackson.databind.util.JSONPObject
import org.jsoup.Jsoup
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class LotteryController(private val lotteryService: LotteryServiceImpl) {

    @GetMapping
    fun showIndex():String{
        val round = lotteryService.getLastRound()

        return "회차 : $round"
    }

    // 회차 입력에 따라 당첨 정보 가져오기
    @GetMapping("/{round}")
    fun getWinningNumber(@PathVariable round: Int): ResDto{

        return ResDto(lotteryService.getLotteryInfoByRound(round))
    }

    @GetMapping("/won/{round}")
    fun getWonLotteryTickets(@PathVariable round: Int): ResDto{

        return ResDto(
            lotteryService.getWonLotteryInfo(round)
        )
    }

}