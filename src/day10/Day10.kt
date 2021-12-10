package day10

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val instructions = input.map {
            it.toCharArray()
        }

        val stack = ArrayDeque<Char>()
        val corruptedChars = mutableListOf<Int>()
        instructions.map line@ { line ->
            stack.clear()
            line.map { currentChar ->
                if (currentChar.isOpening()) {
                    stack.push(currentChar)
                } else {
                    val stackChar = stack.peek()
                    if (stackChar?.isMatching(currentChar) == true) {
                        stack.pop()
                    } else {
                        corruptedChars.add(currentChar.getCorruptedScoreValue())
                        return@line
                    }
                }
            }
        }


        return corruptedChars.sum()
    }

    fun part2(input: List<String>): Long {
        val instructions = input.map {
            it.toCharArray()
        }

        val stack = ArrayDeque<Char>()
        val completedValues = mutableListOf<Long>()
        instructions.map line@ { line ->
            stack.clear()
            line.map { currentChar ->
                if (currentChar.isOpening()) {
                    stack.push(currentChar)
                } else {
                    val stackChar = stack.peek()
                    if (stackChar?.isMatching(currentChar) == true) {
                        stack.pop()
                    } else {
                        return@line
                    }
                }
            }
            var value = 0L
            while (stack.isNotEmpty()) {
                val completedChar = stack.peek()!!.getMatching()
                value = (value * 5) + completedChar.getCompletedScoreValue()
                stack.pop()
            }
            completedValues.add(value)
        }

        return completedValues.sortedBy { it }.get(completedValues.size/2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10","Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10","Day10")
    println(part1(input))
    println(part2(input))
}

fun Char.isOpening() : Boolean {
    return when (this) {
        '(', '[', '{', '<' -> true
        else -> false
    }
}

fun Char.isMatching(otherChar: Char): Boolean {
    return if (this == '(' && otherChar == ')') {
        true

    } else if (this == '[' && otherChar == ']'){
        true

    } else if (this == '{' && otherChar == '}'){
        true

    } else this == '<' && otherChar == '>'
}

fun Char.getMatching(): Char {
    return if (this == '(') {
        ')'

    } else if (this == '['){
        ']'

    } else if (this == '{'){
        '}'

    } else '>'
}

fun Char.getCorruptedScoreValue(): Int {
    return when(this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        else -> 25137
    }
}

fun Char.getCompletedScoreValue(): Long {
    return when(this) {
        ')' -> 1L
        ']' -> 2L
        '}' -> 3L
        else -> 4L
    }
}


fun <T> ArrayDeque<T>.push(element: T) = addLast(element) // returns Unit

fun <T> ArrayDeque<T>.pop() = removeLastOrNull()

fun <T> ArrayDeque<T>.peek(): T? = if (isNotEmpty()) this[lastIndex] else null

