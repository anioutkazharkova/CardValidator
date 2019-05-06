package cardvalidator.numberchecker

class NumberChecker {

    companion object {
        private val MIN_LENGTH: Int = 12
        private val MAX_LENGTH: Int = 19

        private val checker = NumberChecker()

        fun instance(): NumberChecker {
            return checker
        }
    }

    fun isValid(cardNumber: String):Boolean {
        return checkLead(cardNumber) && checkLength(cardNumber) && luhnAlgorithm(cardNumber)
    }

    private  fun checkLength(cardNumber: String):Boolean {
        return cardNumber.length >= MIN_LENGTH && cardNumber.length <= MAX_LENGTH
    }

    private  fun checkLead(cardNumber: String):Boolean {
        val digits = cardNumber.map { Character.getNumericValue(it) }.toMutableList()
        return digits[0] != 0
    }

  private  fun luhnAlgorithm(cardNumber: String): Boolean {
        val digits = cardNumber.map { Character.getNumericValue(it) }.toMutableList()
        for (i in (digits.size - 2) downTo 0 step 2) {
            var value = digits[i] * 2
            if (value > 9) {
                value = value % 10 + 1
            }
            digits[i] = value
        }
        return digits.sum() % 10 == 0
    }
}