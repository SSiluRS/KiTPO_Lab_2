import builder.UserTypeBuilder
import factory.UserFactory
import list.List_
import ui.UISerialisation

object Main {
  private def test(builder: UserTypeBuilder): Unit = {
    var i = 1
    while ( {
      i < 2000
    }) {
      val n = i * 1000
      System.out.println("N = " + n)
      val list = new List_
      for (j <- 0 until n) {
        list.add(builder.create)
      }
      val start = System.nanoTime
      try list.sort(builder.getComparator)
      catch {
        case ignored: StackOverflowError =>
          System.err.println("Stack error")
          return
      }
      val end = System.nanoTime
      System.out.println("Millis elapsed " + (end - start) * 1.0 / 1_000_000)

      i *= 2
    }
  }

  def main(args: Array[String]): Unit = {

    val userTypeList = List("PolarVector", "Double")


    for (s <- userTypeList) {
      println(s)
      val userFactory = new UserFactory()
      val builder = userFactory.getBuilder(s)
      test(builder)
      val list = new List_()
      for (j <- 0 until 10) {
        list.add(builder.create)
      }
      System.out.println("initial")
      list.forEach(System.out.println)
      list.fromArray(list.sortFunc(list.toArray(),builder.getComparator))
      System.out.println("\nafter sort")
      list.forEach(System.out.println)
      list.remove(1)
      System.out.println("\nafter remove from 1 index")
      list.forEach(System.out.println)
      list.remove(0)
      System.out.println("\nafter remove from 0 index")
      list.forEach(System.out.println)
      list.add(builder.create, 1)
      System.out.println("\nafter add to 1 index")
      list.forEach(System.out.println)
      val uiSerialisation = new UISerialisation()
      uiSerialisation.saveToFile("file.dat", list, builder)

      val list1 = new List_()
      uiSerialisation.loadFromFile("file.dat", builder, list1)

      System.out.println("\nSaved List_:")
      list.forEach(System.out.println)
      System.out.println("\n\n")
      System.out.println("Loaded List_:")
      list1.forEach(System.out.println)
    }
  }
}
