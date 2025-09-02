import enums.OrderType;
import enums.SortType;
import worker.Employee;
import worker.Manager;

import java.util.*;

public class SortingData {
    private final List<String> employeeList;
    private final Map<String, String> managerList;
    private final List<String> valueArgs;
    private final List<Manager> workerInDepartment = new ArrayList<>();
    private final Map<String, Manager> managerMap = new HashMap<>();

    public SortingData(Map<String, String> managerList, List<String> employeeList, List<String> valueArgs) {
        this.employeeList = employeeList;
        this.managerList = managerList;
        this.valueArgs = valueArgs;
    }

    public void sortingWorkerToDepartment() {

        getCreatManagerToMap();

        sortingEmployeeToManager();

        sortEmployeeIfNeeded();
    }

    private void getCreatManagerToMap() {
        for (String managerLine : managerList.values()) {
            String[] managerData = managerLine.split(",", 5);
            String targetIdentifier = managerData[1].trim();
            Manager manager = new Manager(Integer.parseInt(targetIdentifier),
                managerData[2].trim(),
                managerData[3].trim(),
                managerData[4].trim());
            managerMap.put(targetIdentifier, manager);
            workerInDepartment.add(manager);
        }
    }

    private void sortingEmployeeToManager() {
        for (String employeeLine : employeeList) {
            String[] employeeData = employeeLine.split(",", 5);
            String targetIdentifier = employeeData[4].trim();
            Manager manager = managerMap.get(targetIdentifier);
            if (manager != null) {
                Employee employee = creatEmployee(employeeData);
                manager.addEmployee(employee);
            } else {
                FilesUtils.addEmployeeWithoutDepartment(employeeLine);
            }
        }
    }

    private Employee creatEmployee(String[] employeeData) {
        return new Employee(Integer.parseInt(employeeData[1].trim()),
            employeeData[2].trim(),
            employeeData[3].trim(),
            Integer.parseInt(employeeData[4].trim()));
    }

    private void sortEmployeeIfNeeded() {
        if (valueArgs.isEmpty()) return;
        Comparator<Employee> comparator = createComparator();
        for (Manager manager : workerInDepartment) {
            if (!manager.getListEmployee().isEmpty()) {
                manager.getListEmployee().sort(comparator);
            }
        }
    }

    private Comparator<Employee> createComparator() {
        if (valueArgs.contains(SortType.NAME.name().toLowerCase())) {
            boolean containsDesc = valueArgs.contains(OrderType.DESC.name().toLowerCase());
            return containsDesc ? Comparator.comparing(Employee::getName).reversed() :
                Comparator.comparing(Employee::getName);
        } else if (valueArgs.contains(SortType.SALARY.name().toLowerCase())) {
            boolean containsDesc = valueArgs.contains(OrderType.DESC.name().toLowerCase());
            return containsDesc ? Comparator.comparing(Employee::getSalary).reversed() :
                Comparator.comparing(Employee::getSalary);
        }
        return (e1, e2) -> 0;
    }

    public List<Manager> getWorkerInDepartment() {
        return workerInDepartment;
    }

}
