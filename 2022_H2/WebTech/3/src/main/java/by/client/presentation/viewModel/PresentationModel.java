package main.java.by.client.presentation.viewModel;

import main.java.by.client.entity.Student;
import main.java.by.client.service.StudentClientService;

import java.util.List;

public abstract class PresentationModel {
    protected StudentClientService studentService;
    public PresentationModel(StudentClientService studentService) {
        this.studentService = studentService;
    }

    public abstract List<Student> getItems();
}
