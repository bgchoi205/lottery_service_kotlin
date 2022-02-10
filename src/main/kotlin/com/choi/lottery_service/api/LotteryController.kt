package com.choi.lottery_service.api

import com.choi.lottery_service.dto.LotterySaveDto
import com.choi.lottery_service.global.response.RespDto
import com.choi.lottery_service.service.LotteryServiceImpl
import org.springframework.web.bind.annotation.*

@RestController
class LotteryController(private val lotteryService: LotteryServiceImpl) {

    @GetMapping
    fun showIndex():String{
        val round = lotteryService.getLastRound()
        val newNumbers = lotteryService.drawNumbers()
        val newNumbersByRandom = lotteryService.drawNumbersByRandom()

        return "최신 회차 : $round // 최근 30회 중 많이 나온 번호 : $newNumbers // 랜덤 번호 : $newNumbersByRandom"
    }

    // 회차 입력에 따라 당첨 번호 가져오기
    @GetMapping("/{round}")
    fun getWinningNumber(@PathVariable round: Int): RespDto{

        return RespDto(lotteryService.getLotteryInfoByRound(round))
    }

    @PostMapping
    fun saveLottery(@RequestBody lotterySaveDto: LotterySaveDto){
        lotteryService.save(lotterySaveDto)
    }

    // 저장된 복권 중 해당 회차 당첨된 번호들 가져오기
    @GetMapping("/won/{round}")
    fun getWonLotteryTickets(@PathVariable round: Int): RespDto? {

        return lotteryService.getWonLotteryInfo(round)?.let {
            RespDto(
                it
            )
        }
    }

}