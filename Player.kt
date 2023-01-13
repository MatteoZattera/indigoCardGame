/** Class representing a player of the Indigo game. */
class Player(val name: String) {

    /** Current cards in hand. */
    val handCards = mutableListOf<Card>()

    /** Cards won during the match. */
    val collectedCards = mutableListOf<Card>()

    /** Current score updated each time the player win cards. */
    var currentScore = 0

    /**
     * Algorithm used by the player to select the number representing the position of the card in the hand to be played.
     * By default, a random position is selected automatically.
     */
    var cardPositionChoiceAlgorithm: () -> Int? = { (1..this.handCards.size).random() }

    /** Returns a card using [cardPositionChoiceAlgorithm] and removes it from hand. */
    fun chooseAndGetHandCard(): Card {
        val position = cardPositionChoiceAlgorithm.runUntil { it in 1..handCards.size }
        return handCards.removeAt(position!! - 1)
    }

    fun hasCardsInHand() = handCards.isNotEmpty()

    fun addCardToHand(card: Card) = handCards.add(card)

    /** Adds the [cards] to the collected cards and updates the [currentScore]. */
    fun addCardsToCollected(cards: Collection<Card>) = collectedCards.addAll(cards).also { currentScore += cards.sumOf { it.points } }
}
