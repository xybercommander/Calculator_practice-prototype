package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) {findViewById<TextView>(R.id.operation)}

    // Variables to hold the operands and type of calculation

    private var operand1: Double? = null //We have to check whether it is null or not
    private var pendingOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // EditTexts
        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)

        // Data input buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)

        // Operation Buttons
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)

        // OnClickListener for the data buttons
        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        // Setting the OnClickListeners for the Data
        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        // OnClickListener for the operations
        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()

            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }

            pendingOperation = op
            displayOperation.text = pendingOperation
        }

        // Setting the OnClickListeners for the operations
        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)


    }

    //Writing the function outside the onCreate method

    // performOperation function
    private fun performOperation(value: Double, operation: String) {

        if(operand1 == null){
            operand1 = value
        } else {
            if(pendingOperation == "="){
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" ->  if(value == 0.0){
                            operand1 = Double.NaN // Handle attempt to divide by zero
                        } else {
                            operand1 = operand1!! / value
                        }
                "*" -> operand1 = operand1!! * value
                "+" -> operand1 = operand1!! + value
                "-" -> operand1 = operand1!! - value
            }

        }

        result.setText(operand1.toString())
        newNumber.setText("")
    }

}
