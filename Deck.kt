/** Class representing the deck of cards of the Indigo game. */
class Deck(private val cards: MutableList<Card>) {

    init { cards.shuffle() }

    fun isNotEmpty() = cards.isNotEmpty()

    /** Deals a card removing it from the top of the deck. */
    fun drawCard() = cards.removeLast()
}
