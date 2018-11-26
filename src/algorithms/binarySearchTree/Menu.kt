package algorithms.binarySearchTree

//30, 10, 45, 38, 20, 50, 25, 33, 8, 12

fun main(args : Array<String>) {
    var tree = BinarySearchTree()
    val list = listOf(30, 10, 45, 38, 20, 50, 25, 33, 8, 12)

    for (num in list)
        tree.insert(Node(num))

    tree.printTree()
    tree.inOrder(tree.rootNode!!)
    tree.delete(10)
    tree.printTree()
    tree.inOrder(tree.rootNode!!)
}

