package tiny.compiler.parser

import tiny.compiler.tokenizer.Token

enum class NodeType {
    PROGRAM,
    CALL_EXPRESSION,
    NUMERIC_LITERAL
}

open class Node(val type: NodeType)

class ProgramNode(val body: MutableList<Node> = mutableListOf()) : Node(NodeType.PROGRAM)

class CallExpressionNode(name: String, params: MutableList<NumericLiteralNode>) : Node(NodeType.CALL_EXPRESSION)

class NumericLiteralNode(value: String) : Node(NodeType.NUMERIC_LITERAL)


fun parser(tokens: List<Token>): ProgramNode {
    return ProgramNode()
}