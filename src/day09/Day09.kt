package day09

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.map { line ->
            line.toCharArray()
        }

        val listOfLowPoints = mutableListOf<Int>()
        matrix.forEachIndexed { y, row ->
            row.forEachIndexed { x, digit ->
                if (isLowPoint(x, y, matrix)) {
                    listOfLowPoints.add(digit.digitToInt()+1)
                }
            }
        }

        return listOfLowPoints.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day09", "Day09_test")
    check(part1(testInput) == 15)

    val input = readInput("day09", "Day09")
    println(part1(input))
    println(part2(input))
}

fun isLowPoint(x: Int, y: Int, matrix: List<CharArray>): Boolean {
    val adjacentNumbers = getValidAdjNumbers(x, y, matrix)
    val outcome = adjacentNumbers.filter { num -> num > matrix[y][x].digitToInt() }.size == adjacentNumbers.size
    return outcome
}

fun getValidAdjNumbers(x: Int, y: Int, matrix: List<CharArray>): List<Int> {
    val numbers = mutableListOf<Int>()

    if (x == 0) {
        when (y) {
            0 -> {
                //top corner
                //right
                numbers.add(matrix[y][1].digitToInt())
                //down
                numbers.add(matrix[1][x].digitToInt())
                return numbers
            }
            matrix.size - 1 -> {
                // bottom corner
                //up
                numbers.add(matrix[y - 1][x].digitToInt())
                //right
                numbers.add(matrix[0][0].digitToInt())
                return numbers
            }
            else -> {
                //side wall
                //up
                numbers.add(matrix[y - 1][x].digitToInt())
                //right
                numbers.add(matrix[y][1].digitToInt())
                //down
                numbers.add(matrix[y + 1][x].digitToInt())
                return numbers
            }
        }
    } else if (x == matrix[y].size - 1) when (y) {
        0 -> {
            //top corner
            //left
            numbers.add(matrix[y][x - 1].digitToInt())
            //down
            numbers.add(matrix[1][x].digitToInt())
            return numbers
        }
        matrix.size - 1 -> {
            // bottom corner
            //up
            numbers.add(matrix[y - 1][x].digitToInt())
            //left
            numbers.add(matrix[y][x - 1].digitToInt())
            return numbers
        }
        else -> {
            //side wall
            //up
            numbers.add(matrix[y - 1][x].digitToInt())
            //left
            numbers.add(matrix[y][x - 1].digitToInt())
            //down
            numbers.add(matrix[y + 1][x].digitToInt())
            return numbers
        }
    }

    when (y) {
        0 -> {
            //top wall
            //left
            numbers.add(matrix[y][x - 1].digitToInt())
            //right
            numbers.add(matrix[y][x + 1].digitToInt())
            //down
            numbers.add(matrix[1][x].digitToInt())
            return numbers
        }
        matrix.size - 1 -> {
            //bottom wall
            //up
            numbers.add(matrix[y - 1][x].digitToInt())
            //left
            numbers.add(matrix[y][x - 1].digitToInt())
            //right
            numbers.add(matrix[y][x + 1].digitToInt())
            return numbers
        }
        else -> {
            //interior piece - all 4 directions
            //up
            numbers.add(matrix[y - 1][x].digitToInt())
            //left
            numbers.add(matrix[y][x - 1].digitToInt())
            //right
            numbers.add(matrix[y][x + 1].digitToInt())
            //down
            numbers.add(matrix[y + 1][x].digitToInt())
            return numbers
        }
    }
}

