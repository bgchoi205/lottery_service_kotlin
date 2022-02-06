package com.choi.lottery_service.entity

import com.choi.lottery_service.dto.LotterySaveDto
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class IssuedLottery(
    @Id
    @GeneratedValue
    var id: Long? = null,
    val drwNo: Int,
    val issuedDate: LocalDateTime,

    @ElementCollection
    val lotteryNumbers1: Collection<Int>,

    @ElementCollection
    val lotteryNumbers2: Collection<Int>?,

    @ElementCollection
    val lotteryNumbers3: Collection<Int>?,

    @ElementCollection
    val lotteryNumbers4: Collection<Int>?,

    @ElementCollection
    val lotteryNumbers5: Collection<Int>?

//    val num1: Int,
//    val num2: Int,
//    val num3: Int,
//    val num4: Int,
//    val num5: Int,
//    val num6: Int

)