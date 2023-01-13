enum class Suit(private val symbol: String) {
    DIAMOND("♦"),
    HEART("♥"),
    SPADE("♠"),
    CLUB("♣");

    override fun toString() = this.symbol
}
