package data

import java.io.Serializable

 class Card() : Serializable {
     var number: CardNumber? = null
     var scheme: String? = null
     var type: String? = null
     var brand: String? = null
     var country: Country? = null
     var bank: Bank? = null
}
