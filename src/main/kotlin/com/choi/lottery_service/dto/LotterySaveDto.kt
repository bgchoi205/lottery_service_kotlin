package com.choi.lottery_service.dto

data class LotterySaveDto(
    val nums1: Collection<Int>,
    val nums2: Collection<Int>?,
    val nums3: Collection<Int>?,
    val nums4: Collection<Int>?,
    val nums5: Collection<Int>?
)