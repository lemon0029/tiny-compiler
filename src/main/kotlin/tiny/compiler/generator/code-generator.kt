package tiny.compiler.generator

import tiny.compiler.parser.*

fun codeGenerate(node: Node): String {
    return when (node) {
        is ProgramNode -> node.body.joinToString("\n") { codeGenerate(it) }
        is ExpressionStatementNode -> codeGenerate(node.expression) + ";"
        is NumericLiteralNode -> node.value
        is IdentifierNode -> node.name
        is CallExpressionNode -> {
            val functionName = codeGenerate(node.callee!!)
            val arguments = node.arguments.joinToString(", ") { codeGenerate(it) }
            "$functionName($arguments)"
        }
        else -> throw IllegalArgumentException("${node.type}")
    }
}