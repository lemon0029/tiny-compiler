package tiny.compiler.traverser

import tiny.compiler.parser.*
import kotlin.reflect.KClass

fun traverse(programNode: ProgramNode, visitors: Map<KClass<out Node>, Visitor>) {

    fun traverseNode(node: Node, parent: Node?) {
        val visitor = visitors[node::class]

        visitor?.enter(node, parent)

        when (node) {
            is ProgramNode -> node.body.forEach { traverseNode(it, node) }
            is CallExpressionNode -> node.params.forEach { traverseNode(it, node) }
            is NumericLiteralNode -> {}
        }

        visitor?.exit(node, parent)
    }

    traverseNode(programNode, null)
}