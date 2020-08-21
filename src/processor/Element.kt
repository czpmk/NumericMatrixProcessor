package processor

import kotlin.math.floor
import java.lang.NumberFormatException
import kotlin.math.ceil

data class Element(private val _value: String) {
    lateinit var type: String
    var isValid: Boolean = false
    var value: String = ""
        set(newValue) {
            interpretArguments(newValue)
            field = newValue
        }
    val valueToPrint: String
        get() {
            return valueToPrint()
        }

    init {
        value = _value
    }

    private fun isInt(newArgument: String): Boolean {
        return try {
            newArgument.toInt()
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
            else -> {
                type = "OTHER"
                isValid = false
            }
        }
    }

    private fun valueToPrint(): String {
        return if (type == "DOUBLE") {
            if (value[0] == '-') {
                (ceil((value.toDouble() * 100)) / 100).toString()
            } else {
                (floor((value.toDouble() * 100)) / 100).toString()
            }

        } else {
            value
        }
    }

    fun add(element: Element): Element {
        return try {
            Element((this.value.toInt() + element.value.toInt()).toString())
        } catch (e: NumberFormatException) {
            Element((this.value.toDouble() + element.value.toDouble()).toString())
        }
    }

    fun multiply(element: Element): Element {
        return try {
            Element((this.value.toInt() * element.value.toInt()).toString())
        } catch (e: NumberFormatException) {
            Element((this.value.toDouble() * element.value.toDouble()).toString())
        }
    }

    fun divide(element: Element): Element {
        return Element((this.value.toDouble() / element.value.toDouble()).toString())
    }
}