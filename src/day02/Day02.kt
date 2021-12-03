package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        input.map { commandAndUnit ->
            val command = commandAndUnit.split(" ").first()
            val units = commandAndUnit.split(" ").last()
            when(command) {
                "forward" ->{
                    horizontal += units.toInt()
                }
                "up" -> {
                    depth -= units.toInt()
                }
                "down" -> {
                    depth += units.toInt()
                }
            }
        }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0
        input.map { commandAndUnit ->
            val command = commandAndUnit.split(" ").first()
            val units = commandAndUnit.split(" ").last()
            when(command) {
                "forward" ->{
                    horizontal += units.toInt()
                    depth += aim * units.toInt()
                }
                "up" -> {
                    aim -= units.toInt()
                }
                "down" -> {
                    aim += units.toInt()
                }
            }
        }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02","Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("day02","Day02")
    println(part1(input))
    println(part2(input))
}
