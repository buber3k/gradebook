import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassContainer {
    private Map<String, Class> groups;

    public ClassContainer() {
        groups = new HashMap<>();
    }

    public Class getGroup(String groupName) {
        return groups.getOrDefault(groupName, null);
    }

    public List<String> getGroupNames() {
        return groups.keySet().stream().collect(Collectors.toList());
    }

    public void addClass(String groupName, int capacity) {
        groups.put(groupName, new Class(groupName, capacity));
    }

    public void removeClass(String groupName) {
        groups.remove(groupName);
    }

    public List<Class> findEmpty() {
        return groups.values().stream()
                .filter(classGroup -> classGroup.getNumberOfStudents() == 0)
                .collect(Collectors.toList());
    }

    public void summary() {
        for (Map.Entry<String, Class> entry : groups.entrySet()) {
            String groupName = entry.getKey();
            Class classGroup = entry.getValue();
            double percentageFilled = (classGroup.getNumberOfStudents() / (double) classGroup.getCapacity()) * 100;
            System.out.printf("Nazwa grupy: %s, Procentowe zapełnienie: %.2f%%\n", groupName, percentageFilled);
        }
    }

    public void addStudentToClass(String groupName, Student student) {
        Class classGroup = groups.get(groupName);
        if (classGroup != null) {
            classGroup.addStudent(student);
        } else {
            System.out.println("Grupa o podanej nazwie nie istnieje.");
        }
    }

    public boolean removeStudentFromClass(String groupName, String studentId) {
        Class group = groups.get(groupName);
        if (group != null) {
            List<Student> students = group.getStudents();
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getNumerIndeksu().equals(studentId)) {
                    students.remove(i);
                    return true;
                }
            }
        }
        return false;
    }




}
