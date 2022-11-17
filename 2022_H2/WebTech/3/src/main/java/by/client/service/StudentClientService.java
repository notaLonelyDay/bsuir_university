package main.java.by.client.service;

import main.java.by.client.entity.Student;
import main.java.by.client.entity.user.User;

import java.util.List;

public interface StudentClientService {

    boolean edit(Student newValue);

    List<Student> getAll();

    Student get(int id);

    boolean create(Student student);

    User register(User user);

    User login(User user);
}
