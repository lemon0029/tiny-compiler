package tiny.compiler

import tiny.compiler.generator.codeGenerate
import tiny.compiler.parser.parse
import tiny.compiler.parser.tokenize
import tiny.compiler.traverser.transform
import java.nio.file.Files
import kotlin.io.path.toPath

fun compile(input: String): String {
    val tokens = tokenize(input)
    val ast = parse(tokens)
    val transformed = transform(ast)
    return codeGenerate(transformed)
}

fun main(args: Array<String>) {
    val sourceCodeFileName = args[0]

    val path = Thread.currentThread().contextClassLoader.getResource(sourceCodeFileName)?.toURI()?.toPath() ?: return
    val input = Files.readString(path)
    println("[$sourceCodeFileName]\n$input\n")


    val output = compile(input)
    println("[${sourceCodeFileName.removeSuffix(".lisp") + ".c"}]\n$output\n")
}