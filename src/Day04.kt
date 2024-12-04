fun main() {
    val input = readInput("Day04")
    val startTime = System.nanoTime()
    Day04.part1(input).println()
    Day04.part2(input).println()
    println("Runtime " + (System.nanoTime() - startTime))
}

class Day04 {
    companion object {
        fun part1(input: List<String>): Int {
            return Grid(input).countXmas()
        }

        fun part2(input: List<String>): Int {
            return Grid(input).countMas()
        }
    }
}

data class Grid(val grid: List<String>) {
    private val width = grid[0].length
    private val height = grid.size

    fun countXmas(): Int {
        var count = 0
        for (row in 0 until width) {
            for (col in 0 until height) {
                if (grid[row][col] == 'X') {
                    count += countXmas(row, col)
                }
            }
        }
        return count
    }

    private fun countXmas(row: Int, col: Int): Int {
        val texts = mutableListOf<String>()

        texts.add(getText(row, col, row + 3, col))
        texts.add(getText(row, col, row, col + 3))
        texts.add(getText(row, col, row - 3, col))
        texts.add(getText(row, col, row, col - 3))

        texts.add(getText(row, col, row + 3, col + 3))
        texts.add(getText(row, col, row + 3, col - 3))
        texts.add(getText(row, col, row - 3, col - 3))
        texts.add(getText(row, col, row - 3, col + 3))
        return texts.count { it == "XMAS" }
    }

    private fun getText(row: Int, col: Int, row2: Int, col2: Int): String {
        if (row < 0 || col < 0 || row2 < 0 || col2 < 0 ||
            row >= width || col >= height || row2 >= width || col2 >= height
        ) {
            return ""
        }

        val text = mutableListOf<Char>()
        for (coords in seq(row, row2).zip(seq(col, col2))) {
            text += grid[coords.first][coords.second]
        }
        return text.joinToString("")
    }

    private fun seq(a: Int, b: Int): List<Int> = when {
        a == b -> List(4) { a }
        a < b -> (a..b).toList()
        else -> (a downTo b).toList()
    }

    fun countMas(): Int {
        var count = 0
        for (row in 0 until width - 2) {
            for (col in 0 until height - 2) {
                if (grid[row + 1][col + 1] == 'A') {
                    if (hasMas(row, col)) {
                        count++
                    }
                }
            }
        }
        return count
    }

    private fun hasMas(row: Int, col: Int): Boolean {
        val text1 = listOf(grid[row][col], grid[row + 1][col + 1], grid[row + 2][col + 2]).joinToString("")
        val text2 = listOf(grid[row + 2][col], grid[row + 1][col + 1], grid[row][col + 2]).joinToString("")

        return text1 in correctWords && text2 in correctWords
    }

    private val correctWords = listOf("MAS", "SAM")
}