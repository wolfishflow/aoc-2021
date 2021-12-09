package day08

import readInput
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun main() {

    fun part1(input: List<String>): Int {
        val splitInput = input.map { input ->
            val (value, legend) = input.split("|")
            val values = value.split(" ").map { classifyLetter(it) }
            val legends = legend.split(" ").map { classifyLetter(it) }

            legends.filter { values.contains(it) && it != -1 }
        }
        return splitInput.map { it.size }.sum()
    }

    fun part2(input: List<String>): Long {

        var sum = 0L

        input.map{ line ->

            val (displayDigits, output) = line.split("|")
            val map = isolateSegments(displayDigits, output)
            val line = output.split(" ").filter { it.isNotEmpty() }
            val stringBuilder = StringBuilder()

            line.map { digit ->
                stringBuilder.append(map[digit.toSet()])
            }
            sum += stringBuilder.toString().toLong()
            stringBuilder.clear()
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day08", "Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229L)

    val input = readInput("day08", "Day08")
    println(part1(input))
//    check(part1(input) == 473)
    println(part2(input))
//    check(part2(input) == 1097568)
}


/*

 aaaa
b    c
b    c
 dddd
e    f
e    f
 gggg

 */

fun isolateSegments(input: String, output: String) :  MutableMap<Set<Char>, Int>{
    val allChars = setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

    // dddd is the difference between 8 and 0
    val map = mutableMapOf<Set<Char>, Int>()
    val displayDigits = mutableListOf<Set<Char>>()
    displayDigits.addAll(input.split(" ").filter { it.isNotEmpty() }.sortedBy { it.length }.map { it.toSet() })

    val one = displayDigits.first().toSet()
    val seven = displayDigits[1].toSet()
    val four = displayDigits[2].toSet()
    val eight = displayDigits.last().toSet()

    displayDigits.remove(displayDigits.last())
    displayDigits.remove(displayDigits[2])
    displayDigits.remove(displayDigits[1])
    displayDigits.remove(displayDigits.first())

    val top = seven.minus(one)

    val topAndFour = four.plus(top)

    // 4 + a(top) == 9-bottom?
    // 4 + a(top) - 9 == bottom
    val bottom = displayDigits.filter { it.size == 6 }.map { it.minus(topAndFour) }.first { it.size == 1 }

    val bottomLeft = eight.minus(four).minus(top).minus(bottom)

    val nine = displayDigits.filter { it.size == 6 }.first { it.containsAll(four) }

    val three = displayDigits.filter { it.size == 5 }.first { it.containsAll(seven) }

    val two = displayDigits.filter { it.size == 5 }.filter { it != three }.first {it.containsAll(bottomLeft)}

    val five = displayDigits.filter { it.size == 5 }.first { it != three && it != two }

    val six = displayDigits.filter { it.size == 6 }.filter { it != nine }.first { !it.containsAll(seven) }

    val zero = displayDigits.filter { it.size == 6 }.first { it != six && it != nine }

    map[zero] = 0
    map[one] = 1
    map[two] = 2
    map[three] = 3
    map[four] = 4
    map[five] = 5
    map[six] = 6
    map[seven] = 7
    map[eight] = 8
    map[nine] = 9

//    #2 - 1
//    #3 - 7
//    #4 - 4
//    #5 - 235
//    #6 - 069
//    #7 - 8

    return map
}

fun classifyLetter(word: String): Int {
    return when (word.count()) {
        2 -> 1
        3 -> 7
        4 -> 4
        5 -> {
            -1
            //2,3,5
        }
        6 -> {
            -1
            //0,6,9
        }
        7 -> 8
        else -> -1
    }
}
