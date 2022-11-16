package ui

import builder.UserTypeBuilder
import list.IList

import java.io.{BufferedReader, BufferedWriter, FileNotFoundException, FileReader, FileWriter, IOException}

class UISerialisation {
  @throws[FileNotFoundException]
  def saveToFile(filename: String, list: IList, builder: UserTypeBuilder): Unit = {
    try {
      val writer = new BufferedWriter(new FileWriter(filename))
      try {
        writer.write(builder.typeName + "\n")
        list.forEach(el => {
          try writer.write(builder.toString(el) + "\n")
            catch {
              case e: IOException =>
                e.printStackTrace()
            }
          })
      } catch {
        case e: IOException =>
          e.printStackTrace()
      } finally if (writer != null) writer.close()
    }
  }

  @throws[Exception]
  def loadFromFile(filename: String, builder: UserTypeBuilder, list: IList): IList = try {
    val br = new BufferedReader(new FileReader(filename))
    try {
      var line = br.readLine
      if (!builder.typeName.equals(line)) throw new Exception("Wrong file structure")
      line = br.readLine
      while ( line != null){
        list.add(builder.createFromString(line))
        line = br.readLine()
      }
      list
    } finally if (br != null) br.close()
  }
}
