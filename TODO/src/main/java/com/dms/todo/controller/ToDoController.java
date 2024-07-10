package com.dms.todo.controller;

import com.dms.todo.ResponseHandler;
import com.dms.todo.entity.ToDo;
import com.dms.todo.exception.ToDoException;
import com.dms.todo.repo.ToDoRepo;
import com.dms.todo.service.ToDoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/todo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private ToDoRepo toDoRepo;

    @PostMapping(value = "/Todos")
    public ResponseEntity<?> createToDoTask(@RequestBody ToDo todo){
        try{
            toDoService.createToDoTask(todo);
            return ResponseHandler.responseBuilder("Todo created successfully", HttpStatus.CREATED, null);
        } catch (ConstraintViolationException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, null);
        } catch (ToDoException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.CONFLICT, null);
        }
    }

    @GetMapping(value = "/Todos")
    public ResponseEntity<?> getAllToDoTasks(){
        List<ToDo> todos = toDoService.getAllToDoTasks();
        return ResponseHandler.responseBuilder("List of todos", !todos.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND, todos);
    }

    @GetMapping(value = "/Todos/{id}")
    public ResponseEntity<?> getSingleToDoTaskById(@PathVariable("id") String id){
        try{
            ToDo todo = toDoService.getSingleToDoTaskById(id);
            return ResponseHandler.responseBuilder("Todo found", HttpStatus.OK, todo);
        } catch (ToDoException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping(value = "/Todos/{id}")
    public ResponseEntity<?> updateToDoTask(@PathVariable("id") String id, @RequestBody ToDo todo){
        try{
            toDoService.updateToDoTask(id, todo);
            return ResponseHandler.responseBuilder("Todo with id " + id + " updated successfully", HttpStatus.OK, todo);
        } catch (ConstraintViolationException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, null);
        } catch (ToDoException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping(value = "/Todos/{id}")
    public ResponseEntity<?> deleteToDoTask(@PathVariable("id") String id){
        try{
            toDoService.deleteToDoTaskById(id);
            return ResponseHandler.responseBuilder("Todo with id " + id + " deleted successfully", HttpStatus.OK, null);
        } catch (ToDoException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping(value = "/Todos/{id}/complete")
    public ResponseEntity<?> completeToDoTask(@PathVariable("id") String id){
        try{
            toDoService.markToDoTaskAsCompleted(id);
            return ResponseHandler.responseBuilder("Todo with id " + id + " completed successfully", HttpStatus.OK, null);
        } catch (ToDoException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}
