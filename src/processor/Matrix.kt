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

    fun determinantOfSmallMatrix(): String {
        return when (this.columns) {
            1 -> this.get(0, 0).value
            else -> {
                val plusSum = this.get(0, 0).multiply(this.get(1, 1))
                val minusSum = this.get(0, 1).multiply(this.get(1, 0))
                plusSum.add(minusSum.multiply(Element("-1"))).value
            }
        }
    }

    fun determinantOfBigMatrix(): String {
        var determinant = Element("0")
        for (startRow in 0 until rows) {
            var plusProduct = Element("1")
            var minusProduct = Element("-1")
            for (i in 0 until columns) {
                val rowIdx = (startRow + i) % columns
                plusProduct = plusProduct.multiply(this.get(rowIdx, i))
                minusProduct = minusProduct.multiply(this.get(rowIdx, (columns - 1 - i)))
            }
            determinant = determinant.add(plusProduct).add(minusProduct)
        }
        return determinant.value
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
//1 2 0 0 0
//−2 4 0 0 0
//4 −781 1 0 0
//3208 −5 −54 3 0
//−8379 −9 0 −879 3
