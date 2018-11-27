package algorithms.binarySearchTree

import java.lang.StringBuilder
import kotlin.math.log
import kotlin.math.pow

data class Node(var value: Int, var position: StringBuilder = StringBuilder(), var leftNode: Node? = null,
                var rightNode: Node? = null, var parentNode: Node? = null, var depth: Int = 0)
enum class Slash{ LEFT, Right }
class BinarySearchTree {
    var rootNode: Node? = null
    private var height: Int = 0
    private var treeMap: HashMap<Int, Int> = hashMapOf() // maps complete tree positions with node values

    fun insert(newNode: Node) {
        var currentNode: Node? = null
        var node: Node? = this.rootNode

        while (node != null) {
            currentNode = node
            node = if ( newNode.value < node.value) node.leftNode.also {  newNode.position.append(0) }
            else node.rightNode.also {newNode.position.append(1) } //append to string for printing position
        }

        newNode.parentNode = currentNode

        if (currentNode == null) {
            this.rootNode = newNode
            treeMap[0] = rootNode!!.value
            return
        }
        if ( newNode.value < currentNode.value ) {
            currentNode.leftNode = newNode
        } else {
            currentNode.rightNode = newNode
        }

        treeMap[decimalPos(newNode).toInt()] = newNode.value
        height = log(treeMap.keys.max()!!.toDouble() + 1, 2.0).toInt()
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
    }
    fun postOrder(node: Node){
        node.leftNode?.let { postOrder(node.leftNode!!) }
        node.rightNode?.let { postOrder(node.rightNode!!) }
        println(node.value)
    }

    private fun minimum(node: Node): Node {
        node.leftNode?.let { return minimum( node.leftNode!! ) }
        return node
    }

    private fun transplant(node: Node?, replacementNode: Node?){
        when {
            node!!.parentNode == null -> rootNode = replacementNode
            node == node.parentNode!!.leftNode -> node.parentNode!!.leftNode = replacementNode
            else -> node.parentNode!!.rightNode = replacementNode
        }
        if (replacementNode != null) replacementNode.parentNode = node.parentNode
    }

    fun delete (num: Int) {
        val node: Node? = search(num)
        node?.let {
            when{
                node.leftNode == null -> transplant(node,node.rightNode)
                node.rightNode == null -> transplant(node,node.leftNode)
                else ->{
                    val successor = minimum(node.rightNode!!)
                    if (successor.parentNode != node){
                        transplant( successor, successor.rightNode)
                        successor.rightNode = node.rightNode
                        successor.rightNode!!.parentNode = successor
                    }
                    transplant( node, successor)
                    successor.leftNode = node.leftNode
                    successor.leftNode!!.parentNode = successor
                }
            }
            newMap()
        }
    }

    private fun decimalPos (node: Node): Long {
        val depth = node.position.length
        if (depth == 0) return 0
        val posInRow = toDecimal(node.position.toString())

        return ( (2.0).pow(depth) + posInRow - 1 ).toLong()

    }
    private  fun toDecimal(num: String): Int {
        val length = num.length
        var number = num.toLong()
        var decimalNumber = 0
        var remainder: Long

        for (i in 0..length){
            remainder = number % 10
            number /= 10
            decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        }
        return decimalNumber
    }

    fun printTree() {
        var a: Int
        var c: Int
        (1..(height + 1)).forEach { i ->
            a = ((2.pow(i) - 2) / 2)
            c = (2.pow(i) - 2)
            val pad = 2.pow(height - i + 2)

            print("".padEnd(pad))
            for (k in a..c) printNode(k, 2 * pad)
            println()
            if (i <= height) {
                print("".padEnd(pad / 2))
                printSlash(pad, 2.pow(i))
            }
            println()
        }
    }

    private fun printSlash(pad: Int, i: Int) {
        var slash: Slash = Slash.LEFT

        repeat(i) {
            if (slash == Slash.LEFT) {
                print(" /".padEnd(pad - 1))
                slash = Slash.Right
            }
            else if (slash == Slash.Right){
                print("\\".padEnd(pad + 1))
                slash = Slash.LEFT
            }
        }

    }

    private fun printNode(k: Int, pad: Int){
        val temp = if (k !in treeMap.keys) "__".padEnd(pad)
        else treeMap.getValue(k).toString().padEnd(pad)
        print(temp)
    }

    private fun search(num: Int): Node? {
        var node = rootNode
        while(node != null)  {
            if (node.value == num)
                return node
            node = if (node.value > num )
                node.leftNode else node.rightNode
        }

        return node
    }

    fun searchPath(num: Int) {
        var node = rootNode
        var msg = "Number $num was not found."
        while(node != null)  {
            println(node.value)
            if (node.value == num) {
                msg = "Found!"
                break
            }
            node = if (node.value > num )
                node.leftNode else node.rightNode
        }
        println(msg)
    }

    private fun newPosString(node: Node){
        var tempNode = node
        node.position.clear()
        while (tempNode !== rootNode){
            if (tempNode.value < tempNode.parentNode!!.value)
                node.position.insert(0, "0")
            else
                node.position.insert(0, "1")
            tempNode = tempNode.parentNode!!

        }
        treeMap[decimalPos(node).toInt()] = node.value
    }

    private fun transverseNewPos(node: Node){
        newPosString(node)
        node.leftNode?.let { transverseNewPos(node.leftNode!!) }
        node.rightNode?.let { transverseNewPos(node.rightNode!!) }
    }

    private fun newMap(){
        treeMap.clear()
        transverseNewPos(rootNode!!)
        treeMap[0] = rootNode!!.value
        height = log(treeMap.keys.max()!!.toDouble() + 1, 2.0).toInt()
    }

}

private fun Int.pow(i: Int): Int {
    return this.toDouble().pow(i).toInt()
}






