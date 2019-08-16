package rationals

import java.math.BigInteger

class Rational(
        private val numerator: BigInteger,
        private val denominator: BigInteger
) : Comparable<Rational> {

    init {
        if (denominator == BigInteger.ZERO) {
            throw IllegalArgumentException("Denominator cannot be 0.")
        }
    }

    operator fun plus(other: Rational): Rational {
        val num = (numerator * other.denominator) + (denominator * other.numerator)
        val den = denominator * other.denominator
        return num.divBy(den)
    }

    operator fun minus(other: Rational): Rational {
        val num = (numerator * other.denominator) - (denominator * other.numerator)
        val den = denominator * other.denominator
        return num.divBy(den)
    }

    operator fun times(other: Rational): Rational {
        val num = numerator * other.numerator
        val den = denominator * other.denominator
        return num.divBy(den)
    }

    operator fun div(other: Rational): Rational {
        val num = numerator * other.denominator
        val den = denominator * other.numerator
        return num.divBy(den)
    }

    operator fun unaryMinus(): Rational = Rational(numerator.negate(), denominator)

    override fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(denominator * other.numerator)
    }

    fun simplify(): Rational {
        val greatestCommonDivisor = numerator.gcd(denominator)
        val num = numerator / greatestCommonDivisor
        val den = denominator / greatestCommonDivisor
        return Rational(num, den)
    }

    private fun format(): String = "$numerator/$denominator"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other is Rational) {
            val simplifiedThis = simplify()
            val simplifiedOther = other.simplify()
            val thisAsDouble =
                    simplifiedThis.numerator.toDouble() / simplifiedThis.denominator.toDouble()
            val otherAsDouble =
                    simplifiedOther.numerator.toDouble() / simplifiedOther.denominator.toDouble()
            return thisAsDouble == otherAsDouble
        }
        return false
    }

    override fun toString(): String {
        val shouldBeOneNumberOnly =
                denominator == BigInteger.ONE || numerator % denominator == BigInteger.ZERO
        return when {
            shouldBeOneNumberOnly -> (numerator / denominator).toString()
            else -> {
                val simplified = simplify()
                if (simplified.denominator < BigInteger.ZERO
                        || simplified.denominator < BigInteger.ZERO) {
                    Rational(simplified.numerator.negate(),
                            simplified.denominator.negate()).format()
                } else {
                    Rational(simplified.numerator, simplified.denominator).format()
                }
            }
        }
    }
}

infix fun Int.divBy(other: Int): Rational = Rational(toBigInteger(), other.toBigInteger())

infix fun Long.divBy(other: Long): Rational = Rational(toBigInteger(), other.toBigInteger())

infix fun BigInteger.divBy(other: BigInteger): Rational = Rational(this, other)

fun String.toRational(): Rational {
    val ratioNumbers = split("/")
    return when {
        ratioNumbers.size == 1 -> Rational(ratioNumbers[0].toBigInteger(), BigInteger.ONE)
        ratioNumbers.size == 2 -> Rational(
                ratioNumbers[0].toBigInteger(),
                ratioNumbers[1].toBigInteger()
        )
        else -> throw IllegalArgumentException("Invalid format.")
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}