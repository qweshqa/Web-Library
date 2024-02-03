package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", defaultValue = "-1") int page,
                        @RequestParam(value = "booksPerPage", defaultValue = "0") int booksPerPage,
                        @RequestParam(value = "sortByYear", defaultValue = "false") boolean sortByYear,
                        Model model){
        if(page == -1 && booksPerPage == 0 && !sortByYear){
            model.addAttribute("books", bookService.index());
        }
        else{
            model.addAttribute("books", bookService.index(page, booksPerPage, sortByYear));
        }
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Optional<Book> optionalBook = bookService.show(id);

        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            model.addAttribute("book", book);

            if(book.getOwner() != null){
                model.addAttribute("owner", book.getOwner());
            }
            else{
                model.addAttribute("people", personService.index());
            }
        }

        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        Optional<Book> optionalBook = bookService.show(id);
        optionalBook.ifPresent(book -> model.addAttribute("book", book));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "/books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @RequestParam("person") int personId){
        bookService.assign(id, personId);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId){
        bookService.release(bookId);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/search")
    public String search(){
        return "/books/search";
    }
    @GetMapping("/search/results")
    public String searchResults(@RequestParam("search") String searchRequest, Model model){
        if(searchRequest.equals("")){ }
        else{
            model.addAttribute("books", bookService.findByTitleStartingWith(searchRequest));
        }
        return "/books/search";
    }


}
