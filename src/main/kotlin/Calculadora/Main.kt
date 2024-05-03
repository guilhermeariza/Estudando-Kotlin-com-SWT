package org.example.Calculadora

import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

fun main() {
    val display = Display()
    val shell = Shell(display)
    CalculatorUI(shell).run()
}
