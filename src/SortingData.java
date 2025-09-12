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

        managerList.values().stream()
            .map(this::parseManager)
            .filter(Objects::nonNull)
            .forEach(manager ->
            {
                managerMap.put(manager.getIdentifier().toString(), manager);
                workerInDepartment.add(manager);
            });

        for (String employeeLine : employeeList) {
            Employee employee = parseEmployee(employeeLine);
            String targetIdentifier = employee.getIdentifierM().toString();
            Manager manager = managerMap.get(targetIdentifier);
            if (manager != null) {
                manager.addEmployee(employee);
            } else {
                FilesUtils.addEmployeeWithoutDepartment(employeeLine);
            }
        }

        if (valueArgs.isEmpty()) {
            Comparator<Employee> comparator = createComparator();
            workerInDepartment.forEach(manager -> manager.getListEmployee().sort(comparator));
        }
    }

    private Manager parseManager(String managerLine) {
        try {
            String[] managerData = managerLine.split(",", 5);
            return new Manager(Integer.parseInt(managerData[1].trim()),
                managerData[2].trim(),
                managerData[3].trim(),
                managerData[4].trim());
        } catch (Exception ignored) {

        }
        return null;
    }

    private Employee parseEmployee(String employeeLine) {
        String[] employeeData = employeeLine.split(",", 5);
        return new Employee(Integer.parseInt(employeeData[1].trim()),
            employeeData[2].trim(),
            employeeData[3].trim(),
            Integer.parseInt(employeeData[4].trim()));
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
