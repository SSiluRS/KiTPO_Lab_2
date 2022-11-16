package builder

import comparator.Comparator

import java.util.Random

class PolarVectorObjectBuilder extends UserTypeBuilder {
  override def typeName = "PolarVector"

  override def create: PolarVector = {
    val vector = new PolarVector
    vector.setAngle(new Random().nextDouble)
    vector.setLength(new Random().nextDouble)
    vector
  }

  override def createFromString(s: String): PolarVector = {
    val vector = new PolarVector
    vector.setLength(s.split(" ")(0).toDouble)
    vector.setAngle(s.split(" ")(1).toDouble)
    vector
  }

  override def toString(`object`: Any): String = `object`.toString

  override def getComparator: Comparator = (o1: Any, o2: Any) => (o1.asInstanceOf[PolarVector]).getLength - (o2.asInstanceOf[PolarVector]).getLength

}
