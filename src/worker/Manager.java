package worker;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private String status;
    private Integer identifier;
    private String name;
    private String salary;
    private String department;
    private List<Employee> listEmployee = new ArrayList<>();

    public Manager(Integer identifier, String name, String salary, String department) {
        this.status = "Manager";
        this.identifier = identifier;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public void addEmployee(Employee employee)
    {
        listEmployee.add(employee);
    }

    @Override
    public String toString() {
        StringBuilder  builder = new StringBuilder(String.format("%s\n%s,%s,%s,%s",department, status, identifier, name, salary));
        for(Employee employee: listEmployee)
        {
            builder.append("\n"+employee.toString());
        }

        return builder.toString();
    }

    public List<Employee> getListEmployee() {
        return listEmployee;
    }

    public String getStatus() {
        return status;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }
}
