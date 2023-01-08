package tiny.compiler.traverser

import tiny.compiler.parser.*
import kotlin.reflect.KClass

fun traverse(programNode: ProgramNode, visitors: Map<KClass<out Node>, Visitor>) {

    fun traverseNode(node: Node) {
        val visitor = visitors[node::class]

        visitor?.enter(node)

        when (node) {
            is ProgramNode -> node.body.forEach(::traverseNode)
            is CallExpressionNode -> node.params.forEach(::traverseNode)
            is NumericLiteralNode -> {}
        }

        visitor?.exit(node)
    }

    traverseNode(programNode)
}