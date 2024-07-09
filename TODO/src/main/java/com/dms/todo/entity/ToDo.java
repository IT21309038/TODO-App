package com.dms.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")
public class ToDo {

    @Id
    private String id;

    private String title;
    private String description;
    private Date due_date;
    private String status;
    private boolean is_completed;
    private Date createdAt;
    private Date updatedAt;
}
