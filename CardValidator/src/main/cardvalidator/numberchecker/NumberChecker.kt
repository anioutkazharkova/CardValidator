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

    /**
     * @param cardNumber number of card
     * @return Returns true if card number has appropriate length and correct digits
     * */
    fun isValid(cardNumber: String): Boolean {
        return checkLead(cardNumber) && checkLength(cardNumber) && luhnAlgorithm(cardNumber)
    }

    /**
     * Check the card number has length between 12 and 19
     * */
    private fun checkLength(cardNumber: String): Boolean {
        return cardNumber.length >= MIN_LENGTH && cardNumber.length <= MAX_LENGTH
    }

    /**
     * Check if the lead symbol is not zero (0)
     * */
    private fun checkLead(cardNumber: String): Boolean {
        val digits = cardNumber.map { Character.getNumericValue(it) }.toMutableList()
        return digits[0] != 0
    }


    /**
     * Luhn algorithm to check card number
     * @param cardNumber - whole card number without spaces
     * */
    private fun luhnAlgorithm(cardNumber: String): Boolean {
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