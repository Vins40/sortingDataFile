import worker.Employee;
import worker.Manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Statistics {

    public List<String> listStatistic = new ArrayList<>();

    public static final String fieldStatistic = "department, min, max, mid";

    private final List<Manager> listManager;

    private static final int decimalPlaces = 2;

    public Statistics(List<Manager> workerInDepartment) {
        this.listManager = workerInDepartment;
    }

    public void getStatistics(String path) {
        String nullStatistic = "0.00,0.00,0.00";

        for (Manager manager : listManager) {
            if (manager.getListEmployee().size() > 0) {
                float max = 0.00F;
                float min = Float.MAX_VALUE;
                float mid = 0;
                for (Employee employee : manager.getListEmployee()) {
                    if (Float.parseFloat(employee.getSalary()) > max) max = Float.parseFloat(employee.getSalary());
                    if (Float.parseFloat(employee.getSalary()) < min) min = Float.parseFloat(employee.getSalary());
                    mid += Float.parseFloat(employee.getSalary());
                }
                mid = mid / manager.getListEmployee().size();

                listStatistic.add(String.format("%s, %s, %s, %s", manager.getDepartment(), roundOff(min), roundOff(max), roundOff(mid)));
            } else {
                listStatistic.add(String.format("%s, %s", manager.getDepartment(), nullStatistic));
            }
        }

        listStatistic.sort(String::compareTo);

        if (Attributs.argStat && path == null) {
            consoleOutput();
        } else if (path != null) {
            Files.writeStatisticsToFile(getListStatistic(), path);
        }
    }

    public void consoleOutput() {
        System.out.println(fieldStatistic);
        System.out.println();
        listStatistic.forEach(System.out::println);
    }

    public List<String> getListStatistic() {
        return listStatistic;
    }

    public BigDecimal roundOff(Float value) {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(decimalPlaces, RoundingMode.CEILING);

    }
}
