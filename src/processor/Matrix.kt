package processor

import kotlin.math.pow

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

    fun sum(secondMatrix: Matrix, firstMatrix: Matrix = this): Matrix {
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
            sum = sum.add(firstMatrix.get(row, i).multiply(secondMatrix.get(i, column)))
        }
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

    fun transpose(transposeBy: Int = 1): Matrix {
        val newMatrix = Matrix()
        when (transposeBy) {
            // Main diagonal
            1 -> {
                for (column in 0 until this.columns) {
                    val newRow = mutableListOf<Element>()
                    for (row in 0 until this.rows) {
                        newRow.add(this.get(row, column))
                    }
                    newMatrix.appendRow(newRow)
                }
            }
            // Side diagonal
            2 -> {
                for (column in (this.columns - 1) downTo 0) {
                    val newRow = mutableListOf<Element>()
                    for (row in (this.rows - 1) downTo 0) {
                        newRow.add(this.get(row, column))
                    }
                    newMatrix.appendRow(newRow)
                }
            }
            // Vertical line
            3 -> {
                for (row in 0 until this.rows) {
                    val newRow = mutableListOf<Element>()
                    for (column in (this.columns - 1) downTo 0) {
                        newRow.add(this.get(row, column))
                    }
                    newMatrix.appendRow(newRow)
                }
            }
            // Horizontal line
            4 -> {
                for (row in (this.rows - 1) downTo 0) {
                    val newRow = mutableListOf<Element>()
                    for (column in 0 until this.columns) {
                        newRow.add(this.get(row, column))
                    }
                    newMatrix.appendRow(newRow)
                }
            }
        }
        return newMatrix
    }

    fun determinant1x1(): Element {
        return this.get(0, 0)
    }

    fun determinant2x2(): Element {
        val plusSum = this.get(0, 0).multiply(this.get(1, 1))
        val minusSum = this.get(0, 1).multiply(this.get(1, 0))
        return plusSum.add(minusSum.multiply(Element("-1")))
    }

    fun determinantOfBigMatrix(): Element {
        var determinant = Element("0")
        for (i in 0 until rows) {
            // determine sign of minor
            val sign = Element((-1.0).pow(i + 0).toInt().toString())
            // get minor
            val filteredMatrix = this.value.filterIndexed { idx, _ -> idx != i }.toMutableList()
            for (row in filteredMatrix.indices) {
                filteredMatrix[row] = filteredMatrix[row].filterIndexed { idx, _ -> idx != 0 }.toMutableList()
            }
            val minor = Matrix(filteredMatrix)
            // sum determinant
            determinant = when (minor.rows) {
                2 -> determinant.add(this.get(i, 0).multiply(minor.determinant2x2().multiply(sign)))
                else -> {
                    determinant.add(this.get(i, 0).multiply(minor.determinantOfBigMatrix().multiply(sign)))
                }
            }
        }
        return determinant
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
