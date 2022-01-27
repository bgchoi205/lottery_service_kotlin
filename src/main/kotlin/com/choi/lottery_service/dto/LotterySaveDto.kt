package com.choi.lottery_service.dto

import javax.persistence.Embeddable

@Embeddable
data class LotterySaveDto(
    val num1: Int,
    val num2: Int,
    val num3: Int,
    val num4: Int,
    val num5: Int,
    val num6: Int
)