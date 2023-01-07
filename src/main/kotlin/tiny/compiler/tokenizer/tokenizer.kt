package tiny.compiler.tokenizer

enum class TokenType {
    PARENTHESES,
    NAME,
    NUMBER,
}

data class Token(
    val type: TokenType,
    val value: String,
)

fun parenOpenToken() = Token(TokenType.PARENTHESES, "(")

fun parenCloseToken() = Token(TokenType.PARENTHESES, ")")

fun numberToken(n: String) = Token(TokenType.NUMBER, n)

fun nameToken(name: String) = Token(TokenType.NAME, name)

fun tokenizer(input: String): List<Token> {
    var current = 0

    val tokens = mutableListOf<Token>()

    while (current < input.length) {
        if (input[current].isWhitespace()) {
            current++
        } else if (input[current] == '(') {
            tokens.add(parenOpenToken())
            current++
        } else if (input[current] == ')') {
            tokens.add(parenCloseToken())
            current++
        } else if (input[current].isDigit()) {
            tokens.add(numberToken(buildString {
                while (current < input.length && input[current].isDigit()) {
                    append(input[current])
                    current++
                }
            }))
        } else {
            tokens.add(nameToken(buildString {
                while (current < input.length && input[current].isLetter()) {
                    append(input[current])
                    current++
                }
            }))
        }
    }

    return tokens
}