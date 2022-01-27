package com.choi.lottery_service.entity

import com.choi.lottery_service.dto.LotterySaveDto
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class IssuedLottery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val round: Int,
    private val issuedDate: LocalDateTime,

    @ElementCollection
    private val lotteryNumbers: Collection<LotterySaveDto>
    )