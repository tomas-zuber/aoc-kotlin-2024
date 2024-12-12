fun main() {
    val input = readInput("Day11")[0]
//    val input = "125 17"
    val startTime = System.currentTimeMillis()
//    Day11.part1(input).println()
    Day11.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day11 {
    companion object {

        fun part1(input: String): Int {
            val nums = input.split(" ").map { it.toLong() }
            return blinkN(25, nums).count()
        }

        private fun blinkN(n: Int, nums: List<Long>): List<Long> {
            var result = nums
            for (i in 1..n) {
                result = blink(result)
            }
            return result
        }

        private fun blink(nums: List<Long>): List<Long> {
            return nums.flatMap { num ->
                when {
                    num == 0L -> listOf(1L)
                    num.toString().length % 2 == 0 -> {
                        val numString = num.toString()
                        val mid = numString.length / 2
                        listOf(numString.substring(0, mid).toLong(), numString.substring(mid).toLong())
                    }

                    else -> listOf(num * 2024)
                }
            }
        }

        fun part2(input: String): Long {
            var sum = 0L
            val numbers = input.split(" ").map { it.toLong() }

            val nums = blinkN(25, numbers)
            val cache = mutableMapOf<Long, Int>()

            var c = 0
            println("size " + nums.size) // 217812
            for (n in nums) {
                if (c++ % 100 == 0) println(c)

                val nums1 = blinkN(25, listOf(n))
                var sum2 = 0L
                for (n1 in nums1) {

                    if (cache.containsKey(n1)) {
                        sum2 += cache[n1]!!
                    } else {
                        val size = blinkN(25, listOf(n1)).size
                        sum2 += size
                        cache[n1] = size
                    }
                }
                sum += sum2
            }

            return sum
        }

    }
}
