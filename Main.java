import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Tworzenie obiektów studentów
        Student student1 = new Student("Jan", "Kowalski", StudentCondition.ODRABIAJACY, 2000, 85.0, "10001");
        Student student2 = new Student("Anna", "Nowak", StudentCondition.NIEOBECNY, 2001, 90.0, "10002");
        Student student3 = new Student("Michał", "Bąk", StudentCondition.CHORY, 1999, 78.0, "10003");
        Student student4 = new Student("Karolina", "Wiśniewska", StudentCondition.ODRABIAJACY, 2001, 92.0, "10004");

        // Tworzenie obiektu ClassContainer
        ClassContainer classContainer = new ClassContainer();

        // Dodawanie grup do kontenera
        classContainer.addClass("Grupa1", 3);
        classContainer.addClass("Grupa2", 2);

        // Dodawanie studentów do grup
        classContainer.addStudentToClass("Grupa1", student1);
        classContainer.addStudentToClass("Grupa1", student2);
        classContainer.addStudentToClass("Grupa2", student3);
        classContainer.addStudentToClass("Grupa2", student4);

        // Próba dodania studenta do nieistniejącej grupy
        classContainer.addStudentToClass("Grupa3", student1);

        System.out.println("----------------------");

        // Wypisanie informacji o grupach
        classContainer.summary();

        // Wyszukiwanie pustych grup
        List<Class> emptyGroups = classContainer.findEmpty();
        System.out.println("Puste grupy:");
        emptyGroups.forEach(group -> System.out.println(group.getNazwaGrupy()));

        System.out.println("----------------------");

        // Zmiana stanu studenta
        student1.setStanStudenta(StudentCondition.CHORY);

        // Usuwanie studenta z grupy
      //  classContainer.removeStudentFromClass("Grupa1", "10001");

        // Ponowne wypisanie informacji o grupach
        classContainer.summary();

        System.out.println("----------------------");


        // Wyszukiwanie studentów po nazwisku
        Class group1 = classContainer.getGroup("Grupa1");
        Student searchedStudent = group1.search("Nowak");
        if (searchedStudent != null) {
            System.out.println("Znaleziono studenta:");
            searchedStudent.print();
        } else {
            System.out.println("Nie znaleziono studenta o podanym nazwisku.");
        }

        System.out.println("----------------------");

        // Wyszukiwanie studentów po części nazwiska
        List<Student> partialSearchResults = group1.searchPartial("no");
        System.out.println("Wyniki wyszukiwania częściowego:");
        partialSearchResults.forEach(Student::print);

        System.out.println("----------------------");

        // Sortowanie studentów w grupie po nazwisku
        List<Student> sortedByName = group1.sortByName();
        System.out.println("Studenci posortowani alfabetycznie:");
        sortedByName.forEach(Student::print);

        System.out.println("----------------------");

        // Sortowanie studentów w grupie po ilości punktów
        List<Student> sortedByPoints = group1.sortByPoints();
        System.out.println("Studenci posortowani według ilości punktów:");
        sortedByPoints.forEach(Student::print);

        System.out.println("----------------------");

        // Dodawanie punktów studentowi
        group1.addPoints(student1, 10);

        // Usuwanie punktów studentowi
        group1.removePoints(student2, 5);

        //liczenie studentów ze stanem
        long count = group1.countByCondition(StudentCondition.NIEOBECNY);
        System.out.println("Liczba studentów nieobecnych: " + count);

        System.out.println("----------------------");

        //zmiana stanem
        group1.changeCondition(student1, StudentCondition.NIEOBECNY);


        // Wyszukiwanie studenta z największą ilością punktów
        Student maxStudent = group1.max();
        System.out.println("Student z największą ilością punktów: " + maxStudent.getImie() + " " + maxStudent.getNazwisko());

        System.out.println("----------------------");

        // Uruchomienie GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GradebookGUI gradebookGUI = new GradebookGUI(classContainer);
                gradebookGUI.setVisible(true);
            }
        });
    }

}
