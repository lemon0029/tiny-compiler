package tiny.compiler.traverser

import tiny.compiler.parser.*

fun transform(programNode: ProgramNode): ProgramNode {
    val newAST = ProgramNode()

    programNode.context = newAST.body

    traverse(programNode, mapOf(
        CallExpressionNode::class to object : Visitor() {
            override fun enter(node: Node, parent: Node?) {

                val callExpressionNode = CallExpressionNode(
                    callee = IdentifierNode((node as CallExpressionNode).name!!),
                    arguments = mutableListOf(),
                )

                node.context = callExpressionNode.arguments

                if (parent is ProgramNode) {
                    val expressionStatementNode = ExpressionStatementNode(expression = callExpressionNode)
                    parent.context.add(expressionStatementNode)
                } else if (parent is CallExpressionNode) {
                    parent.context.add(callExpressionNode)
                }
            }
        },
        NumericLiteralNode::class to object : Visitor() {
            override fun enter(node: Node, parent: Node?) {
                if (parent is ProgramNode) {
                    parent.context.add(node)
                } else if (parent is CallExpressionNode) {
                    parent.context.add(node)
                }
            }
        }
    ))


    return newAST
}