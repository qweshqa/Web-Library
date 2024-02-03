package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> index(){
        return bookRepository.findAll();
    }

    public List<Book> index(int page, int booksPerPage, boolean sortByYear){
        List<Book> list;
        if(sortByYear){
            list = bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        else{
            list = bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
        return list;
    }

    public Optional<Book> show(int id){
        return bookRepository.findById(id);
    }
    public List<Book> findByTitleStartingWith(String s){
        return bookRepository.findByTitleStartingWith(s);
    }
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }
    @Transactional
    public void assign(int bookId, int personId){
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Person> person = personRepository.findById(personId);

        if(book.isPresent() && person.isPresent()){
            book.get().setOwner(person.get());
            person.get().getBooks().add(book.get());
            book.get().setAssigningDate(LocalDate.now());
        }
    }
    @Transactional
    public void release(int bookId){
        Optional<Book> book = bookRepository.findById(bookId);

        if(book.isPresent()){
            Person person = book.get().getOwner();
            if(person != null){
                book.get().setOwner(null);
                person.getBooks().remove(book);
                book.get().setAssigningDate(null);
            }
        }
    }
}
