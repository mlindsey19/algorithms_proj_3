package algorithms.binarySearchTree

import java.lang.StringBuilder
import kotlin.math.pow

data class Node(var value: Int, var position: StringBuilder = StringBuilder(), var leftNode: Node? = null,
                var rightNode: Node? = null, var parentNode: Node? = null, var depth: Int = 0) {}

class BinarySearchTree {

    var rootNode: Node? = null
    private var height: Int = 0

    private var treeMap: HashMap<Int, Int> = hashMapOf()

    fun insert(newNode: Node) {
        var currentNode: Node? = null
        var node: Node? = this.rootNode


        while (node != null) {
            currentNode = node
            node = if ( newNode.value < node.value) node.leftNode.also {  newNode.position.append(0) }
            else node.rightNode.also {newNode.position.append(1) }
        }

        newNode.parentNode = currentNode

        if (currentNode == null) {
            this.rootNode = newNode
            return
        }

        if ( newNode.value < currentNode.value ) {
            currentNode.leftNode = newNode
        } else {
            currentNode.rightNode = newNode
        }
        if ( newNode.position.length > newNode.depth ) {
            newNode.depth = newNode.position.toString().length
            height = newNode.depth
        }

        treeMap.put(decimalPos(newNode).toInt(), newNode.value!!.toInt())
    }

    fun inOrder(node: Node){
        node.leftNode?.let { inOrder(node.leftNode!!) }
        println(node.value)
        node.rightNode?.let { inOrder(node.rightNode!!) }
    }
    fun preOrder(node: Node){
        println(node.value)
        node.leftNode?.let { preOrder(node.leftNode!!) }
        node.rightNode?.let { preOrder(node.rightNode!!) }
    }fun postOrder(node: Node){
        node.leftNode?.let { postOrder(node.leftNode!!) }
        node.rightNode?.let { postOrder(node.rightNode!!) }
        println(node.value)

    }

    fun minimum(node: Node): Node {
        node.leftNode?.let { return minimum( node.leftNode!! ) }
        return node
    }
    fun maximum (node: Node): Node {
        node.rightNode?.let { return maximum( node.rightNode!! ) }
        return node
    }

    private fun successor(node: Node): Node? {
        node.rightNode?.let { return minimum(node.rightNode!!) }
        val parentNode = node.parentNode
        return if (node == parentNode?.rightNode) successor(node.parentNode!!) else parentNode
    }
    fun delete (node: Node) {

        // set successor children to node children         //and set successor child to left-child of parent
        node.rightNode?.let {
            successor(node)!!.rightNode = node.rightNode.also {
                successor(node)!!.leftNode = node.leftNode.also {
                    successor(node)?.parentNode?.leftNode = successor(node)?.leftNode
                    return
                }
            }
        }
        //else right-child-node has null left-child link new node.right to node.parent.right
        node.parentNode!!.rightNode = node.rightNode
    }

    fun decimalPos (node: Node): Long {
        val depth = node.position.length
        val posInRow = toDecimal(node.position.toString())

        return ( (2.0).pow(depth) + posInRow - 1 ).toLong()

    }
    private  fun toDecimal(num: String): Int {
        val length = num.length
        var num = num.toLong() //adds setter to num
        var decimalNumber = 0
        var remainder: Long

        for (i in 0..length){
            remainder = num % 10
            num /= 10
            decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        }
        return decimalNumber
    }

    fun printTree() {
        var a: Int
        var b: Int
        var c: Int
        var temp: String
        println(this.rootNode!!.value.toString().padStart( ( ( 3 * height ) + 5 )  ) )

        for ( i in 1..height ) {
            print("  ".padEnd(3 * (height - i) + 4) )
            a = (2.0).pow(i).toInt() - 1
            b = (((3 * (2.0).pow(i)) - 2) / 2).toInt() - 1
            c = ((2.0).pow(i + 1) - 2).toInt()
            for (k in a..b) {
                temp = if (k !in treeMap.keys) "__ "
                else treeMap.getValue(k).toString().padEnd(3)

                print(temp)
            }
            for (j in (b + 1)..c) {
                temp = if (j !in treeMap.keys) "__ "
                else treeMap.getValue(j).toString().padEnd(3)

                print(temp)
            }

            println()
        }
    }

}



