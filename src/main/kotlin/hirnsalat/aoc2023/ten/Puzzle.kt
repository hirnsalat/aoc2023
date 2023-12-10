package hirnsalat.hirnsalat.aoc2023.ten

data class Coordinates(val x : Int, val y : Int) {
    fun left() = Coordinates(x-1, y)
    fun right() = Coordinates(x+1, y)
    fun up() = Coordinates(x, y-1)
    fun down() = Coordinates(x, y+1)
}

fun verticalEdges(c : Char?) =
    when(c) {
        // going up
        'F' -> -1
        'J' -> -1
        // going down
        'L' -> 1
        '7' -> 1
        '|' -> 2
        else -> 0
    }

class Puzzle (input : String) {
    val input = input.lines()

    fun log(coords: Coordinates) {
        System.err.print(charAt(coords))
        System.err.print(" ")
        System.err.print(coords)
        System.err.println()
    }

    fun solve() : Int {
        return getLoop().count()/2
    }

    fun bonus() : Int {
        val loop = getLoop()
        val lines = loop.map { it.y }.toSet()
        var insideTiles = 0

        for(line in lines) {
            val loopTilesInLine =
                loop.filter { it.y == line }
                    .toSortedSet(Comparator.comparingInt { it.x })

            var verticalEdges = 0
            var lastEdge = 0

            for(loopTile in loopTilesInLine) {
                if(verticalEdges % 2 == 0 && verticalEdges % 4 != 0) {
                    val length = loopTile.x - lastEdge - 1
                    // logEmptyStretch(line, lastEdge, loopTile.x, length)
                    insideTiles += length
                }

                verticalEdges += verticalEdges(charAt(loopTile))
                lastEdge = loopTile.x
            }
        }

        return insideTiles
    }

    private fun logEmptyStretch(line: Int, x1: Int, x2: Int, length: Int) {
        if(length != 0) {
            System.err.print("empty stretch in line $line")
            System.err.print(" from $x1 (${charAt(x1, line)}) to $x2 (${charAt(x2, line)}), length ${length}")
            System.err.println()
        }
    }

    fun getLoop() : Set<Coordinates> {
        val ret = mutableSetOf<Coordinates>()
        var prev = findStart() // coordinates of S!
        var current = prev // also S
        var next = incomingConnections(current).toSet() // neighbors of S

        ret += prev

        while(next.isNotEmpty()) {
            // advance current to next tile
            current = next.first()
            // get connections for tile, but exclude the direction we came from
            next = incomingConnections(current)
                .filter { it != prev }
                .intersect(outgoingConnections(current))
            // remember position for next step
            prev = current

            ret += prev
        }

        return ret
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

    fun charAt(coords : Coordinates) =
        charAt(coords.x, coords.y)

    fun findStart() : Coordinates {
        for(lineNr in 0..<input.count()) {
            val startIndex = input[lineNr].indexOf('S')

            if(startIndex > -1) {
                return Coordinates(startIndex, lineNr)
            }
        }
        throw RuntimeException("Malformed puzzle input!")
    }

    fun outgoingConnections(coords: Coordinates) : Set<Coordinates> {
        val char = charAt(coords)

        return when (char) {
            'F' -> setOf(coords.right(), coords.down())
            'J' -> setOf(coords.left(), coords.up())
            '7' -> setOf(coords.left(), coords.down())
            'L' -> setOf(coords.right(), coords.up())
            '-' -> setOf(coords.right(), coords.left())
            '|' -> setOf(coords.up(), coords.down())
            else -> setOf()
        }
    }

    fun incomingConnections(coords : Coordinates) : Set<Coordinates> {
        var ret = setOf<Coordinates>()

        fun testAndAdd(coords : Coordinates, allowedChars : List<Char>) {
            if (allowedChars.contains(charAt(coords))) {
                ret = ret.plus(coords)
            }
        }

        //left
        testAndAdd(coords.left(), listOf('-', 'F', 'L'))
        //up
        testAndAdd(coords.up(), listOf('|', '7', 'F'))
        //right
        testAndAdd(coords.right(), listOf('-', '7', 'J'))
        //down
        testAndAdd(coords.down(), listOf('|', 'L', 'J'))

        return ret
    }

}