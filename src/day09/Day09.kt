package day09

import readInput
import java.lang.Exception

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.map { line ->
            line.toCharArray()
        }

        val listOfPoints = mutableListOf<Point>().apply {
            matrix.forEachIndexed { y, row ->
                row.forEachIndexed { x, digit ->
                    this += Point(x, y, digit.digitToInt())
                }
            }
        }

        val listOfLowPoints = listOfPoints.filter { point ->
            getAdjPoints(point, matrix).all { newPoint ->
                newPoint.heightVal > point.heightVal
            }
        }

        return listOfLowPoints.sumOf { it.heightVal + 1 }
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { line ->
            line.toCharArray()
        }

        val listOfPoints = mutableListOf<Point>().apply {
            matrix.forEachIndexed { y, row ->
                row.forEachIndexed { x, digit ->
                    this += Point(x, y, digit.digitToInt())
                }
            }
        }

        val listOfLowPoints = listOfPoints.filter { point ->
            getAdjPoints(point, matrix).all { newPoint ->
                newPoint.heightVal > point.heightVal
            }
        }

        val threeLargestBasins = listOfLowPoints.map { findBasin(it, matrix) }.sortedBy { it.size }.takeLast(3)

        return threeLargestBasins.map { it.size }.reduce { a, b -> a * b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day09", "Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("day09", "Day09")
    println(part1(input))
    println(part2(input))
}

data class Point(val x: Int, val y: Int, val heightVal: Int)

fun getAdjPoints(currentPoint: Point, matrix: List<CharArray>): List<Point> {
    val up = getPossiblePoint(currentPoint.x, currentPoint.y - 1, matrix)
    val left = getPossiblePoint(currentPoint.x - 1, currentPoint.y, matrix)
    val right = getPossiblePoint(currentPoint.x + 1, currentPoint.y, matrix)
    val down = getPossiblePoint(currentPoint.x, currentPoint.y + 1, matrix)

    return listOfNotNull(up, left, right, down)
}


fun getPossiblePoint(x: Int, y: Int, matrix: List<CharArray>): Point? {

    if (x < 0 || x >= matrix.first().size || y < 0 || y >= matrix.size) {
        return null
    }

    try {

        return Point(x, y, matrix[y][x].digitToInt())

    } catch (_: Exception) {

    }
    return null
}

fun findBasin(point: Point, matrix: List<CharArray>): Set<Point> {
    var current = mutableSetOf(point)

    var next: MutableSet<Point>

    while (true) {

        next = getNextBasin(current, matrix)

        if (current.size == next.size) {
            return next
        }

        current = next
    }

}

fun getNextBasin(current: MutableSet<Point>, matrix: List<CharArray>): MutableSet<Point> {
    val nextBasin = current.toMutableSet()
    for (point in current) {
        nextBasin.addAll(getAdjPoints(point, matrix).filter { it.heightVal != 9 })
    }
    return nextBasin
}



