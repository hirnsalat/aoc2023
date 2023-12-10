package hirnsalat.hirnsalat.aoc2023.ten

import java.io.File

fun main() {
    val input = File("src/main/resources/ten.txt").readText()
    val p = Puzzle(input)
    println("main: " + p.solve())
    println("bonus: " + p.bonus())
}