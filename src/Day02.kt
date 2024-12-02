import kotlin.math.absoluteValue

fun main() {
    val input = readInput("Day02")
    Day02.part1(input).println()
    Day02.part2(input).println()
}

class Day02 {
    companion object {

        fun part1(input: List<String>): Int {
            return input.count { isValid(it) }
        }

        private fun isValid(line: String): Boolean {
            val numbers = parseNumbers(line)
            return isValidNumberList(numbers)
        }

        private fun isValidPair(it: Pair<Int, Int>, increasing: Boolean): Boolean {
            val diff = (it.first - it.second).absoluteValue
            if (diff < 1 || diff > 3) {
                return false
            }
            if (increasing && it.first > it.second) {
                return false
            }
            if (!increasing && it.first < it.second) {
                return false
            }
            return true
        }

        fun part2(input: List<String>): Int {
            return input.count { isValidWith1Error(it) }
        }

        private fun isValidWith1Error(line: String): Boolean {
            val numbers = parseNumbers(line)
            val variations = numbers.indices.map { i -> numbers.filterIndexed { index, _ -> i != index } }
            return variations.any { isValidNumberList(it) }
        }

        private fun isValidNumberList(numbers: List<Int>): Boolean {
            val pairs = numbers.windowed(2).map { Pair(it[0], it[1]) }
            val increasing = pairs[0].first < pairs[0].second
            return pairs.all { isValidPair(it, increasing) }
        }

        private fun parseNumbers(line: String) = line.split(" ").map { it.toInt() }
    }
}