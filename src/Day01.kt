import kotlin.math.absoluteValue

fun main() {
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int {
    val inputs = parse(input)
    val list1 = inputs.first.sorted()
    val list2 = inputs.second.sorted()
    val distances = list1.zip(list2) { a, b -> (a - b).absoluteValue }
    return distances.sum()
}

fun parse(input: List<String>): Pair<List<Int>, List<Int>> {
    val result = Pair(mutableListOf<Int>(), mutableListOf<Int>())
    for (e in input) {
        val values = e.split("   ")
        result.first += values[0].toInt()
        result.second += values[1].toInt()
    }
    return result
}

fun part2(input: List<String>): Int {
    val inputs = parse(input)
    val map = inputs.second.groupBy { it }.mapValues { it.value.size }
    val scores = inputs.first.map { map.getOrDefault(it, 0) * it }
    return scores.sum()
}