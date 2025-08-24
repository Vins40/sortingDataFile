import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Attributs attributs = new Attributs(args);
        attributs.setArgs();
        Files files = new Files();
        files.readFileContents();
        SortingData sorting = new SortingData(files.getManagerList(), files.getEmployeeList(), attributs.getValue_args_sort());
        sorting.sortingWorkerToDepartment();
        files.checkEmployeeToError(sorting.getControlList());
        if(Attributs.argStat)
        {
            Statistics statistics = new Statistics(sorting.getWorkerInDepartment());
            statistics.getStatistics(attributs.getValue_args_path());
        }

        files.writeDataToFiles(sorting.getWorkerInDepartment());

    }
}
