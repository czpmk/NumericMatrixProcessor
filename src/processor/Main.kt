package processor

import java.lang.NumberFormatException
import java.util.*

val scanner = Scanner(System.`in`)

fun nextLineToInt(): MutableList<Int> {
    val newLineSplit = scanner.nextLine().split(" ").filter { i -> i.isNotEmpty() }
    val argumentList = mutableListOf<Int>()
    for (argument in newLineSplit) {
        try {
            argumentList.add(argument.toInt())
        } catch (e: NumberFormatException) {
            continue
        }
    }
    return argumentList
}

fun readSize(): Triple<Boolean, Int, Int> {
    val numbers = nextLineToInt()
    return if (numbers.size == 2) {
        val rows = numbers[0]
        val columns = numbers[1]
        Triple(true, rows, columns)
    } else {
        Triple(false, 0, 0)
    }
}

fun readMatrix(numberOfRows: Int, numberOfColumns: Int): Matrix {
    val newMatrix = Matrix()
    for (row in 0 until numberOfRows) {
        val newRow = nextLineToInt()
        if (newRow.size == numberOfColumns) {
            newMatrix.value.add(newRow)
        } else {
            return Matrix()
        }
    }
    return newMatrix
}

fun nextAction() {
    val matrixList = mutableListOf<Matrix>()
    val (good, rows, columns) = readSize()
    if (good) matrixList.add(readMatrix(rows, columns))
    val factor = scanner.nextInt()
    when {
        matrixList.size != 1 -> println("ERROR")
//        !Processor.compareBySize(matrixList[0], matrixList[1]) -> println("ERROR")
        else -> Processor.scalarMultiplication(matrixList[0], factor).print()
    }
}

fun main() {
    nextAction()
}