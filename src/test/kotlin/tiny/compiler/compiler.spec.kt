package tiny.compiler

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CompilerSpec {

    @Test
    fun compilerTest() {
        assertEquals("add(1, 2);", compile("(add 1 2)"))
        assertEquals("subtract(3, 4);", compile("(subtract 3 4)"))
        assertEquals("subtract(3, add(4, 5));", compile("(subtract 3 (add 4 5))"))
        assertEquals("subtract(3, add(4, add(5, 6)));", compile("(subtract 3 (add 4 (add 5 6)))"))
        assertEquals("add(3, add(4, add(5, 6)));", compile("(add 3 (add 4 (add 5 6)))"))

        assertEquals("add(1, 2);\nsubtract(3, 4);", compile("(add 1 2)\n(subtract 3 4)"))
        assertEquals("add(1, 2);\nsubtract(3, add(4, 5));", compile("(add 1 2)\n(subtract 3 (add 4 5))"))

        val input = """
            (add 1123 2)
            (add 39 4)
            (add 51 (subtract 6 7))
            (subtract 61 799)
            (subtract 116 (add 12 (add 94382 (add 63 (add 40 (subtract 45 609))))))
        """.trimIndent()

        val output = """
            add(1123, 2);
            add(39, 4);
            add(51, subtract(6, 7));
            subtract(61, 799);
            subtract(116, add(12, add(94382, add(63, add(40, subtract(45, 609))))));
        """.trimIndent()

        assertEquals(output, compile(input))
    }
}