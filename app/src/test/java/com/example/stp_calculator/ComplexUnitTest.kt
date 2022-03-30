package com.example.stp_calculator

import com.example.stp_calculator.presentation.Complex
import org.junit.Assert
import org.junit.Test
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class ComplexUnitTest {

	@Test
	fun initTest() {
		val testComplex1 = Complex("4 + 8i")
		val testComplex2 = Complex("4 + 8i")
		val expect = true
		Assert.assertEquals(expect, testComplex1.equal(testComplex2))
	}

	@Test
	fun plusTest() {
		val testComplex1 = Complex("1 + 2i")
		val testComplex2 = Complex("1 + 2i")
		val expect = true
		testComplex1.plus(testComplex2)
		val result = Complex("2 + 4i")
		Assert.assertEquals(expect, testComplex1.equal(result))
	}

	@Test
	fun minusTest() {
		val testComplex1 = Complex("2 + 4i")
		val testComplex2 = Complex("1 + 2i")
		val result = Complex("1 + 2i")
		testComplex1.minus(testComplex2)
		val expect = true
		Assert.assertEquals(expect, testComplex1.equal(result))
	}

	@Test
	fun multTest() {
		val testComplex1 = Complex("2 + 2i")
		val testComplex2 = Complex("2 + 2i")
		val result = Complex("0 + 8i")
		val expect = true
		testComplex1.mult(testComplex2)
		Assert.assertEquals(expect, testComplex1.equal(result))
	}

	@Test
	fun divTest() {
		val testComplex1 = Complex("2 + 2i")
		val testComplex2 = Complex("2 + 2i")
		val result = Complex("1 + 0i")
		val expect = true
		testComplex1.div(testComplex2)
		Assert.assertEquals(expect, testComplex1.equal(result))
	}

	@Test
	fun toStringTest1() {
		val testComplex1 = Complex("3 + 8i")
		val expect = "3.0 + 8.0i"
		Assert.assertEquals(expect, testComplex1.toString())
	}

	@Test
	fun toStringTest2() {
		val testComplex1 = Complex("3 + -8i")
		val expect = "3.0 - 8.0i"
		Assert.assertEquals(expect, testComplex1.toString())
	}

	@Test
	fun sqrTest() {
		val testComplex1 = Complex("4 + 3i")
		val result = Complex("7 + 24i")
		val expect = true
		testComplex1.sqr()
		Assert.assertEquals(expect, testComplex1.equal(result))
	}

	@Test
	fun reverseTest() {
		val testComplex1 = Complex("1 + 1i")
		val result = Complex("0.5 + -0.5i")
		val expect = true
		testComplex1.reverse()
		Assert.assertEquals(expect, testComplex1.equal(result))
	}

	@Test
	fun unaryMinusTest() {
		val testComplex1 = Complex("1 + 1i")
		val result = Complex("-1 + -1i")
		val expect = true
		testComplex1.unaryMinus()
		Assert.assertEquals(expect, testComplex1.equal(result))
	}

	@Test
	fun absTest() {
		val testComplex1 = Complex("3 + 4i")
		val result = testComplex1.abs()
		val expect = 5.0
		val delta = 0.01
		Assert.assertEquals(expect, result, delta)
	}

	@Test
	fun radianAngleTest() {
		val testComplex1 = Complex("0.5 + 0.2i")
		val result = testComplex1.radianAngle()
		val expect = 0.38
		val delta = 0.01
		Assert.assertEquals(expect, result, delta)
	}

	@Test
	fun degreeAngleTest() {
		val testComplex1 = Complex("1 + 1i")
		val result = testComplex1.degreeAngle()
		val expect = 45.0
		val delta = 0.1
		Assert.assertEquals(expect, result, delta)
	}

	@Test
	fun powTest() {
		val testComplex1 = Complex("2 + 5i")
		val result = Complex("-142 + -65i")
		testComplex1.pow(3)
		val expectReal = -142.0
		val expectImagine = -65.0
		val delta = 0.01
		Assert.assertEquals(expectReal, testComplex1.getRealDouble(), delta)
		Assert.assertEquals(expectImagine, testComplex1.getImagineDouble(), delta)
	}

	@Test
	fun rootTest() {
		val testComplex1 = Complex("2 + 2i")
		testComplex1.root(4, 3)
		val expectReal = 2.0.pow(3 / 8) * cos(7 * PI / 16)
		val expectImagine = -(2.0.pow(3 / 8) * sin(7 * PI / 16))
		val delta = 0.01
		Assert.assertEquals(expectReal, testComplex1.getRealDouble(), delta)
		Assert.assertEquals(expectImagine, testComplex1.getImagineDouble(), delta)
	}

	@Test
	fun equalTest() {
		val testComplex1 = Complex("2 + 2i")
		val testComplex2 = Complex("2 + 2i")
		val expect = true
		Assert.assertEquals(expect, testComplex2.equal(testComplex1))
	}

	@Test
	fun notEqualTest() {
		val testComplex1 = Complex("2 + 2i")
		val testComplex2 = Complex("2 + 4i")
		val expect = true
		Assert.assertEquals(expect, testComplex2.notEqual(testComplex1))
	}

	@Test
	fun getRealDouble() {
		val testComplex1 = Complex("2 + 2i")
		val expect = 2.0
		val delta = 0.01
		Assert.assertEquals(expect, testComplex1.getRealDouble(), delta)
	}

	@Test
	fun getImagineDouble() {
		val testComplex1 = Complex("2 + 4i")
		val expect = 4.0
		val delta = 0.01
		Assert.assertEquals(expect, testComplex1.getImagineDouble(), delta)
	}

	@Test
	fun getRealString() {
		val testComplex1 = Complex("2 + 2i")
		val expect = "2.0"
		Assert.assertEquals(expect, testComplex1.getRealString())
	}

	@Test
	fun getImagineString() {
		val testComplex1 = Complex("2 + 4i")
		val expect = "4.0"
		Assert.assertEquals(expect, testComplex1.getImagineString())
	}
}