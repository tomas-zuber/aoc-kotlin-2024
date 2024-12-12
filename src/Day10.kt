fun main() {
    val input = readInput("Day10")
    val input2 = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().trim().lines()
    val startTime = System.currentTimeMillis()
    Day10.part1(input).println()
    Day10.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day10 {
    companion object {

        fun part1(input: List<String>): Int {
            val paths = getPaths(input)
            return paths.map { Pair(it.first(), it.last()) }.toSet().size
        }

        private fun findPaths(grid: Grid, path: Path, height: Int): List<Path> {
            if (height > 9) {
                return listOf(path)
            }
            val point = path.last()
            val result = mutableListOf<Path>()
            if (grid.numValueAt(point.up()) == height) {
                result.addAll(findPaths(grid, path + point.up(), height + 1))
            }
            if (grid.numValueAt(point.down()) == height) {
                result.addAll(findPaths(grid, path + point.down(), height + 1))
            }
            if (grid.numValueAt(point.left()) == height) {
                result.addAll(findPaths(grid, path + point.left(), height + 1))
            }
            if (grid.numValueAt(point.right()) == height) {
                result.addAll(findPaths(grid, path + point.right(), height + 1))
            }
            return result
        }

        fun part2(input: List<String>): Int {
            val paths = getPaths(input)
            return paths.size
        }

        private fun getPaths(input: List<String>): MutableList<Path> {
            val grid = Grid(input)
            val paths = mutableListOf<Path>()

            for (y in grid.numGrid.indices) {
                for (x in grid.numGrid[y].indices) {
                    if (grid.numGrid[y][x] == 0) {
                        paths.addAll(findPaths(grid, listOf(Point(x, y)), 1))
                    }
                }
            }
            return paths
        }
    }
}
