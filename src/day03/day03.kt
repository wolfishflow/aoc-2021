package day03

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val gamma = generateGammaRate(input)
        val epsilon = generateEpsilonRate(input)

        return gamma * epsilon
    }


    fun part2(input: List<String>): Int {
        val o2GenRating = getO2GenRating(input)
        val co2ScrubRating = getCo2ScrubRating(input)

        return o2GenRating * co2ScrubRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03","Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("day03","Day03")
    println(part1(input))
    check(part1(input) == 749376)
    println(part2(input))
    check(part2(input) == 2372923)
}


fun generateGammaRate(input: List<String>) : Int {

    val stringBuilder = StringBuilder()
    for (position in 0 until input.first().length) {
        var zero = 0
        var one = 0
        input.map { value ->
            if (value[position] == '0') {
                zero++
            } else {
                one++
            }
        }
        stringBuilder.append(if (zero>one) '0' else '1')
    }
    return stringBuilder.toString().toInt(radix = 2)
}

fun generateEpsilonRate(input: List<String>) : Int {
    val stringBuilder = StringBuilder()
    for (position in 0 until input.first().length) {
        var zero = 0
        var one = 0
        input.map { value ->
            if (value[position] == '0') {
                zero++
            } else {
                one++
            }
        }
        stringBuilder.append(if (zero<one) '0' else '1')
    }
    return stringBuilder.toString().toInt(radix = 2)
}

fun getO2GenRating(input: List<String>) : Int {
    var listOfRatings = input.toMutableList()
    var position = 0
    var mostCommonBit : Char
    do {
        var zero = 0
        var one = 0
        listOfRatings.map { value ->
            if (value[position] == '0') {
                zero++
            } else {
                one++
            }
        }

        mostCommonBit = if (one >= zero) '1' else '0'

        listOfRatings = listOfRatings.filter { rating -> rating[position] == mostCommonBit } as MutableList<String>

        position++
    } while (listOfRatings.size > 1)


    return listOfRatings.first().toInt(radix = 2)
}

fun getCo2ScrubRating(input: List<String>) : Int {
    var listOfRatings = input.toMutableList()
    var position = 0
    var mostCommonBit : Char
    do {
        var zero = 0
        var one = 0
        listOfRatings.map { value ->
            if (value[position] == '0') {
                zero++
            } else {
                one++
            }
        }

        mostCommonBit = if (zero <= one) '0' else '1'

        listOfRatings = listOfRatings.filter { rating -> rating[position] == mostCommonBit } as MutableList<String>

        position++
    }while (listOfRatings.size > 1)


    return listOfRatings.first().toInt(radix = 2)
}
