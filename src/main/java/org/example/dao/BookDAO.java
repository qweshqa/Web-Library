package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
//  private final JdbcTemplate jdbcTemplate;

//    private final SessionFactory sessionFactory;
//
//    @Autowired
//    public BookDAO(SessionFactory sessionFactory){
//        this.sessionFactory = sessionFactory;
//    }
//    @Transactional(readOnly = true)
//    public List<Book> index(){
//        Session session = sessionFactory.getCurrentSession();
//
//        return session.createQuery("select b from Book b", Book.class).getResultList();
//    }
//    @Transactional(readOnly = true)
//    public Book show(int id){
//        Session session = sessionFactory.getCurrentSession();
//
//        return session.get(Book.class, id);
//    }
//    @Transactional
//    public void save(Book book){
//        Session session = sessionFactory.getCurrentSession();
//        session.save(book);
//    }
//    @Transactional
//    public void update(int id, Book updatedBook){
//        Session session = sessionFactory.getCurrentSession();
//
//        Book book = session.get(Book.class, id);
//
//        book.setTitle(updatedBook.getTitle());
//        book.setAuthor(updatedBook.getAuthor());
//        book.setYearOfTribute(updatedBook.getYearOfTribute());
//    }
//    @Transactional
//    public void delete(int id){
//        Session session = sessionFactory.getCurrentSession();
//
//        session.remove(session.get(Book.class, id));
//    }
//    @Transactional(readOnly = true)
//    public Person getHolder(int id){
//        Session session = sessionFactory.getCurrentSession();
//
//        return session.get(Book.class, id).getOwner();
//    }
//    @Transactional
//    public void assign(int bookId, int personId){
//        Session session = sessionFactory.getCurrentSession();
//
//        Book book = session.get(Book.class, bookId);
//        Person person = session.get(Person.class, personId);
//
//        person.getBooks().add(book);
//        book.setOwner(person);
//    }
//    @Transactional
//    public void release(int bookId){
//        Session session = sessionFactory.getCurrentSession();
//
//        Book book = session.get(Book.class, bookId);
//        Person person = book.getOwner();
//
//        book.setOwner(null);
//        person.getBooks().remove(book);
//    }
}
