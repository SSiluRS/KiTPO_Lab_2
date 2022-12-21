import builder.UserTypeBuilder
import factory.UserFactory
import list.List_
import ui.UISerialisation

object Main {
  private def test(builder: UserTypeBuilder): Unit = {

    System.out.print("Elements count\t|\tCreate count\n");
    var i = 1
    while ( {
      i < 2000
    }) {
      val n = i * 100
      System.out.print(n+"\t");
      var list = new List_
      for (j <- 0 until n) {
        list.add(builder.create)
      }
      val start = System.nanoTime
      var a: (List_, Int) = (null, 0)
      var cnt = 0
      try {
        a = list.mergeSortFuncStyle(builder.getComparator, 0)
        cnt = a._2
        list = a._1
      }
      catch {
        case ignored: StackOverflowError =>
          System.err.println("Stack error")
          return
      }
      val end = System.nanoTime
      System.out.println(cnt + "\t");

      i *= 2
    }
  }

  def main(args: Array[String]): Unit = {

    val userTypeList = List("Double", "PolarVector")


    for (s <- userTypeList) {
      println(s)
      val userFactory = new UserFactory()
      val builder = userFactory.getBuilder(s)
      test(builder)
      var list = new List_()
      for (j <- 0 until 10) {
        list.add(builder.create)
      }
      System.out.println("initial")
      list.forEach(System.out.println)
      list = list.mergeSortFuncStyle(builder.getComparator,0)._1
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
      System.out.println("\n")
      System.out.println("Loaded List_:")
      list1.forEach(System.out.println)
      System.out.println("\n")
    }
  }
}
