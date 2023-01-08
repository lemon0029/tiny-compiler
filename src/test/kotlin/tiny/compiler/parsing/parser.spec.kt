package tiny.compiler.parsing

import org.junit.jupiter.api.Test
import tiny.compiler.parser.CallExpressionNode
import tiny.compiler.parser.NodeType
import tiny.compiler.parser.NumericLiteralNode
import tiny.compiler.parser.parse
import tiny.compiler.parser.Token
import tiny.compiler.parser.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParserSpec {

    @Test
    fun emptyAST() {
        val tokens = listOf<Token>()

        val programNode = parse(tokens)

        assertEquals(NodeType.PROGRAM, programNode.type)
        assertTrue { programNode.body.isEmpty() }
    }

    @Test
    fun singleNumberAST() {
        val tokens = mutableListOf(Token(TokenType.NUMBER, "23"))

        val programNode = parse(tokens)

        assertTrue { programNode.body.size == 1 }
        assertTrue {
            val node = programNode.body.first()
            node is NumericLiteralNode && node.value == "23"
        }
    }

    @Test
    fun singleCallExpressionAST() {
        val tokens = listOf(
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.NUMBER, "3"),
            Token(TokenType.PARENTHESES, ")"),
        )

        val programNode = parse(tokens)

        assertTrue { programNode.body.size == 1 && programNode.body.first() is CallExpressionNode }

        (programNode.body.first() as CallExpressionNode).apply {
            assertTrue { name == "add" }
            assertTrue { params.size == 2 }
            assertTrue { (params[0] as NumericLiteralNode).value == "2" }
            assertTrue { (params[1] as NumericLiteralNode).value == "3" }
        }
    }

    @Test
    fun fullAST() {
        val tokens = listOf(
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "5"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "subtract"),
            Token(TokenType.NUMBER, "41"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
        )

        val programNode = parse(tokens)

        assertTrue { programNode.body.size == 1 && programNode.body.first() is CallExpressionNode }
        (programNode.body.first() as CallExpressionNode).apply {
            assertTrue { name == "add" && params.size == 2 }

            assertTrue { (params[0] as NumericLiteralNode).value == "5" }

            (params[1] as CallExpressionNode).apply {
                assertTrue { name == "subtract" && params.size == 2 }
                assertTrue { (params[0] as NumericLiteralNode).value == "41" }
                assertTrue { (params[1] as NumericLiteralNode).value == "2" }
            }
        }
    }

    @Test
    fun fullAST1() {
        val tokens = listOf(
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "1"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "subtract"),
            Token(TokenType.NUMBER, "2"),
            Token(TokenType.NUMBER, "3"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "4"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "add"),
            Token(TokenType.NUMBER, "5"),
            Token(TokenType.PARENTHESES, "("),
            Token(TokenType.NAME, "subtract"),
            Token(TokenType.NUMBER, "6"),
            Token(TokenType.NUMBER, "7"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
            Token(TokenType.PARENTHESES, ")"),
        )

        val programNode = parse(tokens)

        assertTrue { programNode.body.size == 2 }
        assertTrue { programNode.body[0] is CallExpressionNode }
        assertTrue { programNode.body[1] is CallExpressionNode }

        (programNode.body[0] as CallExpressionNode).apply {
            assertTrue { name == "add" && params.size == 2 }

            assertTrue { (params[0] as NumericLiteralNode).value == "1" }

            (params[1] as CallExpressionNode).apply {
                assertTrue { name == "subtract" && params.size == 2 }
                assertTrue { (params[0] as NumericLiteralNode).value == "2" }
                assertTrue { (params[1] as NumericLiteralNode).value == "3" }
            }
        }

        (programNode.body[1] as CallExpressionNode).apply {
            assertTrue { name == "add" && params.size == 2 }

            assertTrue { (params[0] as NumericLiteralNode).value == "4" }

            (params[1] as CallExpressionNode).apply {
                assertTrue { name == "add" && params.size == 2 }
                assertTrue { (params[0] as NumericLiteralNode).value == "5" }
                assertTrue { params[1] is CallExpressionNode }

                (params[1] as CallExpressionNode).apply {
                    assertTrue { (params[0] as NumericLiteralNode).value == "6" }
                    assertTrue { (params[1] as NumericLiteralNode).value == "7" }
                }
            }
        }
    }
}