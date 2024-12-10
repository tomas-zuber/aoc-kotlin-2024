fun main() {
    val input = readInput("Day09")[0]
//    val input = "2333133121414131402"
    val startTime = System.currentTimeMillis()
    Day09.part1(input).println()
    Day09.part2(input).println()

    println("Runtime " + (System.currentTimeMillis() - startTime))
}

class Day09 {
    companion object {

        fun part1(input: String): Long {
            val data = parseData(input)
            prettyPrint(data) //0..111....22222
            var i = 0
            var j = data.lastIndex
            while (i < j) {
                i = data.indexOfFirstFrom(i) { it == null }
                j = data.indexOfLastFrom(j) { it != null }
                if (j == -1 || i >= j) {
                    break
                }
                data[i] = data[j]
                data[j] = null
                //prettyPrint(data)
            }
            prettyPrint(data)

            return data.mapIndexed { index, value -> index * (value ?: 0) }.sum()
        }

        private fun parseData(input: String): MutableList<Long?> {
            var id: Long = 0
            val data = mutableListOf<Long?>()
            for (i in input.indices) {
                val num = input[i].digitToInt()
                if (i % 2 == 0) { // file
                    val ids = List(num) { id }
                    data.addAll(ids)
                    id++
                } else { // empty
                    val empty = List<Long?>(num) { null }
                    data.addAll(empty)
                }
            }
            return data
        }

        private fun prettyPrint(data: MutableList<Long?>) {
            println(data.map { it ?: "." }.joinToString(""))
        }

        fun part2(input: String): Long {
            val data = parseData(input)
            prettyPrint(data) //0..111....22222


            var length = 0
            var id: Long = -1
            val idLengthList = mutableListOf<Pair<Long, Int>>()
            for (i in data.indices.reversed()) {
                if (length > 0 && id >= 0 && data[i] != id) {
                    idLengthList.add(Pair(id, length))
                    length = 0
                }
                if (data[i] != null) {
                    length++
                    id = data[i]!!
                } else {
                    length = 0
                    id = -1
                }
            }

            prettyPrint(data)
            for (idLength in idLengthList) {
                var emptyLength = 0
                for (dataIndex in data.indices) {
                    if (data[dataIndex] == null) {
                        emptyLength++
                    } else {
                        emptyLength = 0
                        if (data[dataIndex] == idLength.first) {
                            break
                        }
                    }
                    if (emptyLength == idLength.second) {
                        // remove
                        for (r in data.indices) {
                            if (data[r] == idLength.first) {
                                data[r] = null
                            }
                        }
                        // set 00...111...2...333.44.5555.6666.777.888899
                        for (s in dataIndex downTo dataIndex - emptyLength + 1) {
                            data[s] = idLength.first
                        }
                        break
                    }
                }
            }
            prettyPrint(data)

            return data.mapIndexed { index, value -> index * (value ?: 0) }.sum()
        }

    }
}

private fun MutableList<Long?>.indexOfFirstFrom(index: Int, function: (Long?) -> Boolean): Int {
    var i = index
    while (!function(this[i])) {
        i++
        if (i >= size) {
            return -1
        }
    }
    return i
}

private fun MutableList<Long?>.indexOfLastFrom(index: Int, function: (Long?) -> Boolean): Int {
    var i = index
    while (!function(this[i])) {
        i--
        if (i < 0) {
            return -1
        }
    }
    return i
}