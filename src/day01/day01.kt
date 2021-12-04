package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val input = input.map(String::toInt)
        return input.filterIndexed { index, depth ->
            if (index == 0) {
                false
            } else {
                (depth > input[index - 1])
            }
        }.size
    }

    fun part2(input: List<String>): Int {
        val input = input.map(String::toInt).windowed(3).map(List<Int>::sum)

        //Is there a better way to do this?
        return input.filterIndexed { index, depth ->
            if (index == 0) {
                false
            } else {
                (depth > input[index - 1])
            }
        }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01","Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day01","Day01")
    println(part1(input))
    check(part1(input) == 1602)
    println(part2(input))
    check(part2(input) == 1633)
}
