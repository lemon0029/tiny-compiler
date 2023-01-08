package tiny.compiler.traversing

import org.junit.jupiter.api.Test
import tiny.compiler.parser.CallExpressionNode
import tiny.compiler.parser.NumericLiteralNode
import tiny.compiler.parser.ProgramNode
import tiny.compiler.traverser.*
import kotlin.test.assertContentEquals

class TraverserSpec {

    private val calling = mutableListOf<Any>()

    private val visitors = mapOf(
        ProgramNode::class to ProgramNodeVisitor(calling),
        NumericLiteralNode::class to NumericLiteralNodeVisitor(calling),
        CallExpressionNode::class to CallExpressionNodeVisitor(calling)
    )

    @Test
    fun singleProgramNodeTraverse() {
        val programNode = ProgramNode()

        traverse(programNode, visitors)

        val expected = mutableListOf(
            "program-node-enter",
            "program-node-exit"
        )

        assertContentEquals(expected, calling)
    }

    @Test
    fun fullNodeTraverse() {
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

        traverse(programNode, visitors)

        val expected = mutableListOf(
            "program-node-enter",
            "call-expression-node-enter-add",
            "numeric-literal-node-enter-1",
            "numeric-literal-node-exit-1",
            "call-expression-node-enter-subtract",
            "numeric-literal-node-enter-2",
            "numeric-literal-node-exit-2",
            "numeric-literal-node-enter-3",
            "numeric-literal-node-exit-3",
            "call-expression-node-exit-subtract",
            "call-expression-node-exit-add",
            "program-node-exit"
        )

        assertContentEquals(expected, calling)
    }
}