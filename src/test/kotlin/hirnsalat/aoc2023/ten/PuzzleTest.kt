package hirnsalat.aoc2023.ten

import hirnsalat.hirnsalat.aoc2023.ten.Coordinates
import hirnsalat.hirnsalat.aoc2023.ten.Puzzle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private const val smallInput = ".....\n" +
        ".S-7.\n" +
        ".|.|.\n" +
        ".L-J.\n" +
        ".....\n"

private const val zeroInsideButWeird = "........\n" +
        ".S----7.\n" +
        ".|F--7|.\n" +
        ".LJ..LJ.\n" +
        "........\n"
private const val startIsAProblem = "........\n" +
        ".F----7.\n" +
        ".S....|.\n" +
        ".L----J.\n" +
        "........\n"

class PuzzleTest {
    @Test
    fun testExample() {
        val p = Puzzle(smallInput)

        assertEquals(4, p.solve())
    }

    @Test
    fun testBonus() {
        val p = Puzzle("OF----7F7F7F7F-7OOOO\n" +
                "O|F--7||||||||FJOOOO\n" +
                "O||OFJ||||||||L7OOOO\n" +
                "FJL7L7LJLJ||LJIL-7OO\n" +
                "L--JOL7IIILJS7F-7L7O\n" +
                "OOOOF-JIIF7FJ|L7L7L7\n" +
                "OOOOL7IF7||L7|IL7L7|\n" +
                "OOOOO|FJLJ|FJ|F7|OLJ\n" +
                "OOOOFJL-7O||O||||OOO\n" +
                "OOOOL---JOLJOLJLJOOO\n")

        assertEquals(8, p.bonus())
    }

    @Test
    fun testBonus2() {
        val p = Puzzle(zeroInsideButWeird)

        assertEquals(0, p.bonus())
    }

    @Test
    fun testBonus3() {
        val p = Puzzle(startIsAProblem)

        assertEquals(4, p.bonus())
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

        assertEquals(Coordinates(1,1), p.findStart())
    }

    @Test
    fun testConnections() {
        val p = Puzzle(smallInput)

        assertEquals(listOf(Coordinates(2,1),Coordinates(1,2)), p.incomingConnections(Coordinates(1,1)))
    }
}