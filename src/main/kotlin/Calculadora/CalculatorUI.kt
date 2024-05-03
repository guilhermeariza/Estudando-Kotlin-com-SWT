package org.example.Calculadora

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text

class CalculatorUI(private val shell: Shell){
    private val text: Text

    init {
        shell.text = "Calculadora"
        shell.layout = GridLayout(4, true)

        text = createTextField(shell)
        val buttonLabels = arrayOf("7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "=", "C", "+")
        for (label in buttonLabels) {
            createButton(shell, text, label)
        }
    }

    fun run() {
        shell.open()
        val display = shell.display
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) {
                display.sleep()
            }
        }
        display.dispose()
    }

    private fun createTextField(shell: Shell): Text {
        val text = Text(shell, SWT.BORDER)
        val gd = GridData(SWT.FILL, SWT.CENTER, true, false)
        gd.horizontalSpan = 4
        text.layoutData = gd
        return text
    }

    private fun createButton(shell: Shell, text: Text, label: String) {
        val button = Button(shell, SWT.PUSH)
        button.text = label
        val gd = GridData(SWT.FILL, SWT.FILL, true, true)
        button.layoutData = gd
        button.addListener(SWT.Selection) {
            val buttonText = (it.widget as Button).text
            when {
                buttonText == "=" -> CalculatorLogic.evaluate(text)
                buttonText == "C" -> CalculatorLogic.clear(text)
                else -> CalculatorLogic.update(text, buttonText)
            }
        }
    }
}