package com.example.t1taskmanager.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="IdGenerator")
    @GenericGenerator(name="IdGenerator", strategy="increment", parameters = {
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "is_completed", nullable = false)
    private boolean completed = false;
}
