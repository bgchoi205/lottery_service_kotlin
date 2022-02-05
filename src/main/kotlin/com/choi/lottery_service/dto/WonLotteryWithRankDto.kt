package com.choi.lottery_service.dto

import com.choi.lottery_service.entity.IssuedLottery

data class WonLotteryWithRankDto (
    val lottery: IssuedLottery,
    val rank: Int
)

