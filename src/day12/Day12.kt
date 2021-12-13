package day12

import readInput


fun main() {

    fun String.isBigCave() = first().isUpperCase()

    var edges : Map<String, List<String>>? = null

    //We use Set and not List because the recursion can produce paths that can overlap between different small caves
    // that are NOT used twice
    // ex: start,A,b,end can occur when b is the double and start,A,b,end can occur when c is the double
    // In fact it'll show up three times since b, c, and d are small caves
    fun traverse(currentPath: List<String>, pathsTravelled: MutableSet<List<String>>, smallCave: String?) {
        val lastNode = currentPath.last()

        if (lastNode == "end") {
            pathsTravelled.add(currentPath)
            return
        }

        val adjNodes = edges!![lastNode]?.filter { node ->
            node.isBigCave() || node !in (currentPath + "start") || node == smallCave && currentPath.count { it == smallCave } < 2
        }

        if (adjNodes != null) {
            for (node in adjNodes) {
                traverse(currentPath+node, pathsTravelled, smallCave)
            }
        }

    }


    fun part1(input: List<String>): Int {
        val paths = input.map { it.split("-") }
        edges  = buildMap<String, MutableList<String>> {
            paths.map {
                this.getOrPut(it.first()) { mutableListOf() }.add(it.last())
                this.getOrPut(it.last()) { mutableListOf() }.add(it.first())
            }
        }

        val pathsTravelled = mutableSetOf<List<String>>()

        traverse(listOf("start"), pathsTravelled, null)

        return pathsTravelled.size
    }

    fun part2(input: List<String>): Int {

        val paths = input.map { it.split("-") }
        val nodes = paths.flatMap { it.toList() }.distinct().sorted()
        edges = buildMap<String, MutableList<String>> {
            paths.map {
                this.getOrPut(it.first()) { mutableListOf() }.add(it.last())
                this.getOrPut(it.last()) { mutableListOf() }.add(it.first())
            }
        }

        val pathsTravelled = mutableSetOf<List<String>>()

        // i want to isolate all small caves, and use each small cave twice once
        // filter for small, and exclude start and end
        // iterate over list and call traverse, and provide the small cave as the cave to be used twice
        nodes.filter { node -> !node.isBigCave() && (node != "start" && node != "end") }.map { smallCave ->
            traverse(listOf("start"), pathsTravelled, smallCave)
        }

        return pathsTravelled.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day12","Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("day12","Day12")
    println(part1(input))
    println(part2(input))
}