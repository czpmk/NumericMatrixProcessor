package processor

class Matrix(_value: MutableList<MutableList<Element>> = mutableListOf()) {
    var value: MutableList<MutableList<Element>> = _value
    val rows: Int
        get() {
            return value.size
        }
    private val columns: Int
        get() {
            return value[0].size
        }
    val isValid: Boolean
        get() {
            for (row in this.value) {
                for (element in row) {
                    if (!element.isValid) return false
                }
            }
            return true
        }

    fun get(numberOfRow: Int, numberOfColumn: Int): Element {
        return try {
            value[numberOfRow][numberOfColumn]
        } catch (e: IndexOutOfBoundsException) {
            println("Out of bounds at Matrix.get($numberOfRow, $numberOfColumn) size: ($rows, $columns)")
            Element("")
        }
    }

    fun set(newElement: Element, row: Int, column: Int) {
        try {
            this.value[row][column] = newElement
        } catch (e: IndexOutOfBoundsException) {
            println("Out of bounds at Matrix.set(${newElement.value}, $row, $column) size: ($rows, $columns)")
        }
    }

    private fun appendRow(newRow: MutableList<Element>) {
        this.value.add(newRow)
    }

    fun ableToMultiplyBy(secondMatrix: Matrix): Boolean {
        return this.columns == secondMatrix.rows
    }

    fun sameSizeAs(secondMatrix: Matrix): Boolean {
        return when {
            this.columns != secondMatrix.columns -> false
            this.rows != secondMatrix.rows -> false
            else -> true
        }
    }

    fun sum(secondMatrix: Matrix, firstMatrix: Matrix = this): Matrix{
        val newMatrix = Matrix()
        for (row in 0 until firstMatrix.rows) {
            val newRow = mutableListOf<Element>()
            for (column in 0 until firstMatrix.columns) {
                newRow.add(firstMatrix.get(row, column).add(secondMatrix.get(row, column)))
            }
            newMatrix.appendRow(newRow)
        }
        return newMatrix
    }

    fun constantMultiplication(constant: Element, matrix: Matrix = this): Matrix {
        val newMatrix = Matrix()
        for (row in 0 until rows) {
            val newRow = mutableListOf<Element>()
            for (column in 0 until columns) {
                newRow.add(matrix.get(row, column).multiply(constant))
            }
            newMatrix.appendRow(newRow)
        }
        return newMatrix
    }

    private fun dotProduct(firstMatrix: Matrix, secondMatrix: Matrix, row: Int, column: Int): Element {
        var sum = Element("0")
        for (i in 0 until firstMatrix.columns) {
            print("${firstMatrix.get(row, i).value} * ${secondMatrix.get(i, column).value} + ")
            sum = sum.add(firstMatrix.get(row, i).multiply(secondMatrix.get(i, column)))
        }
        print(" = " + sum.value)
        println()
        return sum
    }

    fun matrixMultiplication(secondMatrix: Matrix, firstMatrix: Matrix = this): Matrix {
        val newMatrix = Matrix()
        for (row in 0 until firstMatrix.rows) {
            val newRow = mutableListOf<Element>()
            for (column in 0 until secondMatrix.columns) {
                newRow.add(dotProduct(firstMatrix, secondMatrix, row, column))
            }
            newMatrix.value.add((newRow))
        }
        return newMatrix
    }

    fun print() {
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                print("${this.get(row, column).value} ")
            }
            println()
        }
    }
}