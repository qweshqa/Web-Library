package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 30, message = "Title's length should be between 1 and 30 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, max = 30, message = "Title's length should be between 1 and 30 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 1903, message = "Year of tribute should be higher than 1903")
    @Max(value = 2023, message = "Year of tribute should be smaller than 2023")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "holder_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Person owner;
    @Column(name = "assigningDate")
    private LocalDate assigningDate;
    @Transient
    private boolean isOverdue;

    public Book(){

    }

    public Book(int id, String title, String author, int year, Person owner){
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int yearOfTribute) {
        this.year = yearOfTribute;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public LocalDate getAssigningDate() {
        return assigningDate;
    }

    public void setAssigningDate(LocalDate assigningDate) {
        this.assigningDate = assigningDate;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }
}
