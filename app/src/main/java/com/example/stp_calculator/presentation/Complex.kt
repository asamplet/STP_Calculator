package com.example.stp_calculator.presentation

import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class Complex(complex: String) {

	private var real: Double
	private var imagine: Double

	init {
		var complex2 = complex
		if(!complex.contains("+")) complex2 += " + 0i"
		if(complex[complex.lastIndex]==' ')	complex2 += " 0i"
		real = complex2.substringBefore("+", "0").toDouble()
		val imagineBuf: String = complex2.substringAfter("+", "1")
		imagine = imagineBuf.substringBefore("i", "0").toDouble()
	}

	override fun toString(): String {
		return if (this.imagine >= 0) {
			this.real.toString() + " + " + this.imagine.toString() + "i"
		} else
			this.real.toString() + " - " + this.imagine.unaryMinus().toString() + "i"
	}

	fun plus(d: Complex) {
		this.real += d.real
		this.imagine += d.imagine
	}

	fun minus(d: Complex) {
		this.real -= d.real
		this.imagine -= d.imagine
	}

	fun mult(d: Complex) {
		val thisRealTemp = this.real
		val thisImagineTemp = this.imagine

		this.real = thisRealTemp * d.real - thisImagineTemp * d.imagine
		this.imagine = thisRealTemp * d.imagine + thisImagineTemp * d.real
	}

	fun div(d: Complex) {
		val thisRealTemp = this.real
		val thisImagineTemp = this.imagine

		this.real = (thisRealTemp * d.real + thisImagineTemp * d.imagine) / (d.real * d.real + d.imagine * d.imagine)
		this.imagine = (d.real * thisImagineTemp - thisRealTemp * d.imagine) / (d.real * d.real + d.imagine * d.imagine)
	}

	fun sqr() {
		val thisRealTemp = this.real
		val thisImagineTemp = this.imagine

		this.real = thisRealTemp * thisRealTemp - thisImagineTemp * thisImagineTemp
		this.imagine = thisRealTemp * thisImagineTemp + thisRealTemp * thisImagineTemp
	}

	fun reverse() {
		val thisRealTemp = this.real
		val thisImagineTemp = this.imagine

		this.real = thisRealTemp / (thisRealTemp * thisRealTemp + thisImagineTemp * thisImagineTemp)
		this.imagine = -thisImagineTemp / (thisRealTemp * thisRealTemp + thisImagineTemp * thisImagineTemp)
	}

	fun unaryMinus() {
		this.real = this.real.unaryMinus()
		this.imagine = this.imagine.unaryMinus()
	}

	fun abs(): Double {
		return sqrt((this.real * this.real + this.imagine * this.imagine))
	}

	fun radianAngle(): Double {
		var fi: Double = 0.0
		if (this.real > 0) {
			fi = atan((this.imagine / this.real))
		} else if (this.real == 0.0 && this.imagine > 0) {
			fi = PI / 2
		} else if (this.real == 0.0 && this.imagine < 0) {
			fi = -PI / 2
		} else if (this.real < 0) {
			fi = atan((this.imagine / this.real)) + PI
		}
		return fi
	}

	fun degreeAngle(): Double {
		val convertRadToDegrees = 57.296
		var arg: Double = radianAngle() * convertRadToDegrees
//		if (this.real >= 0) {
//			arg = atan((this.imagine / this.real).toDouble())
//		} else if (this.real < 0.0 && this.imagine > 0) {
//			arg = PI + atan((this.imagine / this.real).toDouble())
//		} else if (this.real < 0.0 && this.imagine > 0) {
//			arg = -PI + atan((this.imagine / this.real).toDouble())
//		}
		return arg
	}

	fun pow(n: Int) {
		val radius = this.abs()
		val fi = this.radianAngle()
		this.real = radius.pow(n) * cos((n * fi))                    //Действительная
		this.imagine = radius.pow(n) * sin((n * fi))                 //Мнимая
	}

	fun root(n: Int, i: Int) {
		val radiusRoot = this.abs().pow(1 / n)
		val fi = this.radianAngle()
		var thisRealTemp = 0.0
		var thisImagineTemp = 0.0
		for (k in 0..i) {
			thisRealTemp = radiusRoot * cos(((fi + 2 * k * PI) / n))
			thisImagineTemp = sin((fi + 2 * k * PI) / n)
		}
		this.real = thisRealTemp
		this.imagine = thisImagineTemp
	}

	fun equal(d: Complex): Boolean {
		return this.real == d.real && this.imagine == d.imagine
	}

	fun notEqual(d: Complex): Boolean {
		return !equal(d)
	}

	fun getRealDouble(): Double {
		return this.real
	}

	fun getImagineDouble(): Double {
		return this.imagine
	}

	fun getRealString(): String {
		return this.real.toString()
	}

	fun getImagineString(): String {
		return this.imagine.toString()
	}
}