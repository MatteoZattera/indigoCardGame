/** Class representing a card of the Indigo game. */
class Card(val rank: Rank, val suit: Suit) {

    /** Points that the card is worth. */
    val points = rank.value

    override fun toString() = "$rank$suit"
}
