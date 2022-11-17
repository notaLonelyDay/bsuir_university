package main.java.by.client.presentation.view;

import main.java.by.client.entity.Student;
import main.java.by.client.entity.user.User;
import main.java.by.client.presentation.viewModel.GetModelView;
import main.java.by.client.service.StudentClientService;

import java.util.List;

public class GetView extends PresentationView {
    public GetView(StudentClientService studentService, User user, int id) {
        super(studentService, user);
        this.model = new GetModelView(studentService, id);
    }

    @Override
    public void show() {
        List<Student> items = this.model.getItems();
        if (items.isEmpty()) {
            System.out.println("Element not found");
        } else {
            System.out.println(items.get(0));
        }
    }

    @Override
    public PresentationView getInput(String input) {
        return new GetSelectView(this.studentService, this.currentUser);
    }
}
