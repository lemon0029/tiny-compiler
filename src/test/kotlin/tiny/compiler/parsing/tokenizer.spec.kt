package tiny.compiler.parsing

import org.junit.jupiter.api.Test
import tiny.compiler.parser.Token
import tiny.compiler.parser.TokenType
import tiny.compiler.parser.tokenize
import kotlin.test.assertContentEquals


class TokenizerSpec {

    @Test
    fun singleParenOpen() {
        val input = "("

        val expected = listOf(Token(TokenType.PARENTHESES, "("))
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun singleParenClose() {
        val input = ")"

        val expected = listOf(Token(TokenType.PARENTHESES, ")"))
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun singleNumber() {
        val input = "1"

        val expected = listOf(Token(TokenType.NUMBER, "1"))
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun singleNumber2() {
        val input = "190"

        val expected = listOf(Token(TokenType.NUMBER, "190"))
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun singleName() {
        val input = "add"

        val expected = listOf(Token(TokenType.NAME, "add"))
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun simpleParenAndNameAndNumber() {
        val input = "(add 2 3)"

        val expected = listOf(
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.NUMBER, "3"),
            Token(TokenType.PARENTHESES, ")"),
        )
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun fullParenAndNameAndNumber() {
        val input = "(add 2 (subtract 41 2))"

        val expected = listOf(
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "subtract"),
            Token(TokenType.NUMBER, "41"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
        )
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun fullParenAndNameAndNumber2() {
        val input = "(add 2 (subtract 41 2))\n(add 1 (add 2 (subtract 3 4)))"

        val expected = listOf(
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "subtract"),
            Token(TokenType.NUMBER, "41"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "1"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "subtract"),
            Token(TokenType.NUMBER, "3"),
            Token(TokenType.NUMBER, "4"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
        )
        val tokens = tokenize(input)

        assertContentEquals(expected, tokens)
    }
}