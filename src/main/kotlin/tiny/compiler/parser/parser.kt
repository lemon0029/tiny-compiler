package tiny.compiler.parser

enum class NodeType {
    PROGRAM,
    CALL_EXPRESSION,
    NUMERIC_LITERAL,
    EXPRESSION_STATEMENT,
    IDENTIFIER
}

open class Node(val type: NodeType)

class ExpressionStatementNode(val expression: CallExpressionNode) : Node(NodeType.EXPRESSION_STATEMENT)

class IdentifierNode(val name: String) : Node(NodeType.IDENTIFIER)

class ProgramNode(val body: MutableList<Node> = mutableListOf()) : Node(NodeType.PROGRAM) {
    lateinit var context: MutableList<Node>
}

class CallExpressionNode(
    val name: String? = null,
    val params: MutableList<Node> = mutableListOf(),
    val arguments: MutableList<Node> = mutableListOf(),
    val callee: IdentifierNode? = null
) : Node(NodeType.CALL_EXPRESSION) {
    lateinit var context: MutableList<Node>
}

class NumericLiteralNode(val value: String) : Node(NodeType.NUMERIC_LITERAL)


fun parse(tokens: List<Token>): ProgramNode {
    val programNode = ProgramNode()

    if (tokens.isEmpty()) return programNode

    var current = 0

    fun walk(): Node {
        var token = tokens[current]
        if (token.type == TokenType.NUMBER) {
            current++
            return NumericLiteralNode(token.value)
        }

        if (token.isOpenParentheses()) {
            val callExpressionNode = CallExpressionNode(tokens[++current].value)

            token = tokens[++current]

            while (true) {
                if (token.isCloseParentheses()) {
                    current++
                    break
                }

                callExpressionNode.params.add(walk())
                token = tokens[current]
            }

            return callExpressionNode
        }

        throw IllegalArgumentException("Token type: ${token.type}")
    }

    while (current < tokens.size) {
        programNode.body.add(walk())
    }

    return programNode
}