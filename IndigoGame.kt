import kotlin.system.exitProcess

/** Singleton class with the main methods of the Indigo game. */
object IndigoGame {

    const val NAME = "Indigo Card Game"

    val deck = Deck(Suit.values().map { suit -> Rank.values().map { rank -> Card(rank, suit) } }.flatten().toMutableList())
    val table = Table()
    val human = Player(PLAYER_1_NAME)
    val computer = Player(PLAYER_2_NAME)

    /** The player who started to play. Used for giving points at the end of the game. */
    private lateinit var firstPlayer: Player

    /** The player who won the last round. */
    private lateinit var lastRoundWinner: Player

    private var currentPlayer = human

    /** First stage of the game */
    fun setFirstPlayer() {
        val reply = { "Play first?".reply().uppercase() }.runUntil { it in listOf("YES", "NO") }

        currentPlayer = if (reply == "YES") human else computer
        lastRoundWinner = currentPlayer
        firstPlayer = currentPlayer
    }

    /** Middle stage of the game. */
    fun performTurn() {
        (if (table.isEmpty()) "No cards on the table" else "${table.size} cards on the table, and the top card is ${table.showTopCards(1)}").printlnIt()

        if (currentPlayer.name == PLAYER_1_NAME)
            currentPlayer.handCards.mapIndexed { index, card -> "${index + 1})$card" }.joinToString(" ", "Cards in hand: ").printlnIt()

        if (currentPlayer.name == PLAYER_2_NAME) currentPlayer.handCards.joinToString(" ").printlnIt()
        val playedCard = currentPlayer.chooseAndGetHandCard()
        if (currentPlayer.name == PLAYER_2_NAME) "${currentPlayer.name} plays $playedCard".printlnIt()

        table.addCard(playedCard)

        if (table.size > 1 && table.lastTwoCardsHasEqualSuitsOrRanks()) {
            currentPlayer.addCardsToCollected(table.collectAllCards())
            lastRoundWinner = currentPlayer
            "${currentPlayer.name} wins cards".printlnIt()
            showScore()
        }

        currentPlayer = if (currentPlayer.name == PLAYER_1_NAME) computer else human
    }

    /** Last stage of the game. */
    fun endGame(fromConsole: Boolean = false) {
        if (fromConsole) "Game Over".printlnIt().also { exitProcess(1) }

        (if (table.isEmpty()) "No cards on the table" else "${table.size} cards on the table, and the top card is ${table.showTopCards(1)}").printlnIt()
        lastRoundWinner.addCardsToCollected(table.collectAllCards())
        when {
            human.collectedCards.size > computer.collectedCards.size -> human.currentScore += 3
            computer.collectedCards.size > human.collectedCards.size -> computer.currentScore += 3
            else -> firstPlayer.currentScore += 3
        }
        showScore()
        "Game Over".printlnIt()
    }

    /** Prints to the standard output stream the current score and amount of cards of each player. */
    private fun showScore() {
        "Score: ${human.name} ${human.currentScore} - ${computer.name} ${computer.currentScore}".printlnIt()
        "Cards: ${human.name} ${human.collectedCards.size} - ${computer.name} ${computer.collectedCards.size}".printlnIt()
    }
}
