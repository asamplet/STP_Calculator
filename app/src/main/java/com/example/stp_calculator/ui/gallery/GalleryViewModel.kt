package com.example.stp_calculator.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stp_calculator.presentation.TFrac
import com.example.stp_calculator.ui.Functions
import com.example.stp_calculator.ui.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GalleryViewModel : ViewModel() {

	private val _history = MutableStateFlow("")
	val history: Flow<String> = _history

	private val _text = MutableLiveData("")
	val text: LiveData<String> = _text

	private var memory = "0"

	private var past = ""
	private var actual = ""
	private var softActual = false
	private var switchFun = false
	private var fixNumbers = true
	private var equalChange = false
	private var curOperation = Operation.NULL

	fun equalButton() {
		if (past.isNotEmpty() and actual.isNotEmpty()) {
			startCalculation()
			_history.value = ""
			equalChange = true
		}
	}

	private fun startCalculation() {
		val pNum = TFrac(past)
		val pNum2 = TFrac(actual)
		when (curOperation) {
			Operation.PLUS  -> pNum.plus(pNum2)
			Operation.MINUS -> pNum.minus(pNum2)
			Operation.DIV   -> pNum.div(pNum2)
			Operation.MUL   -> pNum.mult(pNum2)
			Operation.NULL  -> TODO()
		}
		past = pNum.toString()
		_text.value = past
	}

	fun addDot() {
		actual += "/"
		_text.value += "/"
	}

	fun addNumber(number: Int) {
		val text = number.toString()

		if (equalChange) {
			actual = ""
			past = ""
			_text.value = ""
			equalChange = false
		}

		if (softActual) {
			_text.value = ""
			actual = ""
			softActual = false
			fixNumbers = true
		}
		if (switchFun) {
			_text.value = ""
			actual = ""
			switchFun = false
			fixNumbers = true
		}
		_text.value += text
		actual += text
	}

	fun makeFunctions(functions: Functions) {
		if (equalChange) {
			actual = past
			past = ""
			equalChange = false
		}
		val pNum = TFrac(actual)

		when (functions) {
			Functions.SQR -> pNum.sqr()
			Functions.REV -> pNum.reverse()
		}

		actual = pNum.toString()
		_text.value = actual
		switchFun = true
	}

	fun makeOperation(operation: Operation) {
		val sign = when (operation) {
			Operation.PLUS  -> "+"
			Operation.MINUS -> "-"
			Operation.DIV   -> "/"
			Operation.MUL   -> "*"
			Operation.NULL  -> ""
		}

		if (equalChange) {
			actual = past
			past = ""
			equalChange = false
		}

		if (fixNumbers) {
			if (past.isNotEmpty() and actual.isNotEmpty()) startCalculation()

			if (past.isEmpty() and actual.isEmpty()) past = "0/1"
			if (past.isEmpty()) past = actual
			_history.value += " $actual  "
			softActual = true
			fixNumbers = false
		}
		curOperation = operation

		_history.value = _history.value.dropLast(1)
		_history.value += sign
	}

	fun clear() {
		_text.value = ""
		_history.value = ""
		past = ""
		actual = ""
		softActual = false
		switchFun = false
		fixNumbers = true
		equalChange = false
		curOperation = Operation.NULL
	}

	fun erase() {
		_text.value = _text.value?.dropLast(1)
		actual = actual.dropLast(1)
	}

	fun memoryAdd(): Boolean {
		if(actual.isNotEmpty()) {
			val pNum = TFrac(memory)
			val pNum2 = TFrac(actual)
			pNum.plus(pNum2)
			memory = pNum.toString()
			return true
		}
		return false
	}

	fun memoryRead() {
		if (softActual) {
			_text.value = ""
			actual = ""
			softActual = false
			fixNumbers = true
		}

		actual = memory
		_text.value = memory
	}

	fun memorySave(): Boolean {
		if(actual.isNotEmpty()){
			memory = actual
			return true
		}
		return false
	}

	fun memoryClear() {
		memory = "0/1"
	}
}