package main.java.by.server.dao.impl;

import main.java.by.client.entity.Student;
import main.java.by.client.entity.user.User;
import main.java.by.server.dao.StudentServerDAO;

import java.beans.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StudentServerDAOImpl implements StudentServerDAO {

    private static final String STUDENTS_XML = "src/main/resources/students.xml";
    private static final String USERS_XML = "src/main/resources/users.xml";
    private final ReentrantReadWriteLock studentsLock = new ReentrantReadWriteLock(true);
    private final ReentrantReadWriteLock usersLock = new ReentrantReadWriteLock(true);

    @Override
    public List<Student> getAll() {
        ArrayList<Student> students = new ArrayList<>();
        Student student;
        this.studentsLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentServerDAOImpl.STUDENTS_XML)))) {
            do {
                student = (Student) decoder.readObject();
                students.add(student);
            } while (student != null);
        } catch (ArrayIndexOutOfBoundsException e) {
            // End of file.
        } catch (FileNotFoundException e) {
            System.out.printf("Error trying read XML: %s%n", e.getMessage());
        } finally {
            this.studentsLock.readLock().unlock();
        }


        return students;
    }

    @Override
    public Student get(int id) {
        Student student;
        this.studentsLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentServerDAOImpl.STUDENTS_XML)))) {

            do {
                student = (Student) decoder.readObject();
                if (student.getId() == id) {
                    return student;
                }

            } while (student != null);
        } catch (ArrayIndexOutOfBoundsException e) {
            // End of file.
        } catch (FileNotFoundException e) {
            System.out.printf("Error trying read XML: %s%n", e.getMessage());
        } finally {
            this.studentsLock.readLock().unlock();
        }


        return null;
    }

    @Override
    public void rewriteStudents(List<Student> students) throws FileNotFoundException {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(StudentServerDAOImpl.STUDENTS_XML)))) {

            encoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });

            encoder.setPersistenceDelegate(LocalDateTime.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDateTime, Encoder encdr) {
                            return new Expression(localDateTime,
                                    LocalDateTime.class,
                                    "parse",
                                    new Object[]{localDateTime.toString()});
                        }
                    });

            try {
                this.studentsLock.writeLock().lock();
                for (Student item : students) {
                    encoder.writeObject(item);
                }

            } finally {
                this.studentsLock.writeLock().unlock();
            }

        } catch (ArrayIndexOutOfBoundsException ignored) {
            // End of file.
        }
    }

    @Override
    public User userExists(User user) {
        User readUser;
        this.usersLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentServerDAOImpl.USERS_XML)))) {

            do {
                readUser = (User) decoder.readObject();
                if (readUser.getLogin().equals(user.getLogin())) {
                    return readUser;
                }

            } while (readUser != null);

        } catch (ArrayIndexOutOfBoundsException | FileNotFoundException ignored) {
            // End of file.
        } finally {
            this.usersLock.readLock().unlock();
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        User user;
        this.usersLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentServerDAOImpl.USERS_XML)))) {
            do {
                user = (User) decoder.readObject();
                users.add(user);
            } while (user != null);
        } catch (ArrayIndexOutOfBoundsException e) {
            // End of file.
        } catch (FileNotFoundException e) {
            System.out.printf("Error trying read XML: %s%n", e.getMessage());
        } finally {
            this.usersLock.readLock().unlock();
        }

        return users;
    }

    @Override
    public void rewriteUsers(List<User> users) throws FileNotFoundException {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(StudentServerDAOImpl.USERS_XML)))) {

            try {
                this.studentsLock.writeLock().lock();
                for (User item : users) {
                    encoder.writeObject(item);
                }

            } finally {
                this.studentsLock.writeLock().unlock();
            }

        } catch (ArrayIndexOutOfBoundsException ignored) {
            // End of file.
        }
    }
}
