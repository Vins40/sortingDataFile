import worker.Employee;
import worker.Manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Statistics {

    private final List<Manager> listManager;
    public List<String> listStatistic = new ArrayList<>();

    public Statistics(List<Manager> workerInDepartment) {
        this.listManager = workerInDepartment;
    }

    public void getStatistics(String path)
    {

        for(Manager manager: listManager)
        {
            if(manager.getListEmployee().size()>0){
                float max = 0.00F;
                float min = Float.MAX_VALUE;
                float mid=0;


               for (Employee employee: manager.getListEmployee())
               {
                   if(Float.parseFloat(employee.getSalary())>max) max=Float.parseFloat(employee.getSalary());
                   if(Float.parseFloat(employee.getSalary())<min) min=Float.parseFloat(employee.getSalary());
                   mid+=Float.parseFloat(employee.getSalary());
               }
               mid = mid/manager.getListEmployee().size();

                listStatistic.add(String.format("%s, %s, %s, %s",manager.getDepartment(), roundOff(min), roundOff(max), roundOff(mid)));
            }
            else {
                listStatistic.add(String.format("%s, %s",manager.getDepartment(),"0.00,0.00,0.00"));
            }
        }

        listStatistic.sort((s1,s2)->s1.compareTo(s2));

        if (Attributs.argStat&&path == null) {
            consoleOutput();
        } else if (path!=null) {
            Files.writeStatisticsToFile(getListStatistic(), path);
        }
    }
    public void consoleOutput() {
        System.out.println("department, min, max, mid");
        System.out.println();
        listStatistic.forEach(System.out::println);
    }

    public List<String> getListStatistic() {
        return listStatistic;
    }

    public BigDecimal roundOff (Float value)
    {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(2,RoundingMode.CEILING);

    }
}
