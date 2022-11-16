package factory

import builder.{DoubleObjectBuilder, PolarVectorObjectBuilder, UserTypeBuilder}

class UserFactory {
  val builders: List[UserTypeBuilder] = List(new DoubleObjectBuilder, new PolarVectorObjectBuilder)

  def getAllTypes: List[String] = {
    var list: List[String] = List()
    for (t <- builders) {
      list = list ++: List(t.typeName)
    }
    list
  }

  def getBuilder(name: String): UserTypeBuilder = {
    if (name == null) throw new NullPointerException
    for (b <- builders) {
      if (name == b.typeName) return b
    }
    throw new IllegalArgumentException
  }
}
