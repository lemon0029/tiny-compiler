package tiny.compiler

import tiny.compiler.generator.codeGenerator
import tiny.compiler.parser.parse
import tiny.compiler.parser.tokenize
import tiny.compiler.traverser.transform

fun compile(input: String): String {
    val tokens = tokenize(input)
    val ast = parse(tokens)
    val transformed = transform(ast)
    return codeGenerator(transformed)
}