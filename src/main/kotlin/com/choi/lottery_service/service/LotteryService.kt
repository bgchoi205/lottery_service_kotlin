package com.choi.lottery_service.service

import com.choi.lottery_service.entity.IssuedLottery

interface LotteryService {
    fun findById(id: Long): IssuedLottery
    fun findAll(): List<IssuedLottery>
    fun save(issuedLottery: IssuedLottery)
}