
package StudentMangementSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private final ArrayList<Student> students = new ArrayList<>();
    private final String fileName = "students.dat";

    public StudentManager() {
        loadFromFile();
    }

    public boolean addStudent(Student student) {
        if (searchById(student.getStudentId()) != null) {
            System.out.println("Student ID already exists.");
            return false;
        }

        students.add(student);
        saveToFile();
        System.out.println("Student added successfully.");
        return true;
    }

    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.printf("%-10s %-20s %-8s %-12s %-30s%n",
                "ID", "Name", "Age", "Average", "Subject Marks");
        System.out.println(
                "--------------------------------------------------------------------------------");

        for (Student student : students) {
            System.out.println(student);
        }
    }

    public Student searchById(int id) {
        for (Student student : students) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        return null;
    }

    public List<Student> searchByName(String name) {
        List<Student> matches = new ArrayList<>();

        for (Student student : students) {
            if (student.getName().toLowerCase()
                    .contains(name.toLowerCase())) {
                matches.add(student);
            }
        }
        return matches;
    }

    public void updateStudent(Student student) {
        if (student != null) {
            saveToFile();
            System.out.println("Student record updated.");
        }
    }

    public boolean deleteStudent(int id) {
        Student student = searchById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return false;
        }

        students.remove(student);
        saveToFile();
        System.out.println("Student deleted successfully.");
        return true;
    }

    public double calculateAverage() {
        if (students.isEmpty()) {
            return 0;
        }

        double total = 0;
        for (Student student : students) {
            total += student.getAverageMarks();
        }

        return total / students.size();
    }

    
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving records: "
                    + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(fileName);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file))) {
            Object data = in.readObject();

            if (data instanceof ArrayList<?>) {
                students.clear();
                students.addAll((ArrayList<Student>) data);
                System.out.println("Student records loaded.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load saved records: "
                    + e.getMessage());
        }
    }
}
