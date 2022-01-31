package com.choi.lottery_service.dto

data class ResWonLotteryInfo(
    val drwNoDate: String, // 발표날짜
    val firstWinamnt: Long, // 1등 당첨금
    val firstPrzwnerCo: Int, // 1등 당첨자 수
    val drwNo: Int, // 회차
    val drwtNo1: Int, // 1번째 ~ 6번째 당첨번호
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int // 보너스 번호
)
