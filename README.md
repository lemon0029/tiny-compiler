# Tiny Frontend Compiler

参考 [the-super-tiny-compiler](https://github.com/jamiebuilds/the-super-tiny-compiler) 使用 Kotlin 编写的一个非常简单的前端编译器，这里我所理解的前端编译器是指将人类可阅读的源代码编译成中间字节码文件，比如说 `javac` 这个编译器将 .java 纯文本文件编译成 .class 二进制文件。

当然，我从未学过任何编译原理相关的知识，但是此前在 bilibili 这个视频网站看到过教学手写编译器的视频激发了我的兴趣，最开始看到的那个视频是应该是参考 [c4](https://github.com/rswier/c4) 这个项目实现的 C 语言编译器，难度系数比较高，似乎是要自己去实现一个 VM 以及相关指令集？另外是用 C 语言实现的，我对于需要操作指针的就不怎么感兴趣了，不是说怕指针之类的，只是因为我感觉写了好多好多的 Java/Kotlin 代码了，再去写指针不太习惯。

后面就是看到基于 [the-super-tiny-compiler](https://github.com/jamiebuilds/the-super-tiny-compiler) 用 TypeScript 实现的简单编译器了，仅仅只需要 200 行，这让我很感兴趣，应该是很简单的东西。因此花了大概半天的时间完成了这个项目，当然我的所有写法都基本上是参考自 the-super-tiny-compiler，不然的话一个对编译原理什么都不懂的人是不可能写得出来的，或许一天前你问我词法分析、语法分析到底是什么东西我是完全说不出来的。

好吧，再来说一说这个项目，和参考的项目一样都是将 Lisp-like 代码转为 C-like 代码，比如说将 `(add 1 2)` 转为 `add(1, 2);`。事实上这种简单的可以直接通过字符串处理来实现，完全不需要让一个 “编译器” 来完成这个过程，但是什么东西不是从零来说的呢？正是因为这个过程非常简单，对于想要了解编译器相关知识的人来说这就非常有帮助。



那么来说说我在做这个项目的时候学到了什么吧。

第一点是有关编译的基础知识，即包括：词法分析、语法分析、代码生成等；

第二点算是实践测试驱动开发。



那么从词法分析开始说，首先输入一串代码如 `(add 1 2)` 给词法分析器（tokenizer）将得到一个 token 列表，这里的 Token 的含义就是对代码中的各个元素的描述，比如说可以是一个括号、一个数字以及标识符等等

```kotlin
listOf(
  Token(PARENTHESES, "("),
  Token(NAME, "add"),
  Token(NUMBER, "1"),
  Token(NUMBER, "2"),
  Token(PARENTHESES, ")"),
)
```

然后是语法分析，词法分析器返回了一个 token 列表，但是那仅仅只是一个列表并没有太多的意义，因此语法分析器会根据这个 token 生成一个抽象语法树（Abstract Syntax Tree），得到 AST 之后对其遍历其实就是这段代码的语法含义。

```kotlin
// (add 1 2)
val programNode = ProgramNode().apply {
  body.add(CallExpressionNode("add").apply {
    params.add(NumericLiteralNode("1"))
    params.add(NumericLiteralNode("2"))
  })
}
```

然后是 AST 转换，我也不知道这个过程到底是为了什么，但是前面一个步骤生成的 AST 似乎不够直观？因为没有对语句的描述。因此需要 tranformer 来对 AST 做转换，这个过程其实就是对 AST 做遍历然后生成一棵新的 AST。大概就和下面的类似，和此前的 AST 不同的是根节点下面应该是一系列的 ExpressionStatementNode 即语句，而这个语句在这里也只是对 CallExpressionNode 做了一个包装而已。

![image-2023010832717312 PM](https://yec-dev.oss-cn-guangzhou.aliyuncs.com/image-2023010832717312%20PM.png)

有了真正的 AST 就可以去做代码生成了，我不知道真正的编译器这个步骤到底是在什么时候，以及说这个过程到底是将 AST 变成一个什么东西，但是按照当前项目的做法将其转为 C-like 的代码这是非常好理解的，也和 Code Generator 的含义非常像。

![image-2023010833202047 PM](https://yec-dev.oss-cn-guangzhou.aliyuncs.com/image-2023010833202047%20PM.png)

最后就是将这些组件包装成一个 Compiler 对象了，说实话这种一步步实现的感觉真的很好，现实中的 Coding 因为水平有限很难做到这种写起来非常舒服的代码，另外就是这个项目的各种概念是很清晰的，不需要自己去想类、方法命名（这简直太舒服了！）。

```kotlin
fun compile(input: String): String {
    val tokens = tokenize(input)
    val ast = parse(tokens)
    val transformed = transform(ast)
    return codeGenerate(transformed)
}
```



另外就是 TDD 了，在工作之前从来没有关心过测试，只知道个 junit 去做单元测试，另外自己项目写得也不多，用单元测试大部分时候都只是一个做功能测试的过程，耦合了很多真实数据，因此那时候打包项目都是需要跳过单元测试的。而 TDD 呢是工作之后我导师和我提到的，虽然他没有要求我一定要以 TDD 的方式去开发（很难的啦），但是我还是去学习了一下 Mockito 这个库的使用，当然 TDD 只是一个概念和用依赖没有任何关系，但我在之前是完全没法做到先写测试再实现功能的。而 Mockito 就可以帮助我去先假定某个接口方法的返回值之类的，这样我就可以脱离像 Repository 的实现直接测试 Service 的功能了，但是我想了想此前写了几个用 Mockito 的测试类还是先有具体的 Service 代码实现再去做测试的，那么这种测试只能满足那部分代码的功能。（当然大部分情况下这个功能不会变化就是了）

在这个项目中，我很明确我每个方法需要什么，比如说 compile 输入一个 Lisp-like 代码得到一个 C-like 代码，那么我可以先写单元测试要求输入 (add 1 2) 得到 add(1, 2); 然后再去实现 compile 方法，其它情况是类似的。当然这个项目中都是在测试方法输出是不是符合预期，但是放在工作开发中应该也是类似的，也不过是用 Mockito 来做期望某个对象的某个方法调用了多少次而已。

总得来说，这个项目的开发过程是按照 TDD 来的，而且对于我而言很喜欢这种开发模式，虽然也没有写很多的测试，但是我还是希望自己在今后的工作中仍然按照这种方式来开发，即方便自己也方便他人（当代码改动时）。



那就说到这里了，很开心自己又学到了一些新知识。或许等之后有空了会尝试去做一下 C4 那样真正的编译器吧，用 Kotlin 来写一个 C 语言的编译器真的能实现吗？:)