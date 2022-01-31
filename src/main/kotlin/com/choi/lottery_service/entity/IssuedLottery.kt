package com.choi.lottery_service.entity

import com.choi.lottery_service.dto.LotterySaveDto
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class IssuedLottery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val drwNo: Int,
    private val issuedDate: LocalDateTime,

//    @ElementCollection
//    private val lotteryNumbers: Collection<LotterySaveDto>

    private val num1: Int,
    private val num2: Int,
    private val num3: Int,
    private val num4: Int,
    private val num5: Int,
    private val num6: Int

){

    fun getId(): Long {
        return this.id
    }

    fun getDrwNo(): Int{
        return this.drwNo
    }

    fun getNum1(): Int{
        return this.num1
    }

    fun getNum2(): Int{
        return this.num2
    }

    fun getNum3(): Int{
        return this.num3
    }

    fun getNum4(): Int{
        return this.num4
    }

    fun getNum5(): Int{
        return this.num5
    }

    fun getNum6(): Int{
        return this.num6
    }

//    fun getLotteryNumbers(): Collection<LotterySaveDto>{
//        return this.lotteryNumbers
//    }
}