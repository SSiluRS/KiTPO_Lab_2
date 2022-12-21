package ui

import factory.UserFactory
import list.{IList, List_}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.scene.control.{Button, ComboBox, ListView, TextArea}
import scalafx.scene.paint.Color.White
import scalafx.scene.text.Font

object UI extends JFXApp3 {
  val userFactory = new UserFactory
  var types: List[String] = userFactory.getAllTypes
  var sceneNum = 0
  var scene1: Scene = _
  var scene2: Scene = _
  var selectedType = "Double"
  var userType = userFactory.getBuilder(selectedType)
  var list: IList = new List_
  val uiSerialisation = new UISerialisation

  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "Lab2"
    }
    var listElems = new ListView[Any]
    listElems.layoutX = 150
    listElems.layoutY = 20
    listElems.setPrefWidth(300)
    listElems.setPrefHeight(200)

    val outTextField = new TextArea
    outTextField.layoutX = 190
    outTextField.layoutY = 20
    outTextField.setPrefWidth(345)
    outTextField.setPrefHeight(525)
    outTextField.setEditable(false)
    outTextField.setFont(new Font("Arial", 14))

    scene1 = new Scene(600, 500) {
      val comboBox = new ComboBox[String](types)
      comboBox.setPrefWidth(200)
      comboBox.layoutX = 200
      comboBox.layoutY = 240
      content = List(comboBox)
      comboBox.onAction = (e: ActionEvent) => {
        val item = comboBox.selectionModel.apply.getSelectedItem
        selectedType = item
        userType = userFactory.getBuilder(selectedType)
        stage.setScene(scene2)
      }
    }
    scene2 = new Scene(600, 500) {
      fill = White
      val addBtn = new Button("Добавить элемент")
      addBtn.setPrefWidth(200)
      addBtn.layoutX = 200
      addBtn.layoutY = 240
      val insertBtn = new Button("Вставить элемент перед выбранным")
      insertBtn.setPrefWidth(200)
      insertBtn.layoutX = 200
      insertBtn.layoutY = 280
      val deleteBtn = new Button("Удалить выбранный элемент")
      deleteBtn.setPrefWidth(200)
      deleteBtn.layoutX = 200
      deleteBtn.layoutY = 320
      val sortBtn = new Button("Сортировка")
      sortBtn.setPrefWidth(200)
      sortBtn.layoutX = 200
      sortBtn.layoutY = 360
      val saveBtn = new Button("Сохранить в бинарный файл")
      saveBtn.setPrefWidth(200)
      saveBtn.layoutX = 200
      saveBtn.layoutY = 400
      val loadBtn = new Button("Загрузить из бинарного файла")
      loadBtn.setPrefWidth(200)
      loadBtn.layoutX = 200
      loadBtn.layoutY = 440

      content = List(addBtn, insertBtn, deleteBtn, sortBtn, saveBtn, loadBtn, listElems)

      addBtn.onAction = (e: ActionEvent) => {
        val elem = userType.create
        list.add(elem)
        listElems.getItems.clear()
        for (i <- 0 to list.size - 1) {
          listElems.getItems.add(list.get(i))
        }
      }

      deleteBtn.onAction = (e: ActionEvent) => {
        if (listElems.getItems.length > 0 && list.size > 0) {
          val elem = listElems.selectionModel.apply.getSelectedIndex
          list.remove(elem)
          listElems.getItems.remove(elem)
        }
      }

      insertBtn.onAction = (e: ActionEvent) => {
        if (listElems.getItems.length > 0 && list.size > 0) {
          val idx = listElems.selectionModel.apply.getSelectedIndex
          if (idx > 0) {
            val elem = userType.create
            list.add(elem, idx)
            listElems.getItems.clear()
            for (i <- 0 to list.size - 1) {
              listElems.getItems.add(list.get(i))
            }
          }
        }
      }

      sortBtn.onAction = (e: ActionEvent) => {
        if (listElems.getItems.length > 0 && list.size > 0) {
          list = list.mergeSortFuncStyle(userType.getComparator,0)._1
          listElems.getItems.clear()
          listElems.items
          for (i <- 0 to list.size - 1) {
            listElems.getItems.add(list.get(i))
          }
        }
      }

      saveBtn.onAction = (e: ActionEvent) => {
        uiSerialisation.saveToFile("file.dat", list, userType)
      }
      loadBtn.onAction = (e: ActionEvent) => {
        list.removeAll()
        list = uiSerialisation.loadFromFile("file.dat", userType, list)
        listElems.getItems.clear()
        for (i <- 0 to list.size - 1) {
          listElems.getItems.add(list.get(i))
        }
      }

    }
    stage.setScene(scene1)


  }
}
