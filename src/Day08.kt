fun main() {
    val input = readInput("Day08")
    val startTime = System.currentTimeMillis()
    Day08.part1(input).println()
    Day08.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day08 {
    companion object {

        fun part1(input: List<String>): Int {
            val grid = Grid8(input)
            val combinations = combinations(grid.antennas)
            val antinodes = antinodes(combinations)
            return antinodes.filter { grid.isValid(it) }.toSet().size
        }

        private fun combinations(antennas: MutableMap<Char, MutableList<Point>>) =
            antennas.flatMap {
                val pairs = it.value
                val combinations = mutableListOf<Pair<Point, Point>>()
                for (i in 0..< pairs.size - 1) {
                    for (j in i + 1..< pairs.size) {
                        combinations.add(Pair(pairs[i], pairs[j]))
                    }
                }
                combinations
            }

        private fun antinodes(combinations: List<Pair<Point, Point>>):List<Point> = combinations.flatMap {
            val diff = it.first - it.second
            listOf(it.first + diff, it.second - diff)
        }


        fun part2(input: List<String>): Int {
            val grid = Grid8(input)
            val combinations = combinations(grid.antennas)
            combinations.println()
            val antinodes = antinodes2(combinations, grid)
            antinodes.println()
            return antinodes.toSet().size
        }

        private fun antinodes2(combinations: List<Pair<Point, Point>>, grid: Grid8):List<Point> = combinations.flatMap {
            val diff = it.first - it.second

            val result = mutableListOf<Point>()
            var p = it.first
            while (grid.isValid(p)) {
                result.add(p)
                p += diff
            }
            p = it.second
            while (grid.isValid(p)) {
                result.add(p)
                p -= diff
            }
            result
        }
    }
}

data class Grid8(val grid: List<String>) {
    private val width = grid[0].length
    private val height = grid.size
    var antennas = mutableMapOf<Char, MutableList<Point>>()

    init {
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c != '.') {
                    antennas.computeIfAbsent(c) { mutableListOf() }
                        .add(Point(x, y))
                }
            }
        }
    }

    fun isValid(position: Point): Boolean {
        return position.x in 0..<width &&
                position.y in 0..<height
    }
}
