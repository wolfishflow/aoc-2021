package day07

import readInput
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun main() {

    fun part1(input: List<String>): Int {
        val crabs = input.first().split(",").map(String::toInt).sortedBy { it }
        val median = if (crabs.size%2 == 0) {
            (crabs[(crabs.size/2)-1] + crabs[crabs.size/2]) / 2
        } else {
            (crabs[crabs.size])/2
        }

        val crabBucket = crabs.groupBy { it }

        val totalFuel = crabBucket.map {
            //key is position
            //value is num of crabs
            abs(it.key - median) * it.value.size
        }

        return totalFuel.sum()
    }

    fun part2(input: List<String>): Long {
        val crabs = input.first().split(",").map(String::toInt).sortedBy { it }
        val avg = floor(crabs.average())

        val crabBucket = crabs.groupBy { it }

        val totalFuel = crabBucket.map { crab ->
            //key is position
            //value is num of crabs
            //Do nothing
            val costToMove = abs(crab.key - avg.toLong())
            var fuelBucket = 0L
            for (fuel in 0 .. costToMove) {
                fuelBucket += fuel * crab.value.size.toLong()
            }
            fuelBucket
        }



        return totalFuel.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day07", "Day07_test")
    check(part1(testInput) == 37)
//    check(part2(testInput) == 168L)

    val input = readInput("day07", "Day07")
    println(part1(input))
//    check(part1(input) == 331067)
    println(part2(input))
//    check(part2(input) == 92881128)
}
