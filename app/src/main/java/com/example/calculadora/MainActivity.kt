package com.example.calculadora

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val suma  = "+"
    val resta = "-"
    val multipicacion = "*"
    val division = "/"

    var operacionActual = ""

    var primerNumero : Double = Double.NaN
    var segundoNumero : Double = Double.NaN

    lateinit var resultadoTemporal : TextView
    lateinit var resultadoFinal : TextView

    lateinit var formatoDecimal : DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        formatoDecimal = DecimalFormat("#.#########")
        resultadoTemporal = findViewById(R.id.resultadoTemporal) // corregido
        resultadoFinal = findViewById(R.id.resultadoFinal)       // corregido
    }

    fun agregarNumero(b : View){
        val boton : Button = b as Button
        val numero = boton.text.toString()

        if (resultadoFinal.text == "0") {
            resultadoFinal.text = numero
        } else {
            resultadoFinal.text = resultadoFinal.text.toString() + numero
        }
    }

    fun agregarDecimal(b : View){
        if (!resultadoFinal.text.contains(".")) {
            resultadoFinal.text = resultadoFinal.text.toString() + "."
        }
    }

    fun cambiarOperador(b : View){
        val boton : Button = b as Button

        if(boton.text.toString().trim() == "÷"){
            operacionActual = division
        }
        if (boton.text.toString().trim() == "x"){
            operacionActual = multipicacion
        }
        if(boton.text.toString().trim() == "+"){
            operacionActual = suma
        }
        if(boton.text.toString().trim() == "-"){  // corregido
            operacionActual = resta
        }

        primerNumero = resultadoFinal.text.toString().toDouble()
        resultadoTemporal.text = resultadoFinal.text.toString() + " " + boton.text.toString()
        resultadoFinal.text = "0"
    }

    fun calcular(b : View){
        if (primerNumero.isNaN() || operacionActual.isEmpty()) return

        segundoNumero = resultadoFinal.text.toString().toDouble()

        var resultado = 0.0

        if (operacionActual == suma) resultado = primerNumero + segundoNumero
        if (operacionActual == resta) resultado = primerNumero - segundoNumero
        if (operacionActual == multipicacion) resultado = primerNumero * segundoNumero
        if (operacionActual == division) {
            if (segundoNumero == 0.0) {
                resultadoFinal.text = "Error"
                return
            }
            resultado = primerNumero / segundoNumero
        }

        resultadoFinal.text = formatoDecimal.format(resultado)
        resultadoTemporal.text = ""
        primerNumero = Double.NaN
        operacionActual = ""
    }

    fun limpiar(b : View){
        resultadoFinal.text = "0"
        resultadoTemporal.text = "0"
        primerNumero = Double.NaN
        segundoNumero = Double.NaN
        operacionActual = ""
    }

    fun borrar(b : View){
        val actual = resultadoFinal.text.toString()
        if (actual.length > 1) {
            resultadoFinal.text = actual.dropLast(1)
        } else {
            resultadoFinal.text = "0"
        }
    }

    fun porcentaje(b : View){
        val num = resultadoFinal.text.toString().toDoubleOrNull() ?: return
        resultadoFinal.text = formatoDecimal.format(num / 100)
    }
}