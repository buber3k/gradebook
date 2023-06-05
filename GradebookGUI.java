import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GradebookGUI extends JFrame {

    private ClassContainer classContainer;
    private JComboBox<String> groupComboBox;
    private JTextField studentNameTextField;
    private JTable studentTable;
    private StudentTableModel studentTableModel;

    public GradebookGUI(ClassContainer classContainer) {
        this.classContainer = classContainer;

        setTitle("Gradebook");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Górny panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        add(topPanel, BorderLayout.NORTH);

        // Dodaj przyciski do panelu górnego
        groupComboBox = new JComboBox<>(classContainer.getGroupNames().toArray(new String[0]));
        topPanel.add(groupComboBox);
        studentNameTextField = new JTextField(20);
        topPanel.add(studentNameTextField);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);
        JButton removeButton = new JButton("Remove");
        topPanel.add(removeButton);
        JButton addButton = new JButton("Add");
        topPanel.add(addButton);
        JButton sortButton = new JButton("Sort");
        topPanel.add(sortButton);
        JButton addGroupButton = new JButton("Add Group");
        topPanel.add(addGroupButton);
        JButton addPointsButton = new JButton("Add Points");
        topPanel.add(addPointsButton);
        JButton removePointsButton = new JButton("Remove Points");
        topPanel.add(removePointsButton);


        // Tabela ze studentami
        studentTableModel = new StudentTableModel();
        studentTable = new JTable(studentTableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
        // Listener do wyboru grupy
        groupComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = (String) groupComboBox.getSelectedItem();
                Class group = classContainer.getGroup(groupName);
                studentTableModel.setStudents(group.getStudents());
            }
        });

        // Listener do wyszukiwania studentów
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = studentNameTextField.getText();
                String groupName = (String) groupComboBox.getSelectedItem();
                Class group = classContainer.getGroup(groupName);
                List<Student> searchedStudents = group.searchPartial(studentName);
                studentTableModel.setStudents(searchedStudents);
            }
        });

        // Listener do usuwania studentów
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelIndex = studentTable.convertRowIndexToModel(selectedRow);
                    studentTableModel.removeStudent(modelIndex);
                    System.out.println("Usunięto studenta o indeksie: " + modelIndex);
                } else {
                    System.out.println("Nie wybrano żadnego studenta");
                }
            }
        });

        // Listener do dodawania studentów
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField imieField = new JTextField(20);
                JTextField nazwiskoField = new JTextField(20);
                JTextField numerIndeksuField = new JTextField(20);
                JTextField iloscPunktowField = new JTextField(20);
                JTextField rokUrodzeniaField = new JTextField(20);
                JComboBox<StudentCondition> stanStudentaField = new JComboBox<>(StudentCondition.values());

                JPanel addStudentPanel = new JPanel();
                addStudentPanel.setLayout(new GridLayout(0, 2));
                addStudentPanel.add(new JLabel("Imię:"));
                addStudentPanel.add(imieField);
                addStudentPanel.add(new JLabel("Nazwisko:"));
                addStudentPanel.add(nazwiskoField);
                addStudentPanel.add(new JLabel("Numer indeksu:"));
                addStudentPanel.add(numerIndeksuField);
                addStudentPanel.add(new JLabel("Ilość punktów:"));
                addStudentPanel.add(iloscPunktowField);
                addStudentPanel.add(new JLabel("Stan studenta:"));
                addStudentPanel.add(stanStudentaField);
                addStudentPanel.add(new JLabel("Rok urodzenia:"));
                addStudentPanel.add(rokUrodzeniaField);

                int result = JOptionPane.showConfirmDialog(null, addStudentPanel, "Dodaj studenta", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String imie = imieField.getText();
                    String nazwisko = nazwiskoField.getText();
                    String numerIndeksu = numerIndeksuField.getText();
                    double iloscPunktow = Double.parseDouble(iloscPunktowField.getText());
                    StudentCondition stanStudenta = (StudentCondition) stanStudentaField.getSelectedItem();
                    int rokUrodzenia = Integer.parseInt(rokUrodzeniaField.getText());

                    Student newStudent = new Student(imie, nazwisko, stanStudenta, rokUrodzenia, iloscPunktow, numerIndeksu);
                    studentTableModel.addStudent(newStudent);
                }
            }
        });

        // Listener do sortowania studentów
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = (String) groupComboBox.getSelectedItem();
                Class group = classContainer.getGroup(groupName);
                studentTableModel.sortStudents();
                studentTableModel.setStudents(group.getStudents());
            }
        });

        // Listener do dodawania grup
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGroupDialog addGroupDialog = new AddGroupDialog(GradebookGUI.this);
                addGroupDialog.setVisible(true);
                String newGroupName = addGroupDialog.getNewGroupName();
                int newGroupCapacity = addGroupDialog.getNewGroupCapacity();
                if (newGroupName != null && newGroupCapacity > 0) {
                    classContainer.addClass(newGroupName, newGroupCapacity);
                    groupComboBox.addItem(newGroupName);
                    groupComboBox.setSelectedItem(newGroupName);
                }
            }
        });

        //Listenery dodawanie/usuwanie punktów

        addPointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String pointsStr = JOptionPane.showInputDialog(GradebookGUI.this, "Enter the number of points to add:", "Add Points", JOptionPane.PLAIN_MESSAGE);
                    if (pointsStr != null) {
                        try {
                            int pointsToAdd = Integer.parseInt(pointsStr);
                            int modelIndex = studentTable.convertRowIndexToModel(selectedRow);
                            Student selectedStudent = studentTableModel.getStudent(modelIndex);
                            String groupName = (String) groupComboBox.getSelectedItem();
                            Class group = classContainer.getGroup(groupName);
                            group.addPoints(selectedStudent, pointsToAdd);
                            studentTableModel.fireTableRowsUpdated(modelIndex, modelIndex);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(GradebookGUI.this, "Invalid points value. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(GradebookGUI.this, "Please select a student first.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removePointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String pointsStr = JOptionPane.showInputDialog(GradebookGUI.this, "Enter the number of points to remove:", "Remove Points", JOptionPane.PLAIN_MESSAGE);
                    if (pointsStr != null) {
                        try {
                            int pointsToRemove = Integer.parseInt(pointsStr);
                            int modelIndex = studentTable.convertRowIndexToModel(selectedRow);
                            Student selectedStudent = studentTableModel.getStudent(modelIndex);
                            String groupName = (String) groupComboBox.getSelectedItem();
                            Class group = classContainer.getGroup(groupName);
                            group.removePoints(selectedStudent, pointsToRemove);
                            studentTableModel.fireTableRowsUpdated(modelIndex, modelIndex);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(GradebookGUI.this, "Invalid points value. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(GradebookGUI.this, "Please select a student first.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Ustawienie domyślnej grupy
        groupComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClassContainer classContainer = new ClassContainer();
                GradebookGUI gradebookGUI = new GradebookGUI(classContainer);
                gradebookGUI.setVisible(true);
            }
        });
    }
}