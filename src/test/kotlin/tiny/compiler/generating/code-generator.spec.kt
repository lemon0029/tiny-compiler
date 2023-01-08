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

    @Test
    fun codeGeneratorTest2() {
        // (add 1 (subtract 2 3))\n(add 4 (add 5 (subtract 6 7)))
        val programNode = ProgramNode().apply {
            body.add(ExpressionStatementNode(CallExpressionNode(callee = IdentifierNode("add"))).apply {
                expression.arguments.add(NumericLiteralNode("1"))
                expression.arguments.add(CallExpressionNode(callee = IdentifierNode("subtract")).apply {
                    arguments.add(NumericLiteralNode("2"))
                    arguments.add(NumericLiteralNode("3"))
                })
            })
            body.add(ExpressionStatementNode(CallExpressionNode(callee = IdentifierNode("add"))).apply {
                expression.arguments.add(NumericLiteralNode("4"))
                expression.arguments.add(CallExpressionNode(callee = IdentifierNode("add")).apply {
                    arguments.add(NumericLiteralNode("5"))
                    arguments.add(CallExpressionNode(callee = IdentifierNode("subtract")).apply {
                        arguments.add(NumericLiteralNode("6"))
                        arguments.add(NumericLiteralNode("7"))
                    })
                })
            })
        }

        val output = codeGenerator(programNode)

        assertEquals("add(1, subtract(2, 3));\nadd(4, add(5, subtract(6, 7)));", output)
    }
}