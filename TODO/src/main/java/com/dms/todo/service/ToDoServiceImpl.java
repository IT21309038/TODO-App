package com.dms.todo.service;

import com.dms.todo.entity.ToDo;
import com.dms.todo.exception.ToDoException;
import com.dms.todo.repo.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService{

    @Autowired
    private ToDoRepo toDoRepo;

    @Override
    public ToDo createToDoTask(ToDo toDo) throws ToDoException {
        String title = toDo.getTitle();
        String description = toDo.getDescription();
        Date due_date = toDo.getDue_date();

        Optional<ToDo> existingToDo = toDoRepo.findById(toDo.getId());

        if (existingToDo.isPresent()) {
            throw new ToDoException(ToDoException.AlreadyExists());
        }

        ToDo newToDo = new ToDo();
        newToDo.setTitle(title);
        newToDo.setDescription(description);
        newToDo.setDue_date(due_date);
        newToDo.setStatus("Pending");
        newToDo.set_completed(false);
        newToDo.setCreatedAt(new Date(System.currentTimeMillis()));
        newToDo.setUpdatedAt(new Date(System.currentTimeMillis()));

        toDoRepo.save(newToDo);
        return newToDo;
    }

    @Override
    public List<ToDo> getAllToDoTasks() {
        List<ToDo> toDoList = toDoRepo.findAll();
        if (!toDoList.isEmpty()) {
            return toDoList;
        }else {
            return new ArrayList<ToDo>();
        }
    }

    @Override
    public ToDo getSingleToDoTaskById(String id) throws ToDoException {
        Optional<ToDo> toDo = toDoRepo.findById(id);
        if (!toDo.isPresent()) {
            throw new ToDoException(ToDoException.NotFoundException(id));
        } else {
            return toDo.get();
        }
    }

    @Override
    public void updateToDoTask(String id, ToDo toDo) throws ToDoException {
        Optional<ToDo> toDoWithId = toDoRepo.findById(id);

        if (toDoWithId.isPresent()) {
            ToDo existingToDo = toDoWithId.get();

            existingToDo.setTitle(toDo.getTitle());
            existingToDo.setDescription(toDo.getDescription());
            existingToDo.setDue_date(toDo.getDue_date());
            existingToDo.setStatus(toDo.getStatus());
            existingToDo.set_completed(toDo.is_completed());
            existingToDo.setUpdatedAt(new Date(System.currentTimeMillis()));

            toDoRepo.save(existingToDo);
        } else {
            throw new ToDoException(ToDoException.NotFoundException(id));
        }
    }

    @Override
    public void deleteToDoTaskById(String id) throws ToDoException {
        Optional<ToDo> toDo = toDoRepo.findById(id);
        if (!toDo.isPresent()) {
            throw new ToDoException(ToDoException.NotFoundException(id));
        } else {
            toDoRepo.deleteById(id);
        }
    }

    @Override
    public void markToDoTaskAsCompleted(String id) throws ToDoException {
        Optional<ToDo> toDo = toDoRepo.findById(id);
        if (!toDo.isPresent()) {
            throw new ToDoException(ToDoException.NotFoundException(id));
        } else {
            ToDo existingToDo = toDo.get();
            existingToDo.setStatus("Completed");
            existingToDo.set_completed(true);
            existingToDo.setUpdatedAt(new Date(System.currentTimeMillis()));

            toDoRepo.save(existingToDo);
        }
    }
}
