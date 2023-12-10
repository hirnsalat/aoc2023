package hirnsalat.hirnsalat.aoc2023.ten

data class Coordinates(val x : Int, val y : Int) {
    fun left() = Coordinates(x-1, y)
    fun right() = Coordinates(x+1, y)
    fun up() = Coordinates(x, y-1)
    fun down() = Coordinates(x, y+1)

    operator fun plus(other : Coordinates) =
        Coordinates(x + other.x, y + other.y)
    operator fun minus(other : Coordinates) =
        Coordinates(x - other.x, y - other.y)
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
        val lines = loop.map { it.y }.distinct()
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

                val connections =
                    if(charAt(loopTile) == 'S') incomingConnections(loopTile).sortedBy { it.x }
                        else outgoingConnections(loopTile).sortedBy { it.x }
                verticalEdges += (connections[0] - connections[1]).y

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

    fun getLoop() : List<Coordinates> {
        val ret = mutableListOf<Coordinates>()
        var prev = findStart() // coordinates of S!
        var current = prev // also S
        var next = incomingConnections(current).first() // a neighbor of S

        ret += prev

        while(charAt(next) != 'S') {
            // advance current to next tile
            current = next
            // get connections for tile, but exclude the direction we came from
            next = outgoingConnections(current)
                .first { it != prev }
            // remember position for next step
            prev = current
            // collect the loop
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

    fun outgoingConnections(coords: Coordinates) : List<Coordinates> {
        val char = charAt(coords)

        return when (char) {
            'F' -> listOf(coords.down(), coords.right())
            'J' -> listOf(coords.left(), coords.up())
            '7' -> listOf(coords.left(), coords.down())
            'L' -> listOf(coords.up(), coords.right())
            '-' -> listOf(coords.left(), coords.right())
            '|' -> listOf(coords.up(), coords.down())
            else -> listOf()
        }
    }

    fun incomingConnections(coords : Coordinates) =
        listOf(coords.left(), coords.right(), coords.down(), coords.up())
            .filter { outgoingConnections(it).contains(coords) }

}