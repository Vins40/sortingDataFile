import worker.Employee;
import worker.Manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Statistics {

    private static final int DECIMAL_PLACES = 2;
    public static final String FIELD_STATISTIC = "department, min, max, mid";

    private final List<Manager> listManager;
    private final List<String> listStatistic = new ArrayList<>();


    public Statistics(List<Manager> workerInDepartment) {
        this.listManager = workerInDepartment;
    }

    public List<String> getListStatistic() {
        return listStatistic;
    }

    public void getStatistics(String path) {
        String nullStatistic = "0.00,0.00,0.00";

        for (Manager manager : listManager) {
            if (manager.getListEmployee().size() > 0) {
                float max = 0.00F;
                float min = Float.MAX_VALUE;
                float mid = 0;
                for (Employee employee : manager.getListEmployee()) {
                    float salary = Float.parseFloat(employee.getSalary());
                    if (salary > max) max = salary;
                    if (salary < min) min = salary;
                    mid += salary;
                }
                mid = mid / manager.getListEmployee().size();

                listStatistic.add(String.format("%s, %s, %s, %s", manager.getDepartment(), roundOff(min), roundOff(max), roundOff(mid)));
            } else {
                listStatistic.add(String.format("%s, %s", manager.getDepartment(), nullStatistic));
            }
        }

        listStatistic.sort(String::compareTo);

        if (Attributs.isArgStat() && path == null) {
            consoleOutput();
        } else if (path != null) {
            FilesUtils.writeStatisticsToFile(getListStatistic(), path);
        }
    }

    public void consoleOutput() {
        System.out.println(FIELD_STATISTIC);
        System.out.println();
        listStatistic.forEach(System.out::println);
    }

    public BigDecimal roundOff(Float value) {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(DECIMAL_PLACES, RoundingMode.CEILING);

    }
}
