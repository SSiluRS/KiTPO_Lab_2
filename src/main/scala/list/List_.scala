package list

import builder.UserTypeBuilder
import comparator.Comparator

class List_ extends IList {
  private var head: Node = _
  private var tail: Node = _
  private var length: Int = 0

  def add(data: Any): Unit = {
    if (head == null) {
      head = new Node(Some(data))
      tail = head
      length += 1
      return
    }
    val newTail = new Node(Some(data))
    newTail.prev = tail
    tail.next = newTail
    tail = newTail
    length += 1
  }

  def get(index: Int): Any = getNode(index).data.get

  def forEach(action: Any => Unit): Unit = {
    var tmp = head
    for (_ <- 0 until length) {
      action(tmp.data.get)
      tmp = tmp.next
    }
  }

  def toArray(): Array[Any] ={
    val arr = new Array[Any](size)
    for(i <- 0 to size-1){
      arr(i) = this.get(i)
    }
    arr
  }

  def fromArray(arr: Array[Any]) ={
    removeAll()
    for(i <- 0 to arr.length-1){
      this.add(arr(i))
    }
  }

  def size: Int = length

  def removeAll(): Unit = {
    while (size != 1){
      remove(1)
    }
    head = null
    length = 0
  }

  def remove(index: Int): Unit = {
    val tmp = getNode(index)
    if (tmp != head) {
      tmp.prev.next = tmp.next
    }
    else {
      head = tmp.next
    }
    if (tmp != tail) {
      tmp.next.prev = tmp.prev
    }
    else {
      tail = tmp.prev
    }
    tmp.next = null
    tmp.prev = null
    length -= 1
  }

  def add(data: Any, index: Int): Unit = {
    val tmp = getNode(index)
    val newNode = new Node(Some(data))
    if (tmp != null) {
      tmp.prev.next = newNode
      newNode.prev = tmp.prev
    }
    else {
      head = newNode
    }
    newNode.next = tmp
    tmp.prev = newNode
    length += 1
  }

  def forEach(a: Action): Unit = {
    var tmp = head
    for (_ <- 0 until length) {
      a.toDo(tmp.data.get)
      tmp = tmp.next
    }
  }

  def sortFunc(xs: Array[Any], comparator: Comparator): Array[Any] = {
    if (xs.length <= 1) xs
    else {
      val pivot = xs(xs.length / 2)
      Array.concat(
        sortFunc(xs.filter(comparator.compare(pivot,_)>0),comparator),
        xs.filter(comparator.compare(pivot,_)==0),
        sortFunc(xs.filter(comparator.compare(pivot,_)<0),comparator))
    }
  }

  def sort(comparator: Comparator): Unit = {
    head = mergeSort(head, comparator)
    tail = getNode(length - 1)
  }

  private def mergeSort(h: Node, comparator: Comparator): Node = {
    if (h == null || h.next == null) {
      return h
    }

    val middle = getMiddle(h)
    val middleNext = middle.next

    middle.next = null

    val left = mergeSort(h, comparator)
    val right = mergeSort(middleNext, comparator)

    merge(left, right, comparator)
  }

  private def merge(head11: Node, head22: Node, comparator: Comparator) = {
    var left = head11
    var right = head22
    val merged = new Node(None)
    var temp = merged
    while ( {
      left != null && right != null
    }) {
      if (comparator.compare(left.data.get, right.data.get) < 0) {
        temp.next = left
        left.prev = temp
        left = left.next
      }
      else {
        temp.next = right
        right.prev = temp
        right = right.next
      }
      temp = temp.next
    }
    while ( {
      left != null
    }) {
      temp.next = left
      left.prev = temp
      left = left.next
      temp = temp.next
    }
    while ( {
      right != null
    }) {
      temp.next = right
      right.prev = temp
      right = right.next
      temp = temp.next
      this.tail = temp
    }
    merged.next
  }

  private def getMiddle(h: Node) = {
    var fast = h.next
    var slow = h
    while (fast != null) {
      fast = fast.next
      if (fast != null) {
        slow = slow.next
        fast = fast.next
      }
    }
    slow
  }

  private def getNode(index: Int): Node = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException()
    var tmp = head
    for (_ <- 0 until index) {
      tmp = tmp.next
    }
    tmp
  }

  class Node(var data: Option[Any]) {
    var next: Node = _
    var prev: Node = _
  }
}
