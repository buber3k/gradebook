import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {

    private List<Student> students;
    private String[] columnNames = {"Imię", "Nazwisko", "Numer indeksu", "Ilość punktów", "Stan studenta", "Rok urodzenia"};

    public StudentTableModel() {
        students = new ArrayList<>();
    }

    public StudentTableModel(List<Student> students) {
        this.students = students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return student.getImie();
            case 1:
                return student.getNazwisko();
            case 2:
                return student.getNumerIndeksu();
            case 3:
                return student.getIloscPunktow();
            case 4:
                return student.getStanStudenta();
            case 5:
                return student.getRokUrodzenia();
            default:
                return null;
        }
    }

    public Student getStudent(int rowIndex) {
        return students.get(rowIndex);
    }

    public void removeStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
            fireTableRowsDeleted(index, index);
        } else {
            System.out.println("Nieprawidłowy indeks: " + index);
        }
    }

    public void addStudent(Student student) {
        students.add(student);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }

    // Uwzględnij sortowanie studentów w klasie `StudentTableModel`
    public void sortStudents() {
        students.sort((s1, s2) -> s1.getNazwisko().compareToIgnoreCase(s2.getNazwisko()));
        fireTableDataChanged();
    }
}
