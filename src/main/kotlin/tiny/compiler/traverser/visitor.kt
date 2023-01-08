package tiny.compiler.traverser

import tiny.compiler.parser.CallExpressionNode
import tiny.compiler.parser.Node
import tiny.compiler.parser.NumericLiteralNode

abstract class Visitor {

    abstract fun enter(node: Node, parent: Node?)

    open fun exit(node: Node, parent: Node?): Unit = TODO("Not yet implemented")

}

class ProgramNodeVisitor(private val calling: MutableList<Any>) : Visitor() {
    override fun enter(node: Node, parent: Node?) {
        calling.add("program-node-enter")
    }

    override fun exit(node: Node, parent: Node?) {
        calling.add("program-node-exit")
    }
}

class NumericLiteralNodeVisitor(private val calling: MutableList<Any>) : Visitor() {
    override fun enter(node: Node, parent: Node?) {
        calling.add("numeric-literal-node-enter-${(node as NumericLiteralNode).value}")
    }

    override fun exit(node: Node, parent: Node?) {
        calling.add("numeric-literal-node-exit-${(node as NumericLiteralNode).value}")
    }
}

class CallExpressionNodeVisitor(private val calling: MutableList<Any>) : Visitor() {
    override fun enter(node: Node, parent: Node?) {
        calling.add("call-expression-node-enter-${(node as CallExpressionNode).name}")

    }

    override fun exit(node: Node, parent: Node?) {
        calling.add("call-expression-node-exit-${(node as CallExpressionNode).name}")
    }
}