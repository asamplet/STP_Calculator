package com.example.stp_calculator.presentation

class PNumber(number: String, base: Int, precision: Int) {

	companion object Alphabet {

		private const val isAlphabetExist = ".*[A-Z].*"
	}

	// Затолкать в инты отдельно дробную и отдельно целую часть, работать с ними
	// Для работы с дробной частью использовать precision
//	private var integerNumber: String = ""
//	private var fractionNumber: String = ""
	private var number: String = ""
	private var numberDecimal: Double = 0.0
	private var base: Int = 10
	private var precision: Int = 1

	//можно попробовать стринг в конструктор кидать
	init {
		this.number = number
		if(!this.number.contains(".")) this.number += ".0"
		if(this.number[number.lastIndex]=='.')	this.number += "0"

		val fractionNumberLength = number.substringAfter(".", "0").length

		this.base = base
		this.precision = precision
		try {
			if (this.base < 2 || this.base > 16) {
				throw ExceptionInInitializerError("Основание не может быть меньше 2 или больше 16!")
			}
		} catch (e: ExceptionInInitializerError) {
			this.base = 10
		}

		this.numberDecimal = toDecimal(this.number)

		try {
			if (baseCheck()) {
				throw ExceptionInInitializerError("Неверное основание числа! Ваше число будет переведено к десятичному основанию.")
			}
		} catch (e: ExceptionInInitializerError) {
			this.base = 10
			this.number = this.numberDecimal.toString()
		}
		try {
			if (this.precision <= 0) {
				throw ExceptionInInitializerError("Точность не может быть меньше 1!")
			}
		} catch (e: ExceptionInInitializerError) {
			this.precision = 1
		}
		try {
			if (this.precision < fractionNumberLength) {
				throw ExceptionInInitializerError("Точность некорректна!")
			}
		} catch (e: ExceptionInInitializerError) {
			this.precision = fractionNumberLength
		}
	}

	private fun baseCheck(): Boolean {
		return this.number.contains(toBaseChar(this.base))
	}

	private fun toBaseChar(base: Int): Char {
		return when (base) {
			2    -> '2'
			3    -> '3'
			4    -> '4'
			5    -> '5'
			6    -> '6'
			7    -> '7'
			8    -> '8'
			9    -> '9'
			10   -> 'a'
			11   -> 'b'
			12   -> 'c'
			13   -> 'd'
			14   -> 'e'
			15   -> 'f'
			16   -> 'g'
			else -> 'f'
		}
	}

	//Добавить обработку минуса
	private fun toDecimal(n: String): Double {
		val integerNumber: String = n.substringBefore(".", "0")
		val fractionNumber: String = n.substringAfter(".", "0")
		var resultInt = 0.0
		var resultFract = 0.0

		var counter = 0
		for (i in integerNumber.length - 1 downTo 0) {
			val currentNumber: String = integerNumber[counter].toString()
			resultInt += getCurrentNumberDouble(currentNumber) * intPow(this.base, i).toDouble()
			counter++
		}

		for (i in 1..fractionNumber.length) {
			val currentNumber: String = fractionNumber[i - 1].toString()
			resultFract += getCurrentNumberDouble(currentNumber) / intPow(this.base, i).toDouble()
		}
		return if (n.contains('-')) {
			(resultInt + resultFract).unaryMinus()
		} else {
			resultInt + resultFract
		}
	}

	private fun getCurrentNumberDouble(number: String): Double =
		when (number) {
			"0"  -> 0.0
			"1"  -> 1.0
			"2"  -> 2.0
			"3"  -> 3.0
			"4"  -> 4.0
			"5"  -> 5.0
			"6"  -> 6.0
			"7"  -> 7.0
			"8"  -> 8.0
			"9"  -> 9.0
			"a"  -> 10.0
			"b"  -> 11.0
			"c"  -> 12.0
			"d"  -> 13.0
			"e"  -> 14.0
			"f"  -> 15.0
			else -> 0.0
		}

	private fun toPBase(n: Double) {
		var result = ""
		val nAbs: Double
		if (n < 0) {
			result += "-"
			nAbs = n.unaryMinus()
		} else {
			nAbs = n
		}
		var intNumber = nAbs.toString().substringBefore(".", "0").toDouble()
		var fractNumber = ("0." + nAbs.toString().substringAfter(".", "0")).toDouble()
		val numberList = mutableListOf<String>()
		if(intNumber == 0.0) numberList.add("0")
		while (intNumber > 0) {
			when (intNumber % this.base) {
				0.0  -> numberList.add("0")
				1.0  -> numberList.add("1")
				2.0  -> numberList.add("2")
				3.0  -> numberList.add("3")
				4.0  -> numberList.add("4")
				5.0  -> numberList.add("5")
				6.0  -> numberList.add("6")
				7.0  -> numberList.add("7")
				8.0  -> numberList.add("8")
				9.0  -> numberList.add("9")
				10.0 -> numberList.add("a")
				11.0 -> numberList.add("b")
				12.0 -> numberList.add("c")
				13.0 -> numberList.add("d")
				14.0 -> numberList.add("e")
				15.0 -> numberList.add("f")
				else -> break
			}
			intNumber /= this.base
			intNumber = floor(intNumber)
		}

		for (i in numberList.size downTo 1)
			result += numberList[i - 1]

		result += "."

		repeat(this.precision) {
			fractNumber *= this.base
			var integerPartOfFractNumber = fractNumber.toString().substringBefore(".", "0")
			var fractPartOfFractNumber = "0." + fractNumber.toString().substringAfter(".", "0")
			when (integerPartOfFractNumber) {
				"0"  -> result += integerPartOfFractNumber
				"1"  -> result += integerPartOfFractNumber
				"2"  -> result += integerPartOfFractNumber
				"3"  -> result += integerPartOfFractNumber
				"4"  -> result += integerPartOfFractNumber
				"5"  -> result += integerPartOfFractNumber
				"6"  -> result += integerPartOfFractNumber
				"7"  -> result += integerPartOfFractNumber
				"8"  -> result += integerPartOfFractNumber
				"9"  -> result += integerPartOfFractNumber
				"10" -> result += "a"
				"11" -> result += "b"
				"12" -> result += "c"
				"13" -> result += "d"
				"14" -> result += "e"
				"15" -> result += "f"
			}
			fractNumber = fractPartOfFractNumber.toDouble()
		}
		this.number = result
	}

	private fun floor(n: Double): Double {
		return n - n % 1
	}

	private fun intPow(number: Int, n: Int): Int {
		var result = 1
		repeat(n) {
			result *= number
		}
		return result
	}

	fun plus(d: PNumber) {
		if (d.base != this.base || d.precision != this.precision) {
			return
		}
		this.numberDecimal += d.numberDecimal
		toPBase(this.numberDecimal)
	}

	fun minus(d: PNumber) {
		if (d.base != this.base || d.precision != this.precision) {
			return
		}
		this.numberDecimal -= d.numberDecimal
		toPBase(this.numberDecimal)
	}

	fun mult(d: PNumber) {
		if (d.base != this.base || d.precision != this.precision) {
			return
		}
		this.numberDecimal *= d.numberDecimal
		toPBase(this.numberDecimal)
	}

	fun div(d: PNumber) {
		if (d.base != this.base || d.precision != this.precision || d.numberDecimal == 0.0) {
			return
		}
		this.numberDecimal /= d.numberDecimal
		toPBase(this.numberDecimal)
	}

	fun revert() {
		if (this.numberDecimal == 0.0)
			return
		val a = this.numberDecimal
		this.numberDecimal = 1 / a
		toPBase(this.numberDecimal)
	}

	fun sqr() {
		this.numberDecimal *= this.numberDecimal
		toPBase(this.numberDecimal)
	}

	fun getNumberDecimal(): Double {
		return this.numberDecimal
	}

	override fun toString(): String {
		return this.number + ", base: " + this.base.toString() + ", prec: " + this.precision.toString()
	}

	fun getBaseInt(): Int {
		return this.base
	}

	fun getBaseString(): String {
		return this.base.toString()
	}

	fun getPrecisionInt(): Int {
		return this.precision
	}

	fun getNumber(): String{
		return this.number
	}

	fun getPrecisionString(): String {
		return this.precision.toString()
	}

	fun setBase(base: Int) {
		this.base = base
	}

	fun setPrecision(precision: Int) {
		this.precision = precision
	}

	fun equal(d: PNumber): Boolean {
		return d.number == this.number
	}
}