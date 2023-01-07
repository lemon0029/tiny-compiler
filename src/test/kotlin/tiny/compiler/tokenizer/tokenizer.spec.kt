package tiny.compiler.tokenizer

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals


class TokenizerSpec {

    @Test
    fun singleParenOpen() {
        val expected = listOf(Token(TokenType.PAREN, "("))

        val input = "("

        val tokens = tokenizer(input)

        assertContentEquals(expected, tokens)
    }
}