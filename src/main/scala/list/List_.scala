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

  def mergeSortFuncStyle(comparator: Comparator, cnt: Int): (List_, Int) = {
    if (this.length <= 1)
      (this, cnt)
    else {
      var cnt_rez = 0
      val sortedList = new List_
      var leftList = new List_
      var rightList = new List_
      val middle = this.length / 2
      for (i <- 0 until middle){
        leftList.add(this.get(i))
      }
      for (i <- middle until this.length){
        rightList.add(this.get(i))
      }
      var a = (leftList,cnt)
      var b = (rightList,cnt)
      a = leftList.mergeSortFuncStyle(comparator, 0)
      leftList = a._1
      cnt_rez = cnt_rez + a._2
      b = rightList.mergeSortFuncStyle(comparator, 0)
      rightList = b._1
      cnt_rez = cnt_rez + b._2

      //Итератор
      var left = leftList.head
      var right = rightList.head
      var leftHead = 0
      var rightHead = 0

      if (leftList.length == 1 && rightList.length == 1) {
        if (comparator.compare(left.data.get, right.data.get) > 0) {
          sortedList.add(right.data.get)
          cnt_rez = cnt_rez + 1
          sortedList.add(left.data.get)
          cnt_rez = cnt_rez + 1
        }
        if (comparator.compare(left.data.get, right.data.get) <= 0) {
          sortedList.add(left.data.get)
          cnt_rez = cnt_rez + 1
          sortedList.add(right.data.get)
          cnt_rez = cnt_rez + 1
        }
      }
      else {
        while (leftHead < leftList.length && rightHead < rightList.length) {
          if (left!=null && comparator.compare(left.data.get, right.data.get ) > 0) {
            sortedList.add(right.data.get)
            cnt_rez = cnt_rez + 1
            right = right.next
            rightHead = rightHead + 1
          }
          if (right != null && comparator.compare(left.data.get, right.data.get) <= 0 ) {
            sortedList.add(left.data.get)
            cnt_rez = cnt_rez + 1
            left = left.next
            leftHead = leftHead + 1
          }
        }

        if (rightHead == rightList.length) {
          while (leftHead < leftList.length) {
            sortedList.add(left.data.get)
            cnt_rez = cnt_rez + 1
            left = left.next
            leftHead = leftHead + 1
          }
        }

        if (leftHead == leftList.length) {
          while (rightHead < rightList.length) {
            sortedList.add(right.data.get)
            cnt_rez = cnt_rez + 1
            right = right.next
            rightHead = rightHead + 1
          }
        }
      }
      (sortedList, cnt_rez)
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
