package ru.mkrasikoff.springmvcapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mkrasikoff.springmvcapp.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showPeople() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class),
                new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public void savePerson(Person person) {
        Random random = new Random();
        jdbcTemplate.update("INSERT INTO person VALUES(?, ?, ?, ?)",
               random.nextInt(1024),person.getName(), person.getSurname(), person.getEmail());
    }

    public void updatePerson(Person updatedPerson, int id) {
        jdbcTemplate.update("UPDATE person SET name = ?, surname = ?, email = ? WHERE id = ?",
                updatedPerson.getName(), updatedPerson.getSurname(), updatedPerson.getEmail(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
}
