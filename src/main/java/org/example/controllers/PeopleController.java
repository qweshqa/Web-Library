package org.example.controllers;

import org.example.models.Book;
import org.example.models.Person;
import org.example.services.PersonService;
import org.example.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
//    private final PersonDAO personDAO;
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.index());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personService.show(id);

        if(person.isPresent()){
            Person finalPerson = person.get();
            model.addAttribute("person", finalPerson);

            if(finalPerson.getBooks().isEmpty()){
                model.addAttribute("nothing", "nothing here lol");
            }
            else{
                personService.validateOverdue(finalPerson);
                model.addAttribute("books", finalPerson.getBooks());
            }
        }
        return "/people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/people/new";

        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        personService.show(id).ifPresent(person -> model.addAttribute("person", person));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/people/edit";

        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
