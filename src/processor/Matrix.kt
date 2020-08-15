package processor

data class Matrix(val value: MutableList<MutableList<Int>> = mutableListOf()) {
    var rows: Int
        get() {
            return value.size
        }
    var columns: Int
        get() {
            return value[0].size
        }

    init {
        if (value.isEmpty()) {
            rows = 0
            columns = 0
        } else {
            rows = value.size
            columns = value[0].size
        }
    }

    fun get(numberOfRow: Int, numberOfColumn: Int): Int {
        return when {
            numberOfRow !in 0 until rows -> -255
            numberOfColumn !in 0 until columns -> -255
            else -> value[numberOfRow][numberOfColumn]
        }
    }

    fun set(value: Int, row: Int, column: Int) {
        this.value[row][column] = value
    }

    fun add(value: Int, row: Int, column: Int) {
        val newValue = this.get(row, column) + value
        this.set(newValue, row, column)
    }

    fun multiply(value: Int, row: Int, column: Int) {
        val newValue = value * this.get(row, column)
        this.set(newValue, row, column)
    }

    fun print() {
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                print("${this.get(row, column)} ")
            }
            println()
        }
    }
}