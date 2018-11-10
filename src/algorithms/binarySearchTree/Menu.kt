package algorithms.binarySearchTree

//30, 10, 45, 38, 20, 50, 25, 33, 8, 12

fun main(args : Array<String>) {
    var tree: BinarySearchTree = BinarySearchTree()
    val list = listOf<Int>(30, 10, 45, 38, 20, 50, 25, 33, 8, 12)

    for (num in list)
        tree.insert(Node(num))

    tree.inOrder(tree.rootNode!!)
    println(tree.minimum(tree.rootNode!!).value)

    //tree.postOrder(tree.rootNode!!)
    //tree.preOrder((tree.rootNode!!))
    println()
    //tree.delete(tree.rootNode!!)
   // tree.inOrder(tree.rootNode!!)

    tree.printTree()
}

