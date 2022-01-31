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

//    @Test
//    fun saveTest(){
//        val dto = LotterySaveDto(8, 15, 22, 23, 39, 44)
//        val now = LocalDateTime.now()
//
//        val lottery = IssuedLottery(
//            1L,
//            16,
//            now,
//            listOf(dto)
//        )
//
//        lotteryService.save(lottery)
//
//        val findLottery = lotteryService.findById(1L)
//
//        Assertions.assertTrue(lottery == findLottery)
//
//
//    }

    @Test
    @Transactional
    fun getWonLotteryTest(){
        val num1 = 2
        val num2 = 8
        val num3 = 19
        val num4 = 22
        val num5 = 32
        val num6 = 42
        val now = LocalDateTime.now()



        for(i:Int in 0..4){
            val lottery = IssuedLottery(
                i+1.toLong(),
                1000,
                now,
                num1 + i,
                num2 + i,
                num3 + i,
                num4 + i,
                num5 + i,
                num6 + i,
            )
            lotteryService.save(lottery)
        }


        val findWonLottery = lotteryService.getWonLotteryInfo(1000)

        Assertions.assertTrue(findWonLottery.size == 1)
    }

}