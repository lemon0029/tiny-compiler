package tiny.compiler.parser

import tiny.compiler.tokenizer.Token
import tiny.compiler.tokenizer.TokenType

enum class NodeType {
    PROGRAM,
    CALL_EXPRESSION,
    NUMERIC_LITERAL
}

open class Node(val type: NodeType)

class ProgramNode(val body: MutableList<Node> = mutableListOf()) : Node(NodeType.PROGRAM)

class CallExpressionNode(name: String, params: MutableList<NumericLiteralNode>) : Node(NodeType.CALL_EXPRESSION)

class NumericLiteralNode(val value: String) : Node(NodeType.NUMERIC_LITERAL)


fun parser(tokens: List<Token>): ProgramNode {
    val programNode = ProgramNode()

    var current = 0

    if (tokens[current].type == TokenType.NUMBER) {
        programNode.body.add(
            NumericLiteralNode(tokens[current].value)
        )
    }

    return programNode
}