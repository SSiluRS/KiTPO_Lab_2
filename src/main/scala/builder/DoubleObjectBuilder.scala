package builder

import comparator.Comparator

import java.util.Random

class DoubleObjectBuilder extends UserTypeBuilder {
  override def typeName = "Double"

  override def create: Double = new Random().nextDouble

  override def createFromString(s: String): Double = s.toDouble

  override def toString(`object`: Any): String = `object`.toString

  override def getComparator: Comparator = (o1: Any, o2: Any) => o1.asInstanceOf[Double] - o2.asInstanceOf[Double]


}
