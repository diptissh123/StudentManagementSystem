
package StudentMangementSystem;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Calculate Class Average");
            System.out.println("7. Save and Exit");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    manager.displayAll();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    System.out.printf("Class average: %.2f%%%n",
                            manager.calculateAverage());
                    break;
                case 7:
                    manager.saveToFile();
                    System.out.println("Records saved. Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    private static void addStudent() {
        try {
            System.out.print("Enter student ID: ");
            int id = readInt();

            System.out.print("Enter name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter age: ");
            int age = readInt();

            Student student = new Student(id, name, age);

            System.out.print("How many subjects? ");
            int count = readInt();

            if (count <= 0) {
                System.out.println(
                        "Enter at least one subject.");
                return;
            }

            for (int i = 1; i <= count; i++) {
                System.out.print("Enter subject " + i + " name: ");
                String subject = sc.nextLine().trim();

                System.out.print("Enter marks out of 100: ");
                double marks = readDouble();

                student.addSubjectMark(subject, marks);
            }

            if (manager.addStudent(student)) {
                student.displayStudentRecord();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid data: " + e.getMessage());
        }
    }

    private static void searchStudent() {
        System.out.println("1. Search by ID");
        System.out.println("2. Search by name");
        System.out.print("Enter choice: ");

        int choice = readInt();

        if (choice == 1) {
            System.out.print("Enter student ID: ");
            Student student = manager.searchById(readInt());

            if (student != null) {
                student.displayStudentRecord();
            } else {
                System.out.println("Student not found.");
            }

        } else if (choice == 2) {
            System.out.print("Enter student name: ");
            List<Student> matches =
                    manager.searchByName(sc.nextLine().trim());

            if (matches.isEmpty()) {
                System.out.println("Student not found.");
            } else {
                for (Student student : matches) {
                    student.displayStudentRecord();
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        Student student = manager.searchById(readInt());

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        boolean editing = true;

        while (editing) {
            student.displayStudentRecord();

            System.out.println("\n1. Update name");
            System.out.println("2. Update age");
            System.out.println("3. Add or update subject marks");
            System.out.println("4. Finish update");
            System.out.print("Enter choice: ");

            int choice = readInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter new name: ");
                        student.setName(sc.nextLine());
                        manager.updateStudent(student);
                        break;

                    case 2:
                        System.out.print("Enter new age: ");
                        student.setAge(readInt());
                        manager.updateStudent(student);
                        break;

                    case 3:
                        System.out.print("Enter subject name: ");
                        String subject = sc.nextLine().trim();

                        System.out.print("Enter new marks: ");
                        double marks = readDouble();

                        student.addSubjectMark(subject, marks);
                        manager.updateStudent(student);
                        break;

                    case 4:
                        editing = false;
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid data: " + e.getMessage());
            }
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        int id = readInt();

        Student student = manager.searchById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.displayStudentRecord();

        System.out.print("Confirm deletion? (yes/no): ");
        String confirmation = sc.nextLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {
            manager.deleteStudent(id);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static int readInt() {
        while (true) {
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            String input = sc.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (Double.isFinite(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Ask again below.
            }

            System.out.print("Enter a valid number: ");
        }
    }
}
