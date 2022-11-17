package main.java.by.client.presentation.view;

import main.java.by.client.entity.user.User;
import main.java.by.client.presentation.viewModel.PresentationModel;
import main.java.by.client.service.StudentClientService;

public abstract class PresentationView {
    protected PresentationModel model;
    protected StudentClientService studentService;
    protected User currentUser;

    protected PresentationView(StudentClientService studentService, User user) {
        this.studentService = studentService;
        this.currentUser = user;
    }

    public abstract void show();
    public abstract PresentationView getInput(String input);
}
