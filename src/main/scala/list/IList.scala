package list

import comparator.Comparator

trait IList {
  def add(data: Any): Unit

  def get(index: Int): Any

  def removeAll(): Unit

  def remove(index: Int): Unit

  def size: Int

  def add(data: Any, index: Int): Unit

  def forEach(a: Action): Unit

  def sort(comparator: Comparator): Unit

  def mergeSortFuncStyle(comparator: Comparator, cnt: Int): (List_, Int)
}
