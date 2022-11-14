package calendarApp.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;

//import todolist.ui.util.SceneTarget;

/**
 * Controller for CalendarLogic objects.
 * Supports adding new Calendar objects and
 * selecting one for viewing and editing.
 */
public class CalendarLogicController {

  private CalendarLogicAccess calendarLogicAccess;

  @FXML
  String userTodoListPath;

  @FXML
  String sampleTodoListResource;

  @FXML
  ComboBox<String> todoListsView;

  @FXML
  CalendarViewController calendarViewController;

  /**
   * Sets the CalendarLogicAccess for this controller,
   * so data can come from different sources.
   *
   * @param calendarLogicAccess the new CalendarLogicAccess to use
   */
  public void setCalendarLogicAccess(CalendarLogicAccess calendarLogicAccess) {
    this.calendarLogicAccess = calendarLogicAccess;
    //updateCalendarView(null);
  }
/*
  @FXML
  void initialize() {
    // kobler data til list-controll
    initializeTodoListsView();
    todoListViewController.setOnTodoListChanged(todoList -> {
      todoModelAccess.notifyTodoListChanged(todoList);
      return null;
    });
  }
*/
/*
  private String addNewTodoListText = "<add new todo list>";

  private void initializeTodoListsView() {
    todoListsView.setEditable(true);
    todoListsView.valueProperty().addListener((prop, oldName, newName) -> {
      if (newName != null && (! todoModelAccess.isValidTodoListName(newName))) {
        // allow user to edit name
      } else if (oldName != null && newName != null
          && (! todoListsView.getItems().contains(newName))) {
        // either new name of dummy item or existing item
        if (addNewTodoListText.equals(oldName)) {
          // add as new list
          todoModelAccess.addTodoList(new TodoList(newName));
          updateTodoListsView(newName);
        } else {
          // update name
          todoModelAccess.renameTodoList(oldName, newName);
          updateTodoListsView(newName);
        }
      } else if (todoListsView.getSelectionModel().getSelectedIndex() == 0) {
        // run later to avoid conflicts with event processing
        Platform.runLater(() -> {
          todoListsView.getEditor().selectAll();
        });
      } else if (todoListsView.getSelectionModel().getSelectedIndex() >= 0) {
        AbstractTodoList todoList = getSelectedTodoList();
        if (! (todoList instanceof TodoList)) {
          // retrieve actual list
          todoList = .getTodoModelAccessTodoList(todoList.getName());
        }
        todoListViewController.setTodoList(todoList instanceof TodoList tl ? tl : null);
      }
    });
  }*/
/*
  AbstractTodoList getSelectedTodoList() {
    return todoModelAccess.getTodoList(todoListsView.getSelectionModel().getSelectedItem());
  }*/
/*
  protected void updateTodoListsView(String newSelection) {
    List<String> items = new ArrayList<>();
    // dummy element used for creating new ones, with null name
    items.add(addNewTodoListText);
    items.addAll(todoModelAccess.getTodoListNames());
    todoListsView.getItems().setAll(items);
    if (newSelection != null) {
      todoListsView.setValue(newSelection);
    } else {
      todoListsView.getSelectionModel().select(todoListsView.getItems().size() > 1 ? 1 : 0);
    }
  }*/
/*

