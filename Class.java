import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Class {
    private String nazwaGrupy;
    private List<Student> listaStudentow;
    private int maksymalnaIloscStudentow;

    public Class(String nazwaGrupy, int maksymalnaIloscStudentow) {
        this.nazwaGrupy = nazwaGrupy;
        this.listaStudentow = new ArrayList<>();
        this.maksymalnaIloscStudentow = maksymalnaIloscStudentow;
    }

    public void addStudent(Student student) {
        if (listaStudentow.size() < maksymalnaIloscStudentow) {
            if (listaStudentow.stream().noneMatch(s -> s.getImie().equals(student.getImie()) && s.getNazwisko().equals(student.getNazwisko()))) {
                listaStudentow.add(student);
            } else {
                System.out.println("Student o takim imieniu i nazwisku już istnieje.");
            }
        } else {
            System.err.println("Przekroczono pojemność grupy.");
        }
    }

    public void removeStudent(String studentIndexNumber) {
        listaStudentow.removeIf(student -> student.getNumerIndeksu().equals(studentIndexNumber));
    }

    public int getNumberOfStudents() {
        return listaStudentow.size();
    }

    public String getNazwaGrupy() {
        return nazwaGrupy;
    }

    public List<Student> getStudents() {
        return listaStudentow;
    }


    public int getCapacity() {
        return maksymalnaIloscStudentow;
    }

    public void addPoints(Student student, double points) {
        student.setIloscPunktow(student.getIloscPunktow() + points);
    }

    public void getStudent(Student student) {
        listaStudentow.remove(student);
    }

    public void changeCondition(Student student, StudentCondition newCondition) {
        student.setStanStudenta(newCondition);
    }

    public void removePoints(Student student, double points) {
        student.setIloscPunktow(Math.max(student.getIloscPunktow() - points, 0));
    }

    public Student search(String nazwisko) {
        return listaStudentow.stream()
                .filter(student -> student.getNazwisko().equalsIgnoreCase(nazwisko))
                .findFirst()
                .orElse(null);
    }

    public List<Student> searchPartial(String partial) {
        return listaStudentow.stream()
                .filter(student -> student.getNazwisko().toLowerCase().contains(partial.toLowerCase()) || student.getImie().toLowerCase().contains(partial.toLowerCase()))
                .collect(Collectors.toList());
    }

    public long countByCondition(StudentCondition condition) {
        return listaStudentow.stream()
                .filter(student -> student.getStanStudenta() == condition)
                .count();
    }

    public void summary() {
        listaStudentow.forEach(Student::print);
    }

    public List<Student> sortByName() {
        List<Student> sortedList = new ArrayList<>(listaStudentow);
        Collections.sort(sortedList);
        return sortedList;
    }

    public List<Student> sortByPoints() {
        List<Student> sortedList = new ArrayList<>(listaStudentow);
        sortedList.sort(Comparator.comparingDouble(Student::getIloscPunktow).reversed());
        return sortedList;
    }

    public Student max() {
        return Collections.max(listaStudentow, Comparator.comparingDouble(Student::getIloscPunktow));
    }
}