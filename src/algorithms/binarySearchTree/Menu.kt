package algorithms.binarySearchTree

//30, 10, 45, 38, 20, 50, 25, 33, 8, 12

fun menu() = """

    1. Insert seq (30, 10, 45, 38, 20, 50, 25, 33, 8, 12)
    2. Transverse in-order
    3. Transverse pre-order
    4. Transverse post-order
    5. Search for __
    6. Delete __
    7. Print Visual Tree (beta)
    8. Insert __
    Q. quit

    * Must insert before transversing
""".trimIndent()

fun getNumber(): Int{
    println("Enter number for Function:")
    return readLine()?.toInt()!!
}

fun main(args : Array<String>) {
    val tree = BinarySearchTree()
    val list = listOf(30, 10, 45, 38, 20, 50, 25, 33, 8, 12)
    var input: Char

    do {
        println(menu())
        input = readLine()?.toCharArray()!!.first()
        println()
        when (input) {
            '1' -> for (num in list) tree.insert(Node(num))
            '2' -> tree.rootNode?.let { tree.inOrder(it) }
            '3' -> tree.rootNode?.let { tree.preOrder(it) }
            '4' -> tree.rootNode?.let { tree.postOrder(it) }
            '5' -> tree.searchPath(getNumber())
            '6' -> tree.delete(getNumber())
            '7' -> tree.printTree()
            '8' -> tree.insert(Node(getNumber()))
            'q','Q' -> return

        }
    }while (input.toLowerCase() != 'q')

}

