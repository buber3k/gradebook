import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGroupDialog extends JDialog {

    private JTextField groupNameTextField;
    private JTextField groupCapacityTextField;
    private String newGroupName;
    private int newGroupCapacity;

    public AddGroupDialog(JFrame owner) {
        super(owner, "Add New Group", true);

        setSize(400, 200);
        setLocationRelativeTo(owner);

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        add(inputPanel, BorderLayout.CENTER);

        JLabel groupNameLabel = new JLabel("Group Name:");
        inputPanel.add(groupNameLabel);

        groupNameTextField = new JTextField();
        inputPanel.add(groupNameTextField);

        JLabel groupCapacityLabel = new JLabel("Group Capacity:");
        inputPanel.add(groupCapacityLabel);

        groupCapacityTextField = new JTextField();
        inputPanel.add(groupCapacityTextField);

        JPanel buttonsPanel = new JPanel();
        add(buttonsPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add");
        buttonsPanel.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        buttonsPanel.add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGroupName = groupNameTextField.getText().trim();
                try {
                    newGroupCapacity = Integer.parseInt(groupCapacityTextField.getText().trim());
                    if (!newGroupName.isEmpty() && newGroupCapacity > 0) {
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AddGroupDialog.this, "Group name cannot be empty and capacity must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddGroupDialog.this, "Invalid capacity value. Please enter a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGroupName = null;
                newGroupCapacity = -1;
                dispose();
            }
        });
    }

    public String getNewGroupName() {
        return newGroupName;
    }

    public int getNewGroupCapacity() {
        return newGroupCapacity;
    }
}
