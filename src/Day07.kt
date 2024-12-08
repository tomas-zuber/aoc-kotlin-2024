
fun main() {
    val input = readInput("Day07")
//    val input = listOf("4068635: 3 3 43 703 22 632")
    val startTime = System.currentTimeMillis()
    //Day07.part1(input).println()
    Day07.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day07 {
    companion object {
        private val lineSplitterRegex = Regex(":? ")

        fun part1(input: List<String>): Long {
            val rows = parse(input)

            return rows.sumOf {row->
                val calcResult = calcResult(row[1], row.drop(2))
                if (calcResult.contains(row[0])) row[0] else 0
            }
        }

        private fun calcResult(total: Long, input: List<Long>): List<Long> {
            if (input.isEmpty()) {
                return listOf(total)
            }

            val newInput = input.drop(1)
            val num = input[0]

            return listOf(
                calcResult(total + num, newInput),
                calcResult(total * num, newInput)
            ).flatten()
        }

        private fun parse(input: List<String>): List<List<Long>> = input.map { line ->
            line.split(lineSplitterRegex).map { it.toLong() }
        }

        fun part2(input: List<String>): Long {
            val rows = parse(input)

            return rows.sumOf { row->
                val calcResult = calcResult2(row[1], row.drop(2))
                if (calcResult.contains(row[0])) row[0] else 0
            }
        }

        private fun calcResult2(total: Long, input: List<Long>): List<Long> {
            if (input.isEmpty()) {
                return listOf(total)
            }

            val newInput = input.drop(1)
            val num = input[0]

            return listOf(
                calcResult2(total + num, newInput),
                calcResult2(total * num, newInput),
                calcResult2(concat(total, num), newInput)
            ).flatten()
        }

        private fun concat(x:Long,y:Long): Long = "$x$y".toLong()
    }
}
