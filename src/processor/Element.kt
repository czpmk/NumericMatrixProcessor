package processor

import java.lang.NumberFormatException

data class Element(private val _value: String) {
    private lateinit var type: String
    var isValid: Boolean = false
    var value: String = ""
        set(newValue) {
            interpretArguments(newValue)
            field = newValue
        }

    init {
        value = _value
    }

    private fun isInt(newArgument: String): Boolean {
        return try {
            newArgument.toLong()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isDouble(newArgument: String): Boolean {
        return try {
            newArgument.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isBigInt(newArgument: String): Boolean {
        if (newArgument[0] in '0'..'9' || (newArgument[0] == '-' && newArgument.length > 1)) {
            for (i in 1 until newArgument.length) {
                if (newArgument[i] !in '0'..'9') {
                    return false
                }
            }
            return true
        } else {
            return false
        }
    }

    private fun interpretArguments(newArgument: String) {
        when {
            newArgument.isEmpty() -> {
                type = "EMPTY"
                isValid = false
            }
            isInt(newArgument) -> {
                type = "INT"
                isValid = true
            }
            isDouble(newArgument) -> {
                type = "DOUBLE"
                isValid = true
            }
            isBigInt(newArgument) -> {
                type = "BIGINT"
                isValid = true
            }
            else -> {
                type = "OTHER"
                isValid = false
            }
        }
    }

    fun add(elementToAdd: Element): Element {
        return when {
            (this.type == "BIGINT" || elementToAdd.type == "BIGINT") -> {
                val newValue = this.value.toBigInteger() + elementToAdd.value.toBigInteger()
                Element(newValue.toString())
            }
            (this.type == "DOUBLE" || elementToAdd.type == "DOUBLE") -> {
                val newValue = this.value.toDouble() + elementToAdd.value.toDouble()
                Element(newValue.toString())
            }
            else -> {
                val newValue = this.value.toInt() + elementToAdd.value.toInt()
                Element(newValue.toString())
            }
        }
    }

    fun multiply(factor: Element): Element {
         return when {
            (this.type == "BIGINT" || factor.type == "BIGINT") -> {
                val newValue = this.value.toBigInteger().multiply(factor.value.toBigInteger())
                Element(newValue.toString())
            }
            (this.type == "DOUBLE" || factor.type == "DOUBLE") -> {
                val newValue = this.value.toDouble() * factor.value.toDouble()
                Element(newValue.toString())
            }
            else -> {
                val newValue = this.value.toInt() * factor.value.toInt()
                Element(newValue.toString())
            }
        }
    }
}