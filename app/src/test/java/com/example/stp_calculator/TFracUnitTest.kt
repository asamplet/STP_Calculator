package com.example.stp_calculator

import com.example.stp_calculator.presentation.TFrac
import org.junit.Assert
import org.junit.Test

class TFracUnitTest {

	@Test
	fun initTest() {
		val testFrac1 = TFrac("-4/-8")
		val testFrac2 = TFrac("1/2")
		val expect = true
		Assert.assertEquals(expect, testFrac1.equal(testFrac2))
	}

	@Test
	fun initExceptionTest() {
		val testFrac1 = TFrac("1/0")
		val testFrac2 = TFrac("1/1")
		val expect = true
		Assert.assertEquals(expect, testFrac1.equal(testFrac2))
	}

	@Test
	fun plusTest() {
		val testFrac1 = TFrac("1/2")
		val testFrac2 = TFrac("3/4")
		val expect = true
		testFrac1.plus(testFrac2)
		val result = TFrac("5/4")
		Assert.assertEquals(expect, testFrac1.equal(result))
	}

	@Test
	fun minusTest() {
		val testFrac1 = TFrac("1/2")
		val testFrac2 = TFrac("3/4")
		val result = TFrac("-1/4")
		testFrac1.minus(testFrac2)
		val expect = true
		Assert.assertEquals(expect, testFrac1.equal(result))
	}

	@Test
	fun multTest() {
		val testFrac1 = TFrac("1/2")
		val testFrac2 = TFrac("3/4")
		val result = TFrac("3/8")
		val expect = true
		testFrac1.mult(testFrac2)
		Assert.assertEquals(expect, testFrac1.equal(result))
	}

	@Test
	fun divTest() {
		val testFrac1 = TFrac("1/2")
		val testFrac2 = TFrac("3/4")
		val result = TFrac("2/3")
		val expect = true
		testFrac1.div(testFrac2)
		Assert.assertEquals(expect, testFrac1.equal(result))
	}

	@Test
	fun divExceptionTest() {
		val testFrac1 = TFrac("1/2")
		val testFrac2 = TFrac("0/4")
		val result = TFrac("1/2")
		val expect = true
		testFrac1.div(testFrac2)
		Assert.assertEquals(expect, testFrac1.equal(result))
	}

	@Test
	fun toStringTest() {
		val testFrac1 = TFrac("1/2")
		val expect = "1/2"
		Assert.assertEquals(expect, testFrac1.toString())
	}

	@Test
	fun sqrTest() {
		val testFrac1 = TFrac("2/3")
		val result = TFrac("4/9")
		val expect = true
		testFrac1.sqr()
		Assert.assertEquals(expect, testFrac1.equal(result))
	}

	@Test
	fun reverseTest() {
		val testFrac1 = TFrac("13/4")
		val result = TFrac("4/13")
		val expect = true
		testFrac1.reverse()
		Assert.assertEquals(expect, testFrac1.equal(result))
	}

	@Test
	fun biggerThanTest() {
		val testFrac1 = TFrac("1/2")
		val testFrac2 = TFrac("3/4")
		val expect = true
		Assert.assertEquals(expect, testFrac2.biggerThan(testFrac1))
	}

	@Test
	fun equalTest() {
		val testFrac1 = TFrac("3/4")
		val testFrac2 = TFrac("3/4")
		val expect = true
		Assert.assertEquals(expect, testFrac2.equal(testFrac1))
	}

	@Test
	fun smallerThanTest() {
		val testFrac1 = TFrac("1/2")
		val testFrac2 = TFrac("3/4")
		val expect = true
		Assert.assertEquals(expect, testFrac1.smallerThan(testFrac2))
	}

	@Test
	fun getNumeratorIntTest() {
		val testFrac1 = TFrac("3/4")
		val expect = 3
		Assert.assertEquals(expect, testFrac1.getNumeratorInt())
	}

	@Test
	fun getNumeratorStringTest() {
		val testFrac1 = TFrac("3/4")
		val expect = "3"
		Assert.assertEquals(expect, testFrac1.getNumeratorString())
	}

	@Test
	fun getDenominatorIntTest() {
		val testFrac1 = TFrac("3/4")
		val expect = 4
		Assert.assertEquals(expect, testFrac1.getDenominatorInt())
	}

	@Test
	fun getDenominatorStringTest() {
		val testFrac1 = TFrac("3/4")
		val expect = "4"
		Assert.assertEquals(expect, testFrac1.getDenominatorString())
	}
}