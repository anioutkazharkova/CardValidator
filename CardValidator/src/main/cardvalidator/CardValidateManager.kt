package cardvalidator

import cardvalidator.cardinfoprovider.CardInfoProvider
import cardvalidator.cardinfoprovider.ICardInfoProvider
import cardvalidator.numberchecker.NumberChecker
import data.Card
import data.CardInfo

class CardValidateManager {
    private var numberChecker: NumberChecker
    private var cardInfoProvider: ICardInfoProvider

    private constructor() {
        this.numberChecker = NumberChecker.instance()
        this.cardInfoProvider = CardInfoProvider()
    }

    companion object {
        private var validator = CardValidateManager()
        fun instance():CardValidateManager {
            return validator
        }
    }

    fun validateNumbers(numbers: List<String>):List<CardInfo> {
        var cardData = arrayListOf<CardInfo>()
        for (number in numbers) {
            cardData.add(getCardValidData(number))
        }
        return cardData
    }

    fun validateNumbers(numbers: List<String>, brand: String):List<CardInfo> {
        return validateNumbers(numbers).filter { it.card?.bank?.name == brand }
    }

    fun validateNumbers(numbers: List<String>, validity: Boolean, brand: String? = null ):List<CardInfo> {
        val cardData = validateNumbers(numbers).filter { it.isValid == validity  }
        if (brand.isNullOrEmpty()) {
            return cardData
        } else {
            return cardData.filter { it.card?.bank?.name == brand }
        }
    }

    fun getCardValidData(cardNumber: String):CardInfo {
        return getCardInfo(cardNumber)
    }

    private fun checkNumber(cardNumber: String):Boolean {
        return  numberChecker.isValid(cardNumber)
    }

    private fun getCardInfo(cardNumber: String):CardInfo {
        val valid = checkNumber(cardNumber)
        val response = cardInfoProvider.getCardInfo(cardNumber)
        if (response.error != null) {
            return CardInfo(valid)
        } else {
            return CardInfo(valid, response.content)
        }
    }

}