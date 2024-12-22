fun main() {
    val inputString = readInput("Day13")
    val input = Day13.parse(inputString)
    val startTime = System.currentTimeMillis()
    Day13.part1(input).println()
    Day13.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day13 {
    companion object {

        fun part1(input: List<Equations>): Long = calculate(input)

        private fun calculate(input: List<Equations>): Long {
            var result = 0L
            for (e in input) {
                val a = (e.cx * e.by - e.cy * e.bx) / (e.ax * e.by - e.ay * e.bx)
                val b = (e.cx - e.ax * a) / e.bx
                // println("$e A=$a B=$b -> ")
                // println("${a * e.ax + b * e.bx} ${a * e.ay + b * e.by} ${e.isCorrect(a, b)}")
                if (e.isCorrect(a, b)) {
                    result += 3 * a + b
                }
            }
            return result
        }

        fun part2(input: List<Equations>) = calculate(input.map {
            it.copy(
                cx = 10000000000000 + it.cx,
                cy = 10000000000000 + it.cy,
            )
        })

        fun parse(inputString: List<String>): List<Equations> {
            return inputString.chunked(4).map { list ->
                val a = parseLine(list[0])
                val b = parseLine(list[1])
                val c = parseLine(list[2])
                Equations(a.first, a.second, b.first, b.second, c.first, c.second)
            }
        }

        private fun parseLine(line: String): Pair<Long, Long> {
            val nums = line.split(":")[1]
                .replace("X", "")
                .replace("Y", "")
                .replace("+", "")
                .replace("=", "")
                .split(",")
                .map { it.trim().toLong() }
            return Pair(nums[0], nums[1])
        }

    }
}

data class Equations(val ax: Long, val ay: Long, val bx: Long, val by: Long, val cx: Long, val cy: Long) {
    fun isCorrect(a: Long, b: Long): Boolean = a * ax + b * bx == cx && a * ay + b * by == cy
}