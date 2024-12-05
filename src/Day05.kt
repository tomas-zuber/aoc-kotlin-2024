fun main() {
    val input = readInput("Day05")
    val startTime = System.currentTimeMillis()
    Day05.part1(input).println()
    Day05.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day05 {
    companion object {
        fun part1(input: List<String>): Int {
            val rules = getRules(input)
            val pages = getPages(input)

            var sum = 0
            for (page in pages) {
                if (isValid(page, rules)) {
                    sum += page.middleElement()
                }
            }

            return sum
        }

        private fun getPages(input: List<String>) = input.filter { it.contains(",") }
            .map { it.split(",").map(String::toInt) }

        private fun getRules(input: List<String>) = input.filter { it.contains("|") }
            .map { it.split("|") }
            .groupBy({ it[0].toInt() }, { it[1].toInt() })

        private fun isValid(page: List<Int>, rules: Map<Int, List<Int>>): Boolean {
            page.forEachIndexed { index, num ->
                val numRules = rules[num]
                if (numRules != null && index != 0) {
                    val numsToCheck = page.subList(0, index)
                    if (numsToCheck.any { it in numRules }) {
                        return false
                    }
                }
            }
            return true
        }

        fun part2(input: List<String>): Int {
            val rules = getRules(input)
            val pages = getPages(input)

            var sum = 0
            for (page in pages) {
                sum += fixedPageMiddle(page, rules)
            }
            return sum
        }


        private fun fixedPageMiddle(page: List<Int>, rules: Map<Int, List<Int>>): Int {
            val pair = fix(page, rules)
            var wrong = pair.first
            val fixablePage = pair.second
            return if (wrong) fixablePage.middleElement() else 0
        }

        fun fix(
            page: List<Int>,
            rules: Map<Int, List<Int>>
        ): Pair<Boolean, MutableList<Int>> {
            var i = 1
            var wrong = false
            val fixablePage = page.toMutableList()
            while (i < page.size) {
                val value = fixablePage[i]
                val numRules = rules[value]
                if (numRules != null) {
                    val numsToCheck = ArrayList(fixablePage.subList(0, i))

                    val foundIndex = numsToCheck.indexOfFirst { it in numRules }
                    if (foundIndex > -1) {
                        wrong = true
                        val moved = fixablePage[foundIndex]

                        fixablePage.removeAt(foundIndex)
                        fixablePage.add(i, moved)
                        i = 1 // TODO fix 5364, 5011
                        continue
                    }
                    i++
                    continue
                }
                i++
            }
            return Pair(wrong, fixablePage)
        }
    }
}

private fun <E> List<E>.middleElement(): E = get(this.size/2)

