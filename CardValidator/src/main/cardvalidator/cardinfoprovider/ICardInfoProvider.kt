package cardvalidator.cardinfoprovider

import data.Card
import data.response.ContentResponse

interface ICardInfoProvider {
    fun getCardInfo(cardNumber:String):ContentResponse<Card>
}