import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentDialog extends JDialog {
    private JTextField nameField;
    private JTextField surnameField;
    private JButton addButton;
    private JButton cancelButton;
    private Student newStudent;

    public AddStudentDialog(JFrame parent) {
        super(parent, "Add Student", true);
        setSize(300, 200);
        setLayout(new BorderLayout());

        // Panel formularza
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(formPanel, BorderLayout.CENTER);

        // Etykiety i pola tekstowe
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Surname:"));
        surnameField = new JTextField();
        formPanel.add(surnameField);

        // Panel przycisków
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        add(buttonPanel, BorderLayout.SOUTH);

        // Przyciski
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        // ActionListener do przycisków
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String surname = surnameField.getText().trim();

                if (!name.isEmpty() && !surname.isEmpty()) {
                    newStudent = new Student(name, surname, StudentCondition.NIEOBECNY, 0, 0.0, "");

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AddStudentDialog.this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    public Student getNewStudent() {
        return newStudent;
    }
}