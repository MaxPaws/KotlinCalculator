package com.coldkitchen.kotlincalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun OnDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        tvInput.text=""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
    }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastDot = false
        }
         else if((view as Button).text == "-" && !isOperatorAdded(tvInput.text.toString())){
            tvInput.text = view.text
            lastDot = false
        }
    }

    private fun removeZero(result: String) : String{
        var value = result
        if(result.endsWith(".0")) value = result.substring(0, value.length - 2)
        return value
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var valueFromTv = tvInput.text.toString()
            var prefix = ""
            try {
                //Checking for a minus before first number:
                if(valueFromTv.startsWith("-")){
                    prefix = "-"
                    valueFromTv = valueFromTv.substring(1)
                }

                when {
                    valueFromTv.contains("-") -> {
                        //Split string by operator:
                        val splitValue = valueFromTv.split("-")
                        var first = splitValue[0]
                        val second = splitValue[1]
                        //Getting back minus if first number had it before:
                        if(!prefix.isEmpty()) first = prefix + first
                        //Sending result to the output:
                        tvInput.text = removeZero((first.toDouble() - second.toDouble()).toString())
                    }
                    valueFromTv.contains("+") -> {
                        //Split string by operator:
                        val splitValue = valueFromTv.split("+")
                        var first = splitValue[0]
                        val second = splitValue[1]
                        //Getting back minus if first number had it before:
                        if (!prefix.isEmpty()) first = prefix + first
                        //Sending result to the output:
                        tvInput.text = (first.toDouble() + second.toDouble()).toString()
                    }
                    valueFromTv.contains("*") -> {
                        //Split string by operator:
                        val splitValue = valueFromTv.split("*")
                        var first = splitValue[0]
                        val second = splitValue[1]
                        //Getting back minus if first number had it before:
                        if (!prefix.isEmpty()) first = prefix + first
                        //Sending result to the output:
                        tvInput.text = (first.toDouble() * second.toDouble()).toString()
                    }
                    valueFromTv.contains("/") -> {
                        //Split string by operator:
                        val splitValue = valueFromTv.split("/")
                        var first = splitValue[0]
                        val second = splitValue[1]
                        //Getting back minus if first number had it before:
                        if (!prefix.isEmpty()) first = prefix + first
                        //Sending result to the output:
                        tvInput.text = (first.toDouble() / second.toDouble()).toString()
                    }
                }

                        } catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        var result = value
            if (value.startsWith("-"))
                result = value.substring(1)

        return result.contains("+") || result.contains("-") || result.contains("*")
                || result.contains("/")
    }
}
