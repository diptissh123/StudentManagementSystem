package StudentMangementSystem;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int studentId;
    private String name;
    private int age;
    private final Map<String, Double> subjectMarks;

    public Student(int studentId, String name, int age) {
        if (studentId <= 0) {
            throw new IllegalArgumentException(
                    "Student ID must be positive.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Name cannot be empty.");
        }
        validateAge(age);

        this.studentId = studentId;
        this.name = name.trim();
        this.age = age;
        this.subjectMarks = new LinkedHashMap<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Map<String, Double> getSubjectMarks() {
        return new LinkedHashMap<>(subjectMarks);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        validateAge(age);
        this.age = age;
    }

    public void addSubjectMark(String subject, double marks) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Subject cannot be empty.");
        }
        if (!Double.isFinite(marks) || marks < 0 || marks > 100) {
            throw new IllegalArgumentException(
                    "Marks must be between 0 and 100.");
        }

        subjectMarks.put(subject.trim(), marks);
    }

    public double getTotalMarks() {
        double total = 0;
        for (double marks : subjectMarks.values()) {
            total += marks;
        }
        return total;
    }

    public double getAverageMarks() {
        if (subjectMarks.isEmpty()) {
            return 0;
        }
        return getTotalMarks() / subjectMarks.size();
    }

    private void validateAge(int age) {
        if (age < 5 || age > 100) {
            throw new IllegalArgumentException(
                    "Age must be between 5 and 100.");
        }
    }
    
    public String getResultStatus() {
        if (subjectMarks.isEmpty()) {
            return "No Marks Available";
        }

        double average = getAverageMarks();

        if (average >= 75) {
            return "Distinction";
        } else if (average >= 60) {
            return "First Class";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail - Needs Improvement";
        }
    }

    public void displayStudentRecord() {
        System.out.println("\nStudent ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("\nSubject Marks:");

        if (subjectMarks.isEmpty()) {
            System.out.println("No marks added.");
        } else {
            for (Map.Entry<String, Double> entry
                    : subjectMarks.entrySet()) {
                System.out.printf("%-20s %.2f/100%n",
                        entry.getKey(), entry.getValue());
            }

            System.out.printf("Total: %.2f / %.2f%n",
                    getTotalMarks(),
                    subjectMarks.size() * 100.0);
            System.out.printf("Average: %.2f%%%n",
                    getAverageMarks());
            
            System.out.println("Result Status: " + getResultStatus());
        }
    }


    
    @Override
    public String toString() {
        return String.format(
                "%-10d %-20s %-8d %-12.2f %-30s",
                studentId, name, age, getAverageMarks(),
                subjectMarks);
    }
}
