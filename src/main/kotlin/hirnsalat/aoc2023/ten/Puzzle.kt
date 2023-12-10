package hirnsalat.hirnsalat.aoc2023.ten

class Puzzle (input : String) {
    val input = input.lines()

    fun solve() : Int {
        var count = 1
        var prev = findStart() // coordinates of S!
        var current = prev // also S
        var next = connections(current) // neighbors of S

        while(next.count() > 0) {
            // advance current to next tile
            current = next.first()
            // get connections for tile, but exclude the direction we came from
            next = connections(current).filter { it != prev }
            // count up
            count += 1
            // remember position for next step
            prev = current
        }

        return count/2;
    }

    /**
     * coordinate system starts at top left. x is to the right, y is down.
     */
    fun charAt(x : Int, y : Int): Char? {
        try {
            return input[y][x]
        } catch (e : IndexOutOfBoundsException) {
            return null
        }
    }

    fun charAt(coords : Pair<Int,Int>) =
        charAt(coords.first, coords.second)

    fun findStart() : Pair<Int, Int> {
        for(lineNr in 0..<input.count()) {
            val startIndex = input[lineNr].indexOf('S')

            if(startIndex > -1) {
                return Pair(startIndex, lineNr)
            }
        }
        throw RuntimeException("Malformed puzzle input!")
    }

    fun connections(x : Int, y : Int) : List<Pair<Int,Int>> {
        var ret = listOf<Pair<Int,Int>>()

        fun testAndAdd(coords : Pair<Int, Int>, allowedChars : List<Char>) {
            if (allowedChars.contains(charAt(coords))) {
                ret = ret.plus(coords)
            }
        }

        //left
        testAndAdd(Pair(x-1, y), listOf('-', 'F', 'L'))
        //up
        testAndAdd(Pair(x, y-1), listOf('|', '7', 'F'))
        //right
        testAndAdd(Pair(x+1, y), listOf('-', '7', 'J'))
        //down
        testAndAdd(Pair(x, y+1), listOf('|', 'L', 'J'))

        return ret;
    }

    fun connections(coords : Pair<Int,Int>) =
        connections(coords.first, coords.second)
}