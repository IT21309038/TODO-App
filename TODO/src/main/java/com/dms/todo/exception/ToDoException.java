package com.dms.todo.exception;

public class ToDoException extends Exception{

    private static final long serialVersionUID = 1L;

    public ToDoException(String message) {
        super(message);
    }

    public static String NotFoundException(String todoId) {
        return "ToDo with " + todoId + " not found";
    }

    public static String AlreadyExists() {
        return "ToDo with given title already exists";
    }
}
