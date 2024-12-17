fun main() {
//    val input = readInput("DayX")[0]
    val input = "RRRRIICCFF\n" +
            "RRRRIICCCF\n".split("\n").toList()
    val startTime = System.currentTimeMillis()
    DayX.part1(input).println()
    DayX.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class DayX {
    companion object {

        fun part1(input: String): Int {
            return 0
        }

        fun part2(input: String): Int {
            return 0
        }


    }
}
