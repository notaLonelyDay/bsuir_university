package main.java.by.client.presentation;

import main.java.by.client.entity.role.UserRole;
import main.java.by.client.entity.user.User;
import main.java.by.client.presentation.view.GuestView;
import main.java.by.client.presentation.view.PresentationView;
import main.java.by.client.service.StudentClientService;

import java.util.Scanner;

public class Presentation {
    private PresentationView view;
    private User currentUser = null;
    public Presentation(StudentClientService studentService) {
        this.currentUser = new User();
        this.currentUser.setRole(UserRole.GUEST);
        this.view = new GuestView(studentService, this.currentUser);
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (this.view != null) {
            this.view.show();
            while (!getInput(scanner.nextLine())) {
                System.out.println("Invalid input");
            }
        }
    }

    private boolean getInput(String input) {
        try {
            this.view = this.view.getInput(input);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
