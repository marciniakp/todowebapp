package com.betapp.services;

import com.betapp.model.TODO;
import com.betapp.repositories.TODORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TODOService {

    private TODORepository todoRepository;

    public TODOService() {
    }

    @Autowired
    public TODOService(TODORepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TODO add(TODO todo) {
        return todoRepository.save(todo);
    }

    public List<TODO> getAll() {
        Iterable<TODO> all = todoRepository.findAll();
        ArrayList<TODO> todos = new ArrayList<>();
        all.forEach(todos::add);
        return todos;
    }

    public List<TODO> getDoneTodos() {
        return getAll().stream().filter(TODO::isDone).collect(Collectors.toList());
    }
    public List<TODO> getNotDoneTodos() {
        return getAll().stream().filter(t->!t.isDone()).collect(Collectors.toList());
    }
    public TODO getTODO(Long id) {
        return todoRepository.findOne(id);
    }
}
