package data

import java.io.Serializable

class CardNumber() : Serializable {
    var length: Int = 0
    val luhn: Boolean = false
}