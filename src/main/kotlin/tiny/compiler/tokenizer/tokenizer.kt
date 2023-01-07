package tiny.compiler.tokenizer

enum class TokenType {
    PAREN,
    NAME,
    NUMBER,
}

data class Token(
    val type: TokenType,
    val value: String,
)

fun parenOpenToken() = Token(TokenType.PAREN, "(")

fun tokenizer(input: String): List<Token> {
    var current = 0

    val tokens = mutableListOf<Token>()

    if (input[current] == '(') {
        tokens.add(parenOpenToken())
        current++
    }

    return tokens
}