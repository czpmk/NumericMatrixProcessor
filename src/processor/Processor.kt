package processor

object Processor {
    fun compareBySize(firstMatrix: Matrix, secondMatrix: Matrix): Boolean {
        return when {
            firstMatrix.columns != secondMatrix.columns -> false
            firstMatrix.rows != secondMatrix.rows -> false
            else -> true
        }
    }

    fun addition(firstMatrix: Matrix, secondMatrix: Matrix): Matrix {
        val newMatrix = Matrix(firstMatrix.value)
        for (row in 0 until newMatrix.rows) {
            for (column in 0 until newMatrix.columns) {
                newMatrix.add(secondMatrix.get(row, column), row, column)
            }
        }
        return newMatrix
    }

    fun scalarMultiplication(matrix: Matrix, scalar: Int): Matrix {
        val newMatrix = Matrix(matrix.value)
        for (row in 0 until newMatrix.rows) {
            for (column in 0 until newMatrix.columns) {
                newMatrix.multiply(scalar, row, column)
            }
        }
        return newMatrix
    }
}