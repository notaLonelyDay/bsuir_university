package main.java.by.client.dao;

import main.java.by.client.entity.Student;
import main.java.by.client.entity.user.User;

import java.util.List;

public interface StudentClientDAO {

    boolean edit(Student newValue);

    List<Student> getAll();

    Student get(int id);

    boolean create(Student item);

    User register(User user);

    User login(User user);
}
