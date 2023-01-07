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

class CallExpressionNode(
    val name: String,
    val params: MutableList<Node> = mutableListOf()
) : Node(NodeType.CALL_EXPRESSION)

class NumericLiteralNode(val value: String) : Node(NodeType.NUMERIC_LITERAL)


fun parser(tokens: List<Token>): ProgramNode {
    val programNode = ProgramNode()

    if (tokens.isEmpty()) return programNode

    var current = 0

    fun walk(): Node {
        var token = tokens[current]
        if (token.type == TokenType.NUMBER) {
            current++
            return NumericLiteralNode(token.value)
        }

        if (token.type == TokenType.PARENTHESES && token.value == "(") {
            token = tokens[++current]

            val callExpressionNode = CallExpressionNode(token.value)

            token = tokens[++current]

            while (true) {
                if (token.type == TokenType.PARENTHESES && token.value == ")") {
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

    programNode.body.add(walk())

    return programNode
}