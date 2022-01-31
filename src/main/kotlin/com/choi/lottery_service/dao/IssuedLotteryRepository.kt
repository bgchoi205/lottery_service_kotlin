package com.choi.lottery_service.dao

import com.choi.lottery_service.entity.IssuedLottery
import org.springframework.data.jpa.repository.JpaRepository

interface IssuedLotteryRepository: JpaRepository<IssuedLottery, Long> {
    fun findByDrwNo(drwNo: Int): List<IssuedLottery>
}