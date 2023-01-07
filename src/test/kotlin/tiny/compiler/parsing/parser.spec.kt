package tiny.compiler.parsing

import org.junit.jupiter.api.Test
import tiny.compiler.parser.NodeType
import tiny.compiler.parser.NumericLiteralNode
import tiny.compiler.parser.parser
import tiny.compiler.tokenizer.Token
import tiny.compiler.tokenizer.tokenizer
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParserSpec {

    @Test
    fun emptyAST() {
        val tokens = listOf<Token>()

        val programNode = parser(tokens)

        assertEquals(NodeType.PROGRAM, programNode.type)
        assertTrue { programNode.body.isEmpty() }
    }

    @Test
    fun singleNumberAST() {
        val input = "23"
        val tokens = tokenizer(input)

        val programNode = parser(tokens)

        assertTrue { programNode.body.size == 1 }
        assertTrue {
            val node = programNode.body.first()
            node is NumericLiteralNode && node.value == "23"
        }
    }
}