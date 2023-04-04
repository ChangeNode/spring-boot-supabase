package com.changenode.frisson.data;


import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "todos", schema = "public")
public class ToDo {
    private Long id;
    private UUID userId;
    private String task;
    private Boolean isComplete;
    private Instant insertedAt;

    @Id()
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "task")
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Basic
    @Column(name = "is_complete")
    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    @Basic
    @Column(name = "inserted_at", nullable = false)
    public Instant getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(Instant insertedAt) {
        this.insertedAt = insertedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo that = (ToDo) o;
        return id == that.id && Objects.equals(userId, that.userId) && Objects.equals(task, that.task) && Objects.equals(isComplete, that.isComplete) && Objects.equals(insertedAt, that.insertedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, task, isComplete, insertedAt);
    }
}
