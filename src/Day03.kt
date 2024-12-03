fun main() {
    val input = readInput("Day03")
    val startTime = System.nanoTime()
    Day03.part1(input).println()
    Day03.part2(input).println()
    println("Runtime " + (System.nanoTime() - startTime))
}

class Day03 {
    companion object {
        private val regex = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        private val regex2 = Regex("""mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)""")

        fun part1(input: List<String>): Int = regex.findAll(input.joinToString())
            .sumOf { parseAndMultiply(it.value) }

        private fun parseAndMultiply(it: String) = it
            .drop(4)
            .dropLast(1)
            .split(",")
            .map(String::toInt)
            .reduce { a, b -> a * b }


        fun part2(input: List<String>): Int {
            val values = regex2.findAll(input.joinToString()).map { it.value }.toList()
            var active = true
            var sum = 0
            for (value in values) {
                if (!value.startsWith("mul")) {
                    active = value == "do()"
                } else if (active) {
                    sum += parseAndMultiply(value)
                }
            }
            return sum
        }

    }
}