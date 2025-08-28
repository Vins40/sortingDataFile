package worker;

public class Employee {
    private final String status;
    private final Integer identifier;
    private final String name;
    private final String salary;
    private final Integer identifierM;

    public Employee(Integer identifier, String name, String salary, Integer identifierM) {
        this.status = "Employee";
        this.identifier = identifier;
        this.name = name;
        this.salary = salary;
        this.identifierM = identifierM;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", status, identifier, name, salary, identifierM);
    }
}
