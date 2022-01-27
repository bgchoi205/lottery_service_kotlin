package com.choi.lottery_service.api

import com.choi.lottery_service.service.LotteryServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LotteryController(private val lotteryService: LotteryServiceImpl) {

    @GetMapping
    fun showIndex():String{
        return "Lottery Service!!"
    }



}