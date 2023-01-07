package tiny.compiler.parsing

import org.junit.jupiter.api.Test
import tiny.compiler.parser.NodeType
import tiny.compiler.parser.parser
import tiny.compiler.tokenizer.Token
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
}