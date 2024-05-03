package org.example.Calculadora

import org.eclipse.swt.widgets.Text

object CalculatorLogic {
    fun evaluate(text: Text) {
        try {
            val result = eval(text.text)
            text.text = result.toString()
        } catch (e: Exception) {
            text.text = "Erro"
        }
    }
    fun clear(text: Text) {
        text.text = ""
    }

    fun update(text: Text, buttonText: String) {
        text.text += buttonText
    }

    private fun eval(expression: String): Double {
        var currentNumber = ""
        var result = 0.0
        var lastOperator = '+'

        for (char in expression) {
            if (char.isDigit() || char == '.') {
                currentNumber += char
            } else {
                result = applyOperation(result, currentNumber.toDouble(), lastOperator)
                lastOperator = char
                currentNumber = ""
            }
        }

        result = applyOperation(result, currentNumber.toDouble(), lastOperator)

        return result
    }

    private fun applyOperation(result: Double, number: Double, operator: Char): Double {
        return when (operator) {
            '+' -> result + number
            '-' -> result - number
            '*' -> result * number
            '/' -> result / number
            else -> throw IllegalArgumentException("Operador inválido: $operator")
        }
    }
}
