package builder

class PolarVector {
  private var length = .0
  private var angle = .0

  def getAngle: Double = angle

  def getLength: Double = length

  def setAngle(angle: Double): Unit = {
    this.angle = angle
  }

  def setLength(length: Double): Unit = {
    this.length = length
  }

  override def toString: String = "" + getLength + " " + getAngle
}
