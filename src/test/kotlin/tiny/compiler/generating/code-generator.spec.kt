package tiny.compiler.generating

import tiny.compiler.generator.codeGenerator
import tiny.compiler.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CodeGeneratorSpec {

    @Test
    fun codeGeneratorTest1() {
        // (add 1 (subtract 2 3))
        val programNode = ProgramNode().apply {
            body.add(ExpressionStatementNode(CallExpressionNode(callee = IdentifierNode("add"))).apply {
                expression.arguments.add(NumericLiteralNode("1"))
                expression.arguments.add(CallExpressionNode(callee = IdentifierNode("subtract")).apply {
                    arguments.add(NumericLiteralNode("2"))
                    arguments.add(NumericLiteralNode("3"))
                })
            })
        }

        val output = codeGenerator(programNode)

        assertEquals("add(1, subtract(2, 3));", output)
    }
}