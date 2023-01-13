/** Class representing the cards placed on the Indigo game table. */
class Table {

    /** Cards on the table. The last card placed on table is at the end of the list. */
    private val cards = mutableListOf<Card>()

    /** Returns the amount of cards on the table. */
    val size get() = cards.size

    /** Returns the top card on the table or `null` if there's no cards. */
    val topCard get() = cards.lastOrNull()

    /** Returns a list of all cards on the table and removes from it. */
    fun collectAllCards() = cards.toList().also { cards.clear() }

    /** Returns a string representing the last [n] cards placed on the table. */
    fun showTopCards(n: Int) = cards.takeLast(n).joinToString(" ")

    fun isEmpty() = cards.isEmpty()

    fun addCard(card: Card)= cards.add(card)

    fun lastTwoCardsHasEqualSuitsOrRanks() = cards[size - 2].suit == cards.last().suit || cards[size - 2].rank == cards.last().rank
}
