package com.dms.todo.service;

import com.dms.todo.entity.ToDo;
import com.dms.todo.exception.ToDoException;

import java.util.List;

public interface ToDoService {

    public ToDo createToDoTask(ToDo toDo) throws ToDoException;

    public List<ToDo> getAllToDoTasks();

    public ToDo getSingleToDoTaskById(String id) throws ToDoException;

    public void updateToDoTask(String id, ToDo toDo) throws ToDoException;

    public void deleteToDoTaskById(String id) throws ToDoException;

    public void markToDoTaskAsCompleted(String id) throws ToDoException;
}
