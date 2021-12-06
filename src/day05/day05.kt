package day05

import readInput
import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val lines = input.map {
            val (start, end) = it.split(" -> ")
            val (x1, y1) = start.split(",")
            val (x2, y2) = end.split(",")

            Line(Vertex(x1.toInt(), y1.toInt()), Vertex(x2.toInt(), y2.toInt()))
        }

        return getIntersectionAmount(lines)
    }

    fun part2(input: List<String>): Int {
        val lines = input.map {
            val (start, end) = it.split(" -> ")
            val (x1, y1) = start.split(",")
            val (x2, y2) = end.split(",")

            Line(Vertex(x1.toInt(), y1.toInt()), Vertex(x2.toInt(), y2.toInt()))
        }


        val abc = getIntersectionAmountIncludingDiagonals(lines)

        return abc
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05", "Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("day05", "Day05")
    println(part1(input))
    //check(part1(input) == 7674)
    println(part2(input))
    //check(part2(input) == 20898)
}


data class Vertex(val x: Int, val y: Int)

data class Line(val start: Vertex, val end: Vertex) {

    private fun validLineDirection(): Line {
        //Not all lines are L->R or U ->D

        return if (start.x < end.x) {
            //do nothing?
            this
        } else if (start.x == end.x && start.y < end.y) {
            this
        } else {
            Line(end, start)
        }
    }

    fun generateCoordinates(): List<Vertex> {

        val validLine = validLineDirection()

        //This is a Vertical Line
        if (validLine.start.x == validLine.end.x) {
            return (validLine.start.y..validLine.end.y).map { Vertex(validLine.start.x, it) }
        }

        //This is a Horizontal Line
        if (validLine.start.y == validLine.end.y) {
            return (validLine.start.x..validLine.end.x).map { Vertex(it, validLine.start.y) }
        }

        return emptyList()
    }

    fun generateCoordinatesWithDiagonals(): List<Vertex> {

        val validLine = validLineDirection()

        //This is a Vertical Line
        if (validLine.start.x == validLine.end.x) {
            return (validLine.start.y..validLine.end.y).map { Vertex(validLine.start.x, it) }
        }

        //This is a Horizontal Line
        if (validLine.start.y == validLine.end.y) {
            return (validLine.start.x..validLine.end.x).map { Vertex(it, validLine.start.y) }
        }

        val deltaX = validLine.end.x - validLine.start.x
        val deltaY = validLine.end.y - validLine.start.y

        val direction = when {
            deltaY > 0 -> 1
            deltaY < 0 -> -1
            else -> 0
        }

            return (0.. deltaX).map { delta ->
                Vertex(validLine.start.x+delta, validLine.start.y + direction * delta)
            }
        }

}


fun getIntersectionAmount(lines: List<Line>): Int {
    //Plot all know coords
    val vertexMap = mutableMapOf<Vertex, Int>()
    //then iterate over the lines
    // check if the map has the coord - if it does +1 versus just 1
    for (currentLine in lines) {
        for (coordinate in currentLine.generateCoordinates()) {
            vertexMap[coordinate] = vertexMap.getOrDefault(coordinate, 0) + 1
        }
    }

    return vertexMap.filter { vertex ->
        vertex.value >= 2
    }.count()
}

fun getIntersectionAmountIncludingDiagonals(lines: List<Line>): Int {
    //Plot all know coords
    val vertexMap = mutableMapOf<Vertex, Int>()
    //then iterate over the lines
    // check if the map has the coord - if it does +1 versus just 1
    for (currentLine in lines) {
        for (coordinate in currentLine.generateCoordinatesWithDiagonals()) {
            vertexMap[coordinate] = vertexMap.getOrDefault(coordinate, 0) + 1
        }
    }

    return vertexMap.filter { vertex ->
        vertex.value >= 2
    }.count()
}

