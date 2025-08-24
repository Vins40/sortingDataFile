import worker.Employee;
import worker.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SortingData {
    private List<String> employeeList;
    private Map<String, String> managerList;
    private List<String> value_args;
    private List<Manager> workerInDepartment = new ArrayList<>();

    private List<Integer> controlList = new ArrayList<>();

    public SortingData(Map<String, String> managerList, List<String> employeeList, List<String> value_args) {
        this.employeeList = employeeList;
        this.managerList = managerList;
        this.value_args = value_args;
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
            if(value_args.size()!=0)
            {
                if(value_args.contains(Attributs.NAME))
                {
                    if(value_args.contains(Attributs.DESС)){
                        manager.getListEmployee().sort((s1,s2)->s2.getName().compareTo(s1.getName()));
                    }
                    else {
                        manager.getListEmployee().sort((s1,s2)->s1.getName().compareTo(s2.getName()));
                    }
                }
                else if (value_args.contains(Attributs.SALARY))
                {
                    if(value_args.contains(Attributs.DESС)){
                        manager.getListEmployee().sort((s1,s2)->s2.getSalary().compareTo(s1.getSalary()));
                    }
                    else {
                        manager.getListEmployee().sort((s1,s2)->s1.getSalary().compareTo(s2.getSalary()));
                    }
                }
            }
        }

    }

    public List<Manager> getWorkerInDepartment() {
        return workerInDepartment;
    }
    public List<Integer> getControlList() {
        return controlList;
    }
}
