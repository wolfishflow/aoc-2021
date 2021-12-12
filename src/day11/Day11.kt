package day11

import readInput
import java.lang.Exception

fun main() {
    data class Octopus(val x: Int, val y: Int, var energy: Int)

    var octopusMatrix = mutableListOf(mutableListOf<Octopus>())

    fun getPossibleOctopus(x: Int, y: Int): Octopus? {

        if (x < 0 || x >= octopusMatrix.first().size || y < 0 || y >= octopusMatrix.size) {
            return null
        }

        try {

            return Octopus(x, y, octopusMatrix[y][x].energy)

        } catch (_: Exception) {

        }
        return null
    }

    fun Octopus.getAdjOctopi(): List<Octopus> {
        val upLeft = getPossibleOctopus(this.x - 1, this.y - 1)
        val up = getPossibleOctopus(this.x, this.y - 1)
        val upRight = getPossibleOctopus(this.x + 1, this.y - 1)
        val left = getPossibleOctopus(this.x - 1, this.y)
        val right = getPossibleOctopus(this.x + 1, this.y)
        val downLeft = getPossibleOctopus(this.x - 1, this.y + 1)
        val down = getPossibleOctopus(this.x, this.y + 1)
        val downRight = getPossibleOctopus(this.x + 1, this.y + 1)

        return listOfNotNull(upLeft, up, upRight, left, right, downLeft, down, downRight)
    }

    fun printOutMatrix() {
        octopusMatrix.forEachIndexed { _, row ->
            row.forEachIndexed { _, octopus ->
                print(octopus.energy)
            }
            println("")
        }
    }

    fun energyBoost(): Int {
        var numOfFlashes = 0
        val octopusList = mutableListOf<Octopus>().apply {
            octopusMatrix.forEachIndexed { _, row ->
                row.forEachIndexed { _, octopus ->
                    this += octopus
                }
            }
        }

        octopusList.forEach { it.energy++ }

        while (octopusList.any { it.energy >= 10 }) {
            val flashingOctopi = octopusList.filter { it.energy >= 10 }
            numOfFlashes += flashingOctopi.size

            for (octopus in flashingOctopi) {
                octopus.energy = 0
                octopus.getAdjOctopi().map { foo -> octopusMatrix[foo.y][foo.x] }.filter { adj ->
                    adj.energy != 0
                }.forEach { overflow ->
                    overflow.energy++
                }
            }

        }
        return numOfFlashes
    }


    fun part1(input: List<String>): Int {

        octopusMatrix = mutableListOf<MutableList<Octopus>>().apply {
            input.forEachIndexed { y, row ->
                val octoRow = mutableListOf<Octopus>()
                row.forEachIndexed { x, energy ->
                    octoRow.add(Octopus(x, y, energy.digitToInt()))
                }
                this += octoRow
            }
        }

        var numOfFlashes = 0

        repeat(100) { step ->
            val octopusList = mutableListOf<Octopus>().apply {
                octopusMatrix.forEachIndexed { _, row ->
                    row.forEachIndexed { _, octopus ->
                        this += octopus
                    }
                }
            }

            println("- Step $step -")
            printOutMatrix()
            println("----------")

            octopusList.forEach { it.energy++ }

            while (octopusList.any { it.energy >= 10 }) {
                val flashingOctopi = octopusList.filter { it.energy >= 10 }
                numOfFlashes += flashingOctopi.size

                for (octopus in flashingOctopi) {
                    octopus.energy = 0
                    octopus.getAdjOctopi().map { foo -> octopusMatrix[foo.y][foo.x] }.filter { adj ->
                        adj.energy != 0
                    }.forEach { overflow ->
                        overflow.energy++
                    }
                }

            }
        }

        return numOfFlashes
    }

    fun part2(input: List<String>): Int {

        octopusMatrix = mutableListOf<MutableList<Octopus>>().apply {
            input.forEachIndexed { y, row ->
                val octoRow = mutableListOf<Octopus>()
                row.forEachIndexed { x, energy ->
                    octoRow.add(Octopus(x, y, energy.digitToInt()))
                }
                this += octoRow
            }
        }

        var steps = 0

        while (true) {
            val amountOfFlashingOctopus = energyBoost()
            steps++
            if (amountOfFlashingOctopus == 100) {
                break
            }
        }

        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day11", "Day11_test")
    check(part1(testInput) == 1656)

    val input = readInput("day11", "Day11")
    println(part1(input))
    println(part2(input))
}



