package com.choi.lottery_service.api

import com.choi.lottery_service.global.response.RespDto
import com.choi.lottery_service.service.LotteryServiceImpl
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
    fun getWinningNumber(@PathVariable round: Int): RespDto{

        return RespDto(lotteryService.getLotteryInfoByRound(round))
    }

    // 해당 회차 당첨번호 가져오기
    @GetMapping("/won/{round}")
    fun getWonLotteryTickets(@PathVariable round: Int): RespDto{

        return RespDto(
            lotteryService.getWonLotteryInfo(round)
        )
    }

}