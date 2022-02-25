package service;

import domain.*;
import repository.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Service {
    private StudentXMLRepository studentXMLRepository;
    private AssignmentXMLRepository assignmentXMLRepository;
    private GradeXMLRepository gradeXMLRepository;

    public Service(StudentXMLRepository studentXMLRepository, AssignmentXMLRepository assignmentXMLRepository, GradeXMLRepository gradeXMLRepository) {
        this.studentXMLRepository = studentXMLRepository;
        this.assignmentXMLRepository = assignmentXMLRepository;
        this.gradeXMLRepository = gradeXMLRepository;
    }

    public Iterable<Student> findAllStudents() { return studentXMLRepository.findAll(); }

    public Iterable<Assignment> findAllAssignments() { return assignmentXMLRepository.findAll(); }

    public Iterable<Grade> findAllGrades() { return gradeXMLRepository.findAll(); }

    public int saveStudent(String id, String name, int group) {
        Student student = new Student(id, name, group);
        Student result = studentXMLRepository.save(student);

        if (result == null) {
            // create file for student ?

            return 1;
        }
        return 0;
    }

    public int saveAssignment(String id, String description, int deadline, int startline) {
        Assignment assignment = new Assignment(id, description, deadline, startline);
        Assignment result = assignmentXMLRepository.save(assignment);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveGrade(String idStudent, String idAssignment, double valGrade, int predata, String feedback) {
        if (studentXMLRepository.findOne(idStudent) == null || assignmentXMLRepository.findOne(idAssignment) == null) {
            return -1;
        }
        else {
            int deadline = assignmentXMLRepository.findOne(idAssignment).getDeadline();

            if (predata - deadline > 2) {
                valGrade =  1;
            } else if(predata > deadline){
                valGrade =  valGrade - 2.5 * (predata - deadline);
            }
            Grade grade = new Grade(new Pair(idStudent, idAssignment), valGrade, predata, feedback);
            Grade result = gradeXMLRepository.save(grade);

            if (result == null) {
                return 1;
            }
            return 0;
        }
    }

    public int deleteStudent(String id) {
        Student result = studentXMLRepository.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int deleteAssignment(String id) {
        Assignment result = assignmentXMLRepository.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateStudent(String id, String newName, int newGroup) {
        Student studentNou = new Student(id, newName, newGroup);
        Student result = studentXMLRepository.update(studentNou);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateAssignment(String id, String newDescription, int newDeadline, int newStartline) {
        Assignment newAssignment = new Assignment(id, newDescription, newDeadline, newStartline);
        Assignment result = assignmentXMLRepository.update(newAssignment);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int extendDeadline(String id, int noWeeks) {
        Assignment assignment = assignmentXMLRepository.findOne(id);

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
        Grade grade = gradeXMLRepository.findOne(new Pair(idStudent, idAssignment));

        try {
            gradeXMLRepository.createFile(grade);
        }catch(IllegalStateException ex){
            System.out.println(ex.getMessage());
        }
    }
}
