package cardvalidator.cardinfoprovider

import data.Card
import data.response.ContentResponse
import network.*

class CardInfoProvider : ICardInfoProvider{
    private  var networkClient: INetworkClient

    constructor() {
        this.networkClient = NetworkClient()
    }

    override fun getCardInfo(cardNumber: String): ContentResponse<Card> {
        return networkClient.requestGet(Card::class.java,cardNumber)
    }
}