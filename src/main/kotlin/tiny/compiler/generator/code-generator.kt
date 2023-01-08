package tiny.compiler.generator

import tiny.compiler.parser.*

fun codeGenerator(node: Node): String {
    return when (node) {
        is ProgramNode -> node.body.joinToString("\n") { codeGenerator(it) }
        is ExpressionStatementNode -> codeGenerator(node.expression) + ";"
        is NumericLiteralNode -> node.value
        is IdentifierNode -> node.name
        is CallExpressionNode -> {
            val functionName = codeGenerator(node.callee!!)
            val arguments = node.arguments.joinToString(", ") { codeGenerator(it) }
            "$functionName($arguments)"
        }
        else -> throw IllegalArgumentException("${node.type}")
    }
}