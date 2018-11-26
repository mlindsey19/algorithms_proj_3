package algorithms.binarySearchTree

import java.lang.StringBuilder
import kotlin.math.pow

data class Node(var value: Int, var position: StringBuilder = StringBuilder(), var leftNode: Node? = null,
                var rightNode: Node? = null, var parentNode: Node? = null, var depth: Int = 0)

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
            else node.rightNode.also {newNode.position.append(1) } //append to string for printing position
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

        treeMap[decimalPos(newNode).toInt()] = newNode.value
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
    fun maximum (node: Node): Node {
        node.rightNode?.let { return maximum( node.rightNode!! ) }
        return node
    }
    private fun successor(node: Node): Node? {
        node.rightNode?.let { return minimum(node.rightNode!!) }
        val parentNode = node.parentNode
        return if (node == parentNode?.rightNode) successor(node.parentNode!!) else parentNode
    }
    fun delete (num: Int) {
        val node: Node? = query(num)
        node?.let {
            // set successor children to node children         //and set successor child to left-child of parent
            node.rightNode?.let {
                val successor = successor(node)
                successor!!.rightNode = node.rightNode.also {
                    successor.leftNode = node.leftNode.also {
                        successor.parentNode?.leftNode = successor.leftNode
                        if (node.value < node.parentNode!!.value) node.parentNode!!.leftNode = successor
                        else node.parentNode!!.rightNode = successor
                        successor.parentNode = node.parentNode
                    }
                }
                treeMap.clear()
                transverseNewPos(rootNode)
                return
            }
            //else right-child-node has null left-child link new node.right to node.parent.right
            node.parentNode!!.rightNode = node.rightNode
            node.rightNode!!.parentNode = node.parentNode
            treeMap.clear()
            transverseNewPos(rootNode)
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
        println(this.rootNode!!.value.toString().padStart( ( 2 * height.pow(2)  )  ) )

        for ( i in 1..height ) {
            a = (2.0).pow(i).toInt() - 1
            b = (((3 * (2.0).pow(i) ) - 2) / 2).toInt() - 1
            c = ((2.0).pow(i + 1) - 2).toInt()
            val pad = 3 * ( i + 1 )

            print( "".padEnd( 2*( height - i ).pow(2)  ) )
            printSlash( pad, ( c - a + 1) )
            print( "".padEnd( 2*( height - i ).pow(2)  ) )

            for (k in a..b) printNode( k, pad )
            print("".padEnd(2 * ( height - i ) ))
            for (j in (b + 1)..c) printNode(j, pad)

            println()
        }
    }

    private fun printSlash(pad: Int, i: Int) {
        var slash: Slash = Slash.LEFT

        repeat(i) {
            if (slash == Slash.LEFT) {
                print("/".padEnd(pad))
                slash = Slash.Right
            }
            else if (slash == Slash.Right){
                print("\\".padEnd(pad))
                slash = Slash.LEFT
            }
        }
        println()
    }

    private fun printNode(k: Int, pad: Int){

        val temp = if (k !in treeMap.keys) "__".padEnd( pad )
        else treeMap.getValue(k).toString().padEnd(pad)
        print(temp)
    }

    fun query( num: Int): Node? {
        var node = rootNode
        while(node != null)  {
            if (node.value == num)
                return node
            if (node.value > num )
                node = node.leftNode
            if (node!!.value < num)
                node = node.rightNode
        }

        return node
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

    private fun transverseNewPos(node: Node?){
        newPosString(node!!)
        node.rightNode?.let { transverseNewPos(node.rightNode) }
        node.leftNode?.let { transverseNewPos(node.leftNode) }
    }

}



private fun Int.pow(i: Int): Int {
    return this.toDouble().pow(i).toInt()
}
enum class Slash{ LEFT, Right }






