package com.choi.lottery_service.service

import com.choi.lottery_service.dao.IssuedLotteryRepository
import com.choi.lottery_service.entity.IssuedLottery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LotteryServiceImpl(private val lotteryRepository: IssuedLotteryRepository): LotteryService {

    override fun findById(id: Long): IssuedLottery {
        return lotteryRepository.findById(id).orElseThrow()
    }

    override fun findAll(): List<IssuedLottery> {
        return lotteryRepository.findAll()
    }

    @Transactional
    override fun save(issuedLottery: IssuedLottery) {
        lotteryRepository.save(issuedLottery)
    }

}