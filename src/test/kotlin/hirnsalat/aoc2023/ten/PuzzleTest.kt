package hirnsalat.aoc2023.ten

import hirnsalat.hirnsalat.aoc2023.ten.Puzzle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val smallInput = ".....\n" +
        ".S-7.\n" +
        ".|.|.\n" +
        ".L-J.\n" +
        ".....\n"

class PuzzleTest {
    @Test
    fun testExample() {
        val p = Puzzle(smallInput)

        assertEquals(4, p.solve())
    }

    @Test
    fun testCharAt() {
        val p = Puzzle(smallInput)

        assertEquals('S', p.charAt(1,1))
        assertEquals('L', p.charAt(1,3))
    }

    @Test
    fun testFindStart() {
        val p = Puzzle(smallInput)

        assertEquals(Pair(1,1), p.findStart())
    }

    @Test
    fun testConnections() {
        val p = Puzzle(smallInput)

        assertEquals(listOf(Pair(2,1),Pair(1,2)), p.connections(1,1))
    }
}