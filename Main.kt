/** Prints `this` argument to the standard output stream. */
fun <T> T.printlnIt() = println(this)

/** Prints `this` argument to the standard output stream, reads an input and returns it. */
fun <T> T.reply() = this.printlnIt().run { readln() }

/** Runs `this` argument function until the [condition] is true and returns the result. */
fun <T> (() -> T).runUntil(condition: (T) -> Boolean): T {
    var result = this()
    while (!condition(result)) result = this()
    return result
}

const val PLAYER_1_NAME = "Player"
const val PLAYER_2_NAME = "Computer"

fun main() {

    with(IndigoGame) {
        NAME.printlnIt()
        setFirstPlayer()

        // Changes the algorithm used by the human player.
        human.cardPositionChoiceAlgorithm = {
            val input = "Choose a card to play (1-${human.handCards.size}):".reply()
            if (input == "exit") endGame(true)
            input.toIntOrNull()
        }

        // Changes the algorithm used by the computer player.
        computer.cardPositionChoiceAlgorithm = {

            fun execute(): Int {
                val tableTopCard = table.topCard
                val cards = computer.handCards
                val candidateCards = cards.mapIndexedNotNull { index, card ->
                    if (card.rank == tableTopCard?.rank || card.suit == tableTopCard?.suit) index + 1 to card else null
                }

                if (cards.size == 1) return 1
                if (candidateCards.size == 1) return candidateCards[0].first

                if (table.isEmpty() || candidateCards.isEmpty()) {
                    val positionsOfCardsWithSameSuit = cards.mapIndexedNotNull { index, card -> if (cards.count { it.suit == card.suit } > 1) index + 1 else null }
                    if (positionsOfCardsWithSameSuit.isNotEmpty()) return positionsOfCardsWithSameSuit.random()

                    val positionsOfCardsWithSameRank = cards.mapIndexedNotNull { index, card -> if (cards.count { it.rank == card.rank } > 1) index + 1 else null }
                    if (positionsOfCardsWithSameRank.isNotEmpty()) return positionsOfCardsWithSameRank.random()

                    return (1..cards.size).random()
                }

                val positionOfCandidateCardsWithSameSuit = candidateCards.filter { it.second.suit == tableTopCard!!.suit }.map { it.first }
                if (positionOfCandidateCardsWithSameSuit.isNotEmpty()) return positionOfCandidateCardsWithSameSuit.random()

                val positionOfCandidateCardsWithSameRank = candidateCards.filter { it.second.rank == tableTopCard!!.rank }.map { it.first }
                if (positionOfCandidateCardsWithSameRank.isNotEmpty()) return positionOfCandidateCardsWithSameRank.random()

                return candidateCards.map { it.first }.random()
            }

            execute()
        }

        // Places the first 4 cards from the top of the deck on the table and prints it.
        repeat(4) { table.addCard(deck.drawCard()) }
        "Initial cards on the table: ${table.showTopCards(4)}".printlnIt()

        while (deck.isNotEmpty()) {

            // Deals the cards to each player.
            repeat(6) {
                human.addCardToHand(deck.drawCard())
                computer.addCardToHand(deck.drawCard())
            }

            // Performs each player's turn until they have no more cards.
            while (human.hasCardsInHand()) {
                performTurn()
                performTurn()
            }
        }

        endGame()
    }
}
