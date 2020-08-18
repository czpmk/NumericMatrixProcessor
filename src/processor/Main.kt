package processor

import java.lang.NumberFormatException
import java.util.*

val scanner = Scanner(System.`in`)
const val menuMessage = "~  Available actions:\n" +
        "1. Add matrices\n" +
        "2. Multiply matrix to a constant\n" +
        "3. Multiply matrices\n" +
        "4. Transpose matrix\n" +
        "0. Exit\n"
const val transpositionMenu = "1. Main diagonal\n" +
        "2. Side diagonal\n" +
        "3. Vertical line\n" +
        "4. Horizontal line\n"

fun readSize(): Pair<Int, Int> {
    while (true) {
        print("~  Enter size of a matrix (rows, columns): ")
        try {
            val rows = scanner.nextInt()
            val columns = scanner.nextInt()
            if (rows < 1 || columns < 1) {
                println("Invalid size, required rows <= 1 columns <= 1")
                continue
            }
            return Pair(rows, columns)
        } catch (e: NumberFormatException) {
            println("Invalid value, integer required")
        }
    }
}

fun readMatrix(numberOfRows: Int, numberOfColumns: Int): Matrix {
    println("~  Enter first matrix ($numberOfRows x $numberOfColumns): ")
    while (true) {
        val newMatrix = Matrix()
        while (newMatrix.rows < numberOfRows) {
            val newRow = mutableListOf<Element>()
            while (newRow.size < numberOfColumns) {
                newRow.add(Element(scanner.next()))
            }
            newMatrix.value.add(newRow)
        }
        if (newMatrix.isValid) {
            return newMatrix
        } else {
            println("Matrix contains invalid elements: \n")
            for (row in newMatrix.value) {
                print(row.filter { i -> !i.isValid }.joinToString(" ") + " ")
            }
            println("\n~ Please, enter the matrix once again: ")
        }
    }
}

fun readConstant(): Element {
    print("~  Enter the constant: ")
    while (true) {
        val constant = Element(scanner.next())
        if (constant.isValid) {
            return constant
        } else {
            println("The number $constant is not valid")
            println("\n~ Please, enter the constant once again: ")
        }
    }

}

fun additionPipeline() {
    val (firstRows, firstColumns) = readSize()
    val firstMatrix = readMatrix(firstRows, firstColumns)
    val (secondRows, secondColumns) = readSize()
    val secondMatrix = readMatrix(secondRows, secondColumns)
    when {
        !firstMatrix.sameSizeAs(secondMatrix) -> {
            println("Error, matrices sizes do not match!")
        }
        else -> {
            println("The addition result:")
            firstMatrix.sum(secondMatrix).print()
        }
    }
}

fun constantMultiplicationPipeline() {
    val (numberOfRows, numberOfColumns) = readSize()
    val firstMatrix = readMatrix(numberOfRows, numberOfColumns)
    val constant = readConstant()
    println("\nThe multiplication result:")
    firstMatrix.constantMultiplication(constant).print()
}

fun matrixMultiplicationPipeline() {
    val (firstRows, firstColumns) = readSize()
    val firstMatrix = readMatrix(firstRows, firstColumns)
    val (secondRows, secondColumns) = readSize()
    val secondMatrix = readMatrix(secondRows, secondColumns)
    when {
        !firstMatrix.ableToMultiplyBy(secondMatrix) -> {
            println("Error, matrices sizes do not allow for multiplication!")
        }
        else -> {
            println("The multiplication result:")
            firstMatrix.matrixMultiplication(secondMatrix).print()
        }
    }
}

fun transpositionPipeline() {
    correctLoop@ while (true) {
        println()
        print(transpositionMenu)
        print("~  Your choice: ")
        val newChoice = scanner.next()
        if (newChoice in listOf("1", "2", "3", "4")) {
            val (rows, columns) = readSize()
            val matrix = readMatrix(rows, columns)
            matrix.transpose(newChoice.toInt()).print()
            break@correctLoop
        } else {
            println("Invalid choice\n" +
                    "Please enter the transposition type once again")
        }
    }
}

fun nextAction() {
    println("~  Welcome to Numeric Matrix Processor, have fun!")
    actionLoop@ while (true) {
        print(menuMessage)
        print("~  Your choice: ")
        when (val actionIdentifier = scanner.next()) {
            "0" -> {
                println("~  Bye!")
                break@actionLoop
            }
            "1" -> additionPipeline()
            "2" -> constantMultiplicationPipeline()
            "3" -> matrixMultiplicationPipeline()
            "4" -> transpositionPipeline()
            else -> println("Invalid action: There is no action = $actionIdentifier")
        }
        println()
    }
}

fun main() {
    nextAction()
}