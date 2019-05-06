
import cardvalidator.CardValidateManager
import org.junit.Test
import kotlin.test.assertEquals

class ValidatorTest {


    var cardValidateManager: CardValidateManager = CardValidateManager.instance()

    @Test
    fun validateValid() {
        assertEquals(true,cardValidateManager.getCardValidData("4929804463622139").isValid)
        assertEquals(false,cardValidateManager.getCardValidData("4929804463622138").isValid)
        assertEquals(true,cardValidateManager.getCardValidData("6762765696545485").isValid)
    }

    @Test
    fun validateScheme() {
        assertEquals("visa",cardValidateManager.getCardValidData("4929804463622139").card?.scheme)
        assertEquals("visa",cardValidateManager.getCardValidData("4929804463622138").card?.scheme)
        assertEquals("mastercard",cardValidateManager.getCardValidData("6762765696545485").card?.scheme)
    }

}
