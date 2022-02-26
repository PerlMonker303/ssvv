package service;

import domain.*;
import repository.*;
import validation.AssignmentValidator;
import validation.StudentValidator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Service {
    private CRUDRepository<String, Student> studentRepository;
    private CRUDRepository<String, Assignment> assignmentRepository;
    private CRUDRepository<Pair<String, String>, Grade> gradeRepository;

    public Service(CRUDRepository<String, Student> studentRepository,
                   CRUDRepository<String, Assignment> assignmentRepository,
                   CRUDRepository<Pair<String, String>, Grade> gradeRepository) {
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
        this.gradeRepository = gradeRepository;
    }

    public Iterable<Student> findAllStudents() { return studentRepository.findAll(); }

    public Iterable<Assignment> findAllAssignments() { return assignmentRepository.findAll(); }

    public Iterable<Grade> findAllGrades() { return gradeRepository.findAll(); }

    public int saveStudent(String id, String name, int group) {
        Student student = new Student(id, name, group);
        Student result = studentRepository.save(student);

        if (result == null) {
            // create file for student ?

            return 1;
        }
        return 0;
    }

    public int saveAssignment(String id, String description, int deadline, int startline) {
        Assignment assignment = new Assignment(id, description, deadline, startline);
        Assignment result = assignmentRepository.save(assignment);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveGrade(String idStudent, String idAssignment, double valGrade, int predata, String feedback) {
        if (studentRepository.findOne(idStudent) == null || assignmentRepository.findOne(idAssignment) == null) {
            return -1;
        }
        else {
            int deadline = assignmentRepository.findOne(idAssignment).getDeadline();

            if (predata - deadline > 2) {
                valGrade =  1;
            } else if(predata > deadline){
                valGrade =  valGrade - 2.5 * (predata - deadline);
            }
            Grade grade = new Grade(new Pair(idStudent, idAssignment), valGrade, predata, feedback);
            Grade result = gradeRepository.save(grade);

            if (result == null) {
                return 1;
            }
            return 0;
        }
    }

    public int deleteStudent(String id) {
        Student result = studentRepository.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int deleteAssignment(String id) {
        Assignment result = assignmentRepository.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateStudent(String id, String newName, int newGroup) {
        Student studentNou = new Student(id, newName, newGroup);
        Student result = studentRepository.update(studentNou);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateAssignment(String id, String newDescription, int newDeadline, int newStartline) {
        Assignment newAssignment = new Assignment(id, newDescription, newDeadline, newStartline);
        Assignment result = assignmentRepository.update(newAssignment);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int extendDeadline(String id, int noWeeks) {
        Assignment assignment = assignmentRepository.findOne(id);

        if (assignment != null) {
            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeek >= 39) {
                currentWeek = currentWeek - 39;
            } else {
                currentWeek = currentWeek + 12;
            }

            if (currentWeek <= assignment.getDeadline()) {
                int deadlineNou = assignment.getDeadline() + noWeeks;
                return updateAssignment(assignment.getID(), assignment.getDescription(), deadlineNou, assignment.getStartline());
            }
        }
        return 0;
    }

    public void createStudentFile(String idStudent, String idAssignment) {
        Grade grade = gradeRepository.findOne(new Pair(idStudent, idAssignment));

        try {
            this.createFile(grade);
        }catch(IllegalStateException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void createFile(Grade gradeObj) {
        String               idStudent = gradeObj.getID().getObject1();
        Student student = studentRepository.findOne(idStudent);

        if(student == null){
            throw new IllegalStateException("Student not found in text file");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(student.getName() + ".txt", false))) {
            gradeRepository.findAll().forEach(nota -> {
                if (nota.getID().getObject1().equals(idStudent)) {
                    try {
                        bw.write("Tema: " + nota.getID().getObject2() + "\n");
                        bw.write("Nota: " + nota.getGrade() + "\n");
                        bw.write("Predata in saptamana: " + nota.getSaptamanaPredare() + "\n");
                        bw.write("Deadline: " + assignmentRepository.findOne(nota.getID().getObject2()).getDeadline() + "\n");
                        bw.write("Feedback: " + nota.getFeedback() + "\n\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
