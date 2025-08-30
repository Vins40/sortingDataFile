import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Attributs attributs = new Attributs();
        attributs.setArgs(args);
        FilesUtils files = new FilesUtils();

        if(files.readFileContents()) {
            SortingData sorting = new SortingData(files.getManagerList(), files.getEmployeeList(), attributs.getValueArgsSort());
            sorting.sortingWorkerToDepartment();
            files.checkEmployeeToError(sorting.getControlList());

            if (Attributs.isArgStat()) {
                Statistics statistics = new Statistics(sorting.getWorkerInDepartment());
                statistics.getStatistics(attributs.getValueArgsPath());
            }

            files.writeDataToFiles(sorting.getWorkerInDepartment());
        }
    }
}


