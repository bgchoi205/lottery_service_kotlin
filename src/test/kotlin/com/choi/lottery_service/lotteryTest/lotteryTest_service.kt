package com.choi.lottery_service.lotteryTest

import com.choi.lottery_service.dto.LotterySaveDto
import com.choi.lottery_service.entity.IssuedLottery
import com.choi.lottery_service.service.LotteryServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Transactional
class lotteryTest_service() {

    @Autowired
    private lateinit var lotteryService: LotteryServiceImpl

    @Test
    fun saveTest(){
        val dto = LotterySaveDto(8, 15, 22, 23, 39, 44)
        val now = LocalDateTime.now()

        val lottery = IssuedLottery(
            1L,
            16,
            now,
            listOf(dto)
        )

        lotteryService.save(lottery)

        val findLottery = lotteryService.findById(1L)

        Assertions.assertTrue(lottery == findLottery)


    }

}