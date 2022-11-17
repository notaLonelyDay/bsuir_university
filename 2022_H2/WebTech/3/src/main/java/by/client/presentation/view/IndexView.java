package main.java.by.client.presentation.view;

import main.java.by.client.entity.user.User;
import main.java.by.client.service.StudentClientService;

public class IndexView extends PresentationView {
    public IndexView(StudentClientService studentService, User user) {
        super(studentService, user);
    }

    @Override
    public void show() {
        System.out.println("1: Get\n2: exit");
    }

    @Override
    public PresentationView getInput(String input) {
        return switch (input) {
            case "1" -> new GetSelectView(this.studentService, this.currentUser);
            case "2" -> null;
            default -> throw new IllegalArgumentException();
        };
    }
}