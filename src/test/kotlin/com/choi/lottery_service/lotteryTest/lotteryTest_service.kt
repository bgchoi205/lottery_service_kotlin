package com.choi.lottery_service.lotteryTest

import com.choi.lottery_service.dto.LotterySaveDto
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
        val numbers1:Collection<Int> = listOf(8, 15, 22, 23, 39, 44)
        val numbers2:Collection<Int> = listOf(8, 15, 22, 25, 32, 43)
        val numbers3:Collection<Int> = listOf(2, 15, 22, 23, 29, 44)

        val now = LocalDateTime.now()

        val lotterySaveDto = LotterySaveDto(
            numbers1,
            numbers2,
            numbers3,
            null,
            null
        )

        lotteryService.save(lotterySaveDto)

        val findLottery = lotteryService.findById(1L)

//        org.assertj.core.api.Assertions.assertThat(lottery.id == findLottery.id)

        Assertions.assertTrue(findLottery.id == 1L)

    }

    @Test
    fun getWonLotteryTest(){

        val numbers1:Collection<Int> = listOf(2, 8, 19, 22, 32, 42) // 1등
        val numbers2:Collection<Int> = listOf(2, 8, 19, 22, 39, 42) // 2등
        val numbers3:Collection<Int> = listOf(2, 8, 11, 25, 32, 42) // 4등
        val numbers4:Collection<Int> = listOf(1, 5, 15, 26, 32, 42) // 낙첨

        val lotterySaveDto = LotterySaveDto(
            numbers1,
            numbers2,
            numbers3,
            numbers4,
            null
        )

        lotteryService.save(lotterySaveDto)

        val wonLotteryInfos = lotteryService.getWonLotteryInfo(1000)

        if (wonLotteryInfos != null) {
            org.assertj.core.api.Assertions.assertThat(wonLotteryInfos.size)
        }
    }

}