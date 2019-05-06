package cardvalidator.cardinfoprovider

import data.Card
import data.response.ContentResponse
import network.*

/**
 * Manager to request card data via network
 * */
class CardInfoProvider : ICardInfoProvider {
    private var networkClient: INetworkClient

    constructor() {
        this.networkClient = NetworkClient()
    }

    /**
     * Request card data from network
     * @param cardNumber - number of card
     * @return card data with error info
     * */
    override fun getCardInfo(cardNumber: String): ContentResponse<Card> {
        return networkClient.requestGet(Card::class.java, cardNumber)
    }
}