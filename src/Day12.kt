fun main() {
//    val input = readInput("Day12")[0]
    val input = ("RRRRIICCFF\n" +
            "RRRRIICCCF\n" +
            "VVRRRCCFFF\n" +
            "VVRCCCJFFF\n" +
            "VVVVCJJCFE\n" +
            "VVIVCCJJEE\n" +
            "VVIIICJJEE\n" +
            "MIIIIIJJEE\n" +
            "MIIISIJEEE\n" +
            "MMMISSJEEE").split("\n")
    val startTime = System.currentTimeMillis()
    Day12.part1(input).println()
    Day12.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day12 {
    companion object {

        private val gardenMap = mutableMapOf<Char, List<Garden>>()

        fun part1(input: List<String>): Int {
            val grid = Grid(input)
            grid.print()

            val type = grid.valueAt(Point(0, 0))
            val garden = Garden(type)
            gardenMap[type] = listOf(garden)
            scanGarden(Point(0, 0), grid, garden)

            gardenMap.values.forEach { println("$it") }

//            A region of R plants with price 12 * 18 = 216.
//            A region of I plants with price 4 * 8 = 32.
//            A region of C plants with price 14 * 28 = 392.
//            A region of F plants with price 10 * 18 = 180.
//            A region of V plants with price 13 * 20 = 260.
//            A region of J plants with price 11 * 20 = 220.
//            A region of C plants with price 1 * 4 = 4.
//            A region of E plants with price 13 * 18 = 234.
//            A region of I plants with price 14 * 22 = 308.
//            A region of M plants with price 5 * 12 = 60.
//            A region of S plants with price 3 * 8 = 24.
//            So, it has a total price of 1930.
            return gardenMap.values.flatten().sumOf { it.borders * it.size() }
        }

        private fun scanGarden(point: Point, grid: Grid, garden: Garden) {
            if (garden.points.contains(point)) return
            garden.addPoint(point)
            garden.borders += grid.countBorders(point)

            handlePosition(grid, point.right(), garden)
            handlePosition(grid, point.down(), garden)
            handlePosition(grid, point.left(), garden)
            handlePosition(grid, point.up(), garden)
        }

        private fun handlePosition(grid: Grid, point: Point, garden: Garden) {
            if (grid.isValid(point)) {
                val pointType = grid.valueAt(point)
                if (pointType == garden.name) {
                    scanGarden(point, grid, garden)
                } else {
                    val existing = gardenMap[pointType]?.firstOrNull { it.points.contains(point) }
                    val newGarden = existing ?: Garden(pointType).also { gardenMap[pointType] = (gardenMap[pointType] ?: emptyList()) + it }
                    scanGarden(point, grid, newGarden)
                }
            }
        }

        fun part2(input: List<String>): Int {
            return 0
        }
    }
}

var globalId = 0

data class Garden(val name: Char) {
    val id: Int = globalId++
    val points: MutableList<Point> = mutableListOf()
    var borders = 0

    fun addPoint(point: Point) {
        points.add(point)
    }

    fun size() = points.size

    override fun toString(): String {
        return "Garden($name ${size()} x $borders  id=$id, points=$points)"
    }
}
