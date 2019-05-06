package data

class CardInfo() {
    var isValid:Boolean = true
    var card: Card? = null

    constructor(isValid: Boolean,card: Card? = null):this() {
        this.isValid = isValid
        this.card = card
    }
}