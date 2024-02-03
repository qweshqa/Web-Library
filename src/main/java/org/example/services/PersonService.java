package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public List<Person> index(){
        return personRepository.findAll();
    }
    public Optional<Person> show(int id){
        return personRepository.findById(id);
    }
    public Optional<Person> show(String email){
        return personRepository.findByEmail(email);
    }
    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }
    @Transactional
    public void validateOverdue(Person person){
        for (Book b: person.getBooks()) {
            if(b.getAssigningDate().isAfter(b.getAssigningDate())){
                b.setOverdue(true);
            }
            else{
                b.setOverdue(false);
            }
        }
    }
}
