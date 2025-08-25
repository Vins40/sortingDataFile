package worker;

public class Employee {
    private final String status;
    private final Integer identifier;
    private final String name;
    private final String salary;
    private final Integer identifier_m;


    public Employee(Integer identifier, String name, String salary, Integer identifier_m) {
        this.status = "Employee";
        this.identifier = identifier;
        this.name = name;
        this.salary = salary;
        this.identifier_m = identifier_m;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", status, identifier, name, salary, identifier_m);
    }
}
