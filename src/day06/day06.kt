package day06

import readInput
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

fun main() {

    fun part1(input: List<String>): Int {
        println("Part1")
        val fishList = input.first().split(",").map { days ->
            LanternFish(days.toInt())
        }

        return countLanternFishBasedOnDaysPassed(fishList, 80).toInt()
    }

    fun part2(input: List<String>): Long {
        println("Part2")
        val fishList = input.first().split(",").map { days ->
            LanternFish(days.toInt())
        }

        return countLanternFishPart2(fishList, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day06", "Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("day06", "Day06")
    println(part1(input))
    check(part1(input) == 352872)
    println(part2(input))
    check(part2(input) == 1604361182149)
}


//New fish daysTill == 8 (which is 9 days)
// Stale fish daysTill == 6 (which is 7 days)
data class LanternFish(var days: Int)


fun countLanternFishBasedOnDaysPassed(initialFishList: List<LanternFish>, daysPassed: Int): Int {
    val mutableFishList = CopyOnWriteArrayList<LanternFish>()
    mutableFishList.addAll(initialFishList)
    val iterator = mutableFishList.iterator()
    for (day in 0 until daysPassed) {
        mutableFishList.iterator().forEach { fish ->
            when (fish.days) {
                0 -> {
                    mutableFishList.add(LanternFish(8))
                    fish.days = 6
                }
                else -> fish.days--
            }
        }
    }
    return mutableFishList.count()
}

fun countLanternFishPart2(initialFishList: List<LanternFish>, daysPassed: Int): Long {

    //Use slots to rotate vs chasing down the fish tree
    //list of age of fish
    val listOfFishDays = (0..8).map { 0L }.toMutableList()
    initialFishList.forEach {
        listOfFishDays[it.days] = listOfFishDays[it.days] + 1
    }

    for (days in 0 until daysPassed) {
        Collections.rotate(listOfFishDays, -1)
        listOfFishDays[6] = listOfFishDays[6] + listOfFishDays[8]
    }

    return listOfFishDays.sum()
}