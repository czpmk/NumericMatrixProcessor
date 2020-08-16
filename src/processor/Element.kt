package processor

import java.lang.NumberFormatException

data class Element(private val _value: String) {
    lateinit var type: String
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
}