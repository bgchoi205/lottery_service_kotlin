package com.choi.lottery_service.dto

import java.time.LocalDate

data class LotteryInfoDto(
    val totSellamnt: Long, // 총 판매금액
    var returnValue: String, // 데이터 수신 성공여부
    val drwNoDate: String, // 발표날짜
    val firstWinamnt: Long, // 1등 당첨금
    val firstPrzwnerCo: Int, // 1등 당첨자 수
    val firstAccumamnt: Long, // 총 당첨금
    val drwNo: Int, // 회차
    val drwtNo1: Int, // 1번째 ~ 6번째 당첨번호
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int // 보너스 번호
)
