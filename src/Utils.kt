import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

data class Grid(val grid: List<String>) {
    private val width = grid[0].length
    private val height = grid.size

    val numGrid = grid.map {
        it.map { c -> c.digitToInt() }
    }

    fun isValid(position: Point): Boolean {
        return position.x in 0..<width &&
                position.y in 0..<height
    }

    fun numValueAt(position: Point): Int? = if(isValid(position)) numGrid[position.y][position.x] else null
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    operator fun minus(other: Point): Point = Point(this.x - other.x, this.y - other.y)

    fun up() = Point(this.x, this.y - 1)
    fun down() = Point(this.x, this.y + 1)
    fun left() = Point(this.x - 1, this.y)
    fun right() = Point(this.x + 1, this.y)
}

typealias Path = List<Point>