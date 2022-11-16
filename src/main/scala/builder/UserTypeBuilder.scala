package builder

import comparator.Comparator

trait UserTypeBuilder {
  def typeName: String

  def create: Any

  def getComparator: Comparator

  def createFromString(s: String): Any

  def toString(obj: Any): String
}
