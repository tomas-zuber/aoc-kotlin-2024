import java.util.LinkedHashSet

fun main() {
    val input = readInput("Day06")
    val startTime = System.currentTimeMillis()
    Day06.part1(input).println()
    Day06.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day06 {
    companion object {
        fun part1(input: List<String>): Int {

            val grid = Grid6(input)

            return getVisited(grid).size
        }

        private fun getVisited(grid: Grid6): MutableSet<Pair<Int, Int>> {
            var direction = 0
            var position = grid.findStart()
            val visited = LinkedHashSet<Pair<Int, Int>>().apply {
                add(position)
            }
            val corners = mutableSetOf<Triple<Int, Int, Int>>()

            while (true) {
                val newPosition = getNewPosition(direction, position)

                if (!grid.isValid(newPosition)) {
                    return visited;
                }
                if (grid.isBarrier(newPosition)) {
                    val corner = Triple(position.first, position.second, direction)
                    if (corners.contains(corner)) {
                        return mutableSetOf()
                    }
                    corners.add(corner);
                    direction = (direction + 1) % 4
                    continue
                }
                position = newPosition
                visited.add(newPosition)
            }
        }

        private fun getNewPosition(dir: Int, position: Pair<Int, Int>) = when (dir) {
            0 -> Pair(position.first, position.second - 1) // up
            1 -> Pair(position.first + 1, position.second) // right
            2 -> Pair(position.first, position.second + 1) // down
            3 -> Pair(position.first - 1, position.second) // left
            else -> error("impossible")
        }

        fun part2(input: List<String>): Int {
            val grid = Grid6(input)
            val possibleBarriers = getVisited(grid).drop(1)
            return possibleBarriers.count {
                val newGrid = Grid6(input, it)
                getVisited(newGrid).isEmpty()
            }
        }

    }
}

data class Grid6(val grid: List<String>, val barrier: Pair<Int, Int>? = null) {
    private val width = grid[0].length
    private val height = grid.size

    fun findStart(): Pair<Int, Int> {
        grid.forEachIndexed { row, s ->
            val col = s.indexOf('^')
            if (col != -1) {
                return Pair(col, row)
            }
        }
        error("Incorrect input")
    }

    fun isValid(position: Pair<Int, Int>): Boolean {
        return position.first in 0..<width &&
                position.second in 0..<height
    }

    fun isBarrier(position: Pair<Int, Int>): Boolean {
        return grid[position.second][position.first] == '#' ||
                barrier == position
    }
}