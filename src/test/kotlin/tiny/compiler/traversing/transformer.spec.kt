package tiny.compiler.traversing

import org.junit.jupiter.api.Test
import tiny.compiler.parser.*
import tiny.compiler.traverser.transform
import kotlin.test.assertTrue

class TransformerSpec {

    @Test
    fun transformTest() {
        // (add 1 (subtract 2 3))
        val programNode = ProgramNode().apply {
            body.add(CallExpressionNode("add").apply {
                params.add(NumericLiteralNode("1"))
                params.add(CallExpressionNode("subtract").apply {
                    params.add(NumericLiteralNode("2"))
                    params.add(NumericLiteralNode("3"))
                })
            })
        }

        val transformed = transform(programNode)

        assertTrue { transformed.body.size == 1 }
        assertTrue { transformed.body.first() is ExpressionStatementNode }
        (transformed.body.first() as ExpressionStatementNode).apply {
            assertTrue { expression.callee!!.name == "add" }
            assertTrue { expression.arguments.size == 2 }
            assertTrue { expression.arguments[0] is NumericLiteralNode }
            assertTrue { expression.arguments[1] is CallExpressionNode }
            assertTrue { (expression.arguments[0] as NumericLiteralNode).value == "1" }

            (expression.arguments[1] as CallExpressionNode).apply {
                assertTrue { callee!!.name == "subtract" }

                assertTrue { arguments.size == 2 }
                assertTrue { arguments[0] is NumericLiteralNode }
                assertTrue { (arguments[0] as NumericLiteralNode).value == "2" }

                assertTrue { arguments[1] is NumericLiteralNode }
                assertTrue { (arguments[1] as NumericLiteralNode).value == "3" }
            }
        }
    }
}