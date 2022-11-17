package main.java.by.client.service.impl;

import main.java.by.client.dao.StudentClientDAO;
import main.java.by.client.entity.Student;
import main.java.by.client.entity.user.User;
import main.java.by.client.service.StudentClientService;

import java.util.List;

public class StudentClientServiceImpl implements StudentClientService {
    private final StudentClientDAO studentDAO;

    public StudentClientServiceImpl(StudentClientDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public boolean edit(Student newValue) {
        return this.studentDAO.edit(newValue);
    }

    @Override
    public List<Student> getAll() {
        return this.studentDAO.getAll();
    }

    @Override
    public Student get(int id) {
        return this.studentDAO.get(id);
    }

    @Override
    public boolean create(Student student) {
        return this.studentDAO.create(student);
    }

    @Override
    public User register(User user) {
        return this.studentDAO.register(user);
    }

    @Override
    public User login(User user) {
        return this.studentDAO.login(user);
    }
}
