fun main() {
    val input = readInput("Day12")
    val input2 = (
            "RRRRIICCFF\n" +
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
        private val processable = mutableSetOf<Point>()
        private val processed = mutableSetOf<Point>()

        fun part1(input: List<String>): Int {
            val grid = Grid(input)
            grid.print()

            processable.add(Point(0, 0))
            while (processable.isNotEmpty()) {
                val type = grid.valueAt(processable.first())
                val garden = Garden(type, grid)
                gardenMap[type] = (gardenMap[type] ?: mutableListOf()) + garden
                scanGarden(processable.first(), grid, garden)
            }

            gardenMap.values.forEach { println("$it") }
            return gardenMap.values.flatten().sumOf { it.borders * it.size() }
        }

        private fun scanGarden(point: Point, grid: Grid, garden: Garden) {
            processable.remove(point)
            if (garden.points.contains(point)) return
            garden.addPoint(point)
            processed.add(point)
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
                } else if (!processed.contains(point)) {
                    processable.add(point)
                }
            }
        }

        fun part2(input: List<String>): Int {
            val grid = Grid(input)
            grid.print()

            processable.add(Point(0, 0))
            while (processable.isNotEmpty()) {
                val type = grid.valueAt(processable.first())
                val garden = Garden(type, grid)
                gardenMap[type] = (gardenMap[type] ?: mutableListOf()) + garden
                scanGarden(processable.first(), grid, garden)
            }

            gardenMap.values.forEach { println("$it") }
            return gardenMap.values.flatten().sumOf { it.sides() * it.size() }
        }
    }
}

var globalId = 0

data class Garden(val name: Char, val grid: Grid) {
    val id: Int = globalId++
    val points: MutableList<Point> = mutableListOf()
    var borders = 0

    fun addPoint(point: Point) {
        points.add(point)
    }

    fun size() = points.size

    override fun toString(): String {
        return "Garden($name ${size()} x ${sides()} /$borders id=$id, points=$points)"
    }

    fun sides(): Int {
        val xGroup = points.groupBy { it.x }
        val yGroup = points.groupBy { it.y }

        val leftEdges = getEdges(xGroup, Point::left).values
        val rightEdges = getEdges(xGroup, Point::right).values
        val topEdges = getEdges(yGroup, Point::up).values
        val bottomEdges = getEdges(yGroup, Point::down).values
        // println("L edge: ${leftEdges.size} $leftEdges")
        // println("R edge: ${rightEdges.size} $rightEdges")
        // println("T edge: ${topEdges.size} $topEdges")
        // println("B edge: ${bottomEdges.size} $bottomEdges")

        return con(leftEdges, Point::y) + con(rightEdges, Point::y) + con(topEdges, Point::x) + con(bottomEdges, Point::x)
    }

    private fun con(edges: Collection<List<Point>>, fn: (Point) -> Int) = edges.sumOf { points -> countSteps(points.map { fn(it) }.sorted()) }

    private fun countSteps(sorted: List<Int>) : Int {
        var result = 0
        var lastVal:Int? = null
        for (i in sorted.indices) {
            if (sorted[i] - 1 != lastVal) {
                result++
            }
            lastVal = sorted[i]
        }
        return result//.also { println("sorted $sorted -> $it") }
    }

    private fun getEdges(xGroup: Map<Int, List<Point>>, moveFn: (Point) -> Point ) =
        xGroup.mapValues { (_, points) ->
            points.filter {
                val side = moveFn(it)
                !grid.isValid(side) || grid.valueAt(it) != grid.valueAt(side)
            }
        }.filterValues { it.isNotEmpty() }
}
