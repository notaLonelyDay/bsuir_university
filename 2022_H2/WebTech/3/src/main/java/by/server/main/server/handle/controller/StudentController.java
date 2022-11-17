package main.java.by.server.main.server.handle.controller;

import main.java.by.client.entity.Student;
import main.java.by.client.entity.StudentRequest;
import main.java.by.client.entity.StudentResponse;
import main.java.by.client.entity.request.ResponseType;
import main.java.by.client.entity.user.User;
import main.java.by.server.service.StudentServerService;

import java.util.List;

public class StudentController {
    private final StudentServerService service;

    public StudentController(StudentServerService service) {
        this.service = service;
    }

    public StudentResponse create(StudentRequest request) {
        Student student;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof Student body) {
            student = body;
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        if (this.service.create(student)) {
            response.setResponseType(ResponseType.OK);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse edit(StudentRequest request) {
        Student student;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof Student body) {
            student = body;
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        if (this.service.edit(student)) {
            response.setResponseType(ResponseType.OK);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse getAll() {
        List<Student> students = this.service.getAll();
        StudentResponse response = new StudentResponse();
        if (students == null) {
            response.setResponseType(ResponseType.ERROR);
            response.setBody(null);
        }
        else {
            response.setResponseType(ResponseType.OK);
            response.setBody(students);
        }

        return response;
    }

    public StudentResponse get(StudentRequest request) {
        int id;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof Integer) {
            id = (int) request.getBody();
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        Student studentToSend = this.service.get(id);
        if (studentToSend != null) {
            response.setResponseType(ResponseType.OK);
            response.setBody(studentToSend);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse register(StudentRequest request) {
        User user;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof User body) {
            user = body;
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        User userResult = this.service.register(user);
        if (userResult != null) {
            response.setResponseType(ResponseType.OK);
            response.setBody(userResult);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse login(StudentRequest request) {
        User user;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof User body) {
            user = body;
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        User userResult = this.service.login(user);
        if (userResult != null) {
            response.setResponseType(ResponseType.OK);
            response.setBody(userResult);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse notFound() {
        StudentResponse response = new StudentResponse();
        response.setResponseType(ResponseType.NOTFOUND);
        return response;
    }
}
