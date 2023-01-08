package tiny.compiler.parser

enum class TokenType {
    PARENTHESES,
    NAME,
    NUMBER,
}

data class Token(
    val type: TokenType,
    val value: String,
)

fun Token.isOpenParentheses() = type == TokenType.PARENTHESES && value == "("

fun Token.isCloseParentheses() = type == TokenType.PARENTHESES && value == ")"

fun buildOpenParenthesesToken() = Token(TokenType.PARENTHESES, "(")

fun buildCloseParenthesesToken() = Token(TokenType.PARENTHESES, ")")

fun buildNumberToken(n: String) = Token(TokenType.NUMBER, n)

fun buildNameToken(name: String) = Token(TokenType.NAME, name)

fun Char.isOpenParentheses() = this == '('

fun Char.isCloseParentheses() = this == ')'

fun tokenize(input: String): List<Token> {
    var current = 0

    val tokens = mutableListOf<Token>()

    while (current < input.length) {
        if (input[current].isWhitespace()) {
            current++
            continue
        }

        if (input[current].isOpenParentheses()) {
            tokens.add(buildOpenParenthesesToken())
            current++
            continue
        }

        if (input[current].isCloseParentheses()) {
            tokens.add(buildCloseParenthesesToken())
            current++
            continue
        }

        if (input[current].isDigit()) {
            tokens.add(buildNumberToken(buildString {
                while (current < input.length && input[current].isDigit()) {
                    append(input[current++])
                }
            }))
            continue
        }

        if (input[current].isLetter()) {
            tokens.add(buildNameToken(buildString {
                while (current < input.length && input[current].isLetter()) {
                    append(input[current++])
                }
            }))
        }
    }

    return tokens
}