import worker.Employee;
import worker.Manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortingData {
    private final List<String> employeeList;
    private final Map<String, String> managerList;
    private final List<String> valueArgs;
    private final List<Manager> workerInDepartment = new ArrayList<>();
    private final List<Integer> controlList = new ArrayList<>();

    public SortingData(Map<String, String> managerList, List<String> employeeList, List<String> valueArgs) {
        this.employeeList = employeeList;
        this.managerList = managerList;
        this.valueArgs = valueArgs;
    }

    public void sortingWorkerToDepartment() {
        for (String string : managerList.values()) {
            String[] dataForManager = string.split(",");
            Manager manager = new Manager(Integer.parseInt(dataForManager[1].trim()), dataForManager[2].trim(), dataForManager[3].trim(), dataForManager[4].trim());
            for (int i = 0; i < employeeList.size(); i++) {
                String[] dataForEmployee = employeeList.get(i).split(",");
                if (dataForManager[1].equals(dataForEmployee[4].trim())) {
                    controlList.add(i);
                    manager.addEmployee(new Employee(Integer.parseInt(dataForEmployee[1].trim()), dataForEmployee[2].trim(), dataForEmployee[3].trim(), Integer.parseInt(dataForEmployee[4].trim())));
                }
            }
            workerInDepartment.add(manager);
            if (!valueArgs.isEmpty()) sortEmployeeInList(manager);
        }
    }
    private void sortEmployeeInList(Manager manager) {
        List<Employee> employees = manager.getListEmployee();
        if (valueArgs.contains(Attributs.NAME)) {
            boolean containsDesc = valueArgs.contains(Attributs.DESС);
            employees.sort(containsDesc ? (s1, s2) -> s2.getName().compareTo(s1.getName()) :
                Comparator.comparing(Employee::getName));
        } else if (valueArgs.contains(Attributs.SALARY)) {
            boolean containsDesc = valueArgs.contains(Attributs.DESС);
            employees.sort(containsDesc ? (s1, s2) -> s2.getSalary().compareTo(s1.getSalary()) :
                Comparator.comparing(Employee::getSalary));
        }
    }

    public List<Manager> getWorkerInDepartment() {
        return workerInDepartment;
    }

    public List<Integer> getControlList() {
        return controlList;
    }
}
