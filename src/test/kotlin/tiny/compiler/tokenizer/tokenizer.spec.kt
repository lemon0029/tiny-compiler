package tiny.compiler.tokenizer

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals


class TokenizerSpec {

    @Test
    fun singleParenOpen() {
        val input = "("

        val expected = listOf(Token(TokenType.PAREN, "("))
        val tokens = tokenizer(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun singleParenClose() {
        val input = ")"

        val expected = listOf(Token(TokenType.PAREN, ")"))
        val tokens = tokenizer(input)

        tokens.forEach(::println)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun singleNumber() {
        val input = "1"

        val expected = listOf(Token(TokenType.NUMBER, "1"))
        val tokens = tokenizer(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun singleName() {
        val input = "add"

        val expected = listOf(Token(TokenType.NAME, "add"))
        val tokens = tokenizer(input)

        assertContentEquals(expected, tokens)
    }

    @Test
    fun simpleParenAndNameAndNumber() {
        val input = "(add 2 3)"

        val expected = listOf(
            Token(TokenType.PAREN, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.NUMBER, "3"),
            Token(TokenType.PAREN, ")"),
        )
        val tokens = tokenizer(input)

        assertContentEquals(expected, tokens)
    }
}