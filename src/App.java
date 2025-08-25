import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Attributs attributs = new Attributs();
        attributs.setArgs(args);
        Files files = new Files();
        files.readFileContents();
        SortingData sorting = new SortingData(files.getManagerList(), files.getEmployeeList(), attributs.getValueArgsSort());
        sorting.sortingWorkerToDepartment();
        files.checkEmployeeToError(sorting.getControlList());
        if(Attributs.argStat)
        {
            Statistics statistics = new Statistics(sorting.getWorkerInDepartment());
            statistics.getStatistics(attributs.getValueArgsPath());
        }

        files.writeDataToFiles(sorting.getWorkerInDepartment());

    }
}
