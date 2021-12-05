package day04

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val numbersCalled = input.first().split(",")

        val listOfBoards = mutableListOf<BingoBoard>()
        var tempList = mutableListOf<List<BingoNumber>>()
        input.subList(2,input.size).map { line ->
            if (line != "") {
                tempList.add(line.split(" ").filter { number -> number != "" }.map { BingoNumber(it, false) })
            } else {
                listOfBoards.add(
                    BingoBoard(tempList.toList())
                )
                tempList.clear()
            }
        }

        listOfBoards.add(BingoBoard(tempList.toList()))

        return getWinningBoardSum(numbersCalled, listOfBoards)
    }

    fun part2(input: List<String>): Int {
        val numbersCalled = input.first().split(",")

        val listOfBoards = mutableListOf<BingoBoard>()
        var tempList = mutableListOf<List<BingoNumber>>()
        input.subList(2,input.size).map { line ->
            if (line != "") {
                tempList.add(line.split(" ").filter { number -> number != "" }.map { BingoNumber(it, false) })
            } else {
                listOfBoards.add(
                    BingoBoard(tempList.toList())
                )
                tempList.clear()
            }
        }

        listOfBoards.add(BingoBoard(tempList.toList()))

        return getLastWinningBoardSum(numbersCalled, listOfBoards)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04", "Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("day04", "Day04")
    println(part1(input))
    check(part1(input) == 8136)
    println(part2(input))
    check(part2(input) == 12738)
}


data class BingoBoard(val rows: List<List<BingoNumber>>)
data class BingoNumber(val number: String, var marked: Boolean)

fun getLastWinningBoardSum(numbersCalled : List<String>, bingoBoards: MutableList<BingoBoard>): Int {
    val cloneList = bingoBoards.toMutableList()
    numbersCalled.map { currentNumber ->
        bingoBoards.map { currentBoard ->
            val isMarked = markNumber(currentNumber, currentBoard)
            if (isMarked) {
                val isWinning = isWinningBoard(currentBoard, currentNumber)
                if (isWinning) {
                    if (cloneList.size == 1 && cloneList.contains(currentBoard)) {
                        return currentNumber.toInt() * calculateUnmarkedSum(currentBoard)
                    } else {
                        cloneList.remove(currentBoard)
                    }
                }
            }
        }
    }
    return 0
}

fun getWinningBoardSum(numbersCalled : List<String>, bingoBoards: List<BingoBoard>): Int {
    numbersCalled.map { currentNumber ->
        bingoBoards.map { currentBoard ->
            val isMarked = markNumber(currentNumber, currentBoard)
            if (isMarked) {
                val isWinning = isWinningBoard(currentBoard, currentNumber)
                if (isWinning) {
                    return currentNumber.toInt() * calculateUnmarkedSum(currentBoard)
                }
            }
        }
    }
    return 0
}

fun markNumber(number: String, board: BingoBoard): Boolean {
    board.rows.map { row ->
        row.map { currentNumber ->
            if (number == currentNumber.number) {
                currentNumber.marked = true
                return true
            }
        }
    }

    return false
}

fun isWinningBoard(board: BingoBoard, number: String): Boolean {
    //Verify Horiz
    var counter = 0
    board.rows.map horizontal@ { row ->
        row.map { currentNumber ->
            if (currentNumber.marked) {
                counter++
                if (counter == 5) {
                    return true
                }
            } else {
                counter = 0
                return@horizontal
            }
        }
    }

    //Verify Verti
    vertical@ for (currentCol in 0 until board.rows.size) {
        for (currentRow in 0 until board.rows.size) {
            val currentNumber = board.rows[currentRow][currentCol]
            if (currentNumber.marked) {
                counter++
                if (counter == 5) {
                    return true
                }
            } else {
                counter = 0
                break
            }
        }
    }

    return false
}

fun calculateUnmarkedSum(board: BingoBoard): Int {
    val sum = board.rows.sumOf { row ->
        row.filter { number -> !number.marked }.map { it.number.toInt() }.sum()
    }
    return sum
}

