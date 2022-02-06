package com.choi.lottery_service.dto

data class WonLotteryNumbersWithRankDto (
    val lotteryId: Long?,
    val numbers: Collection<Int>,
    val rank: Int
)