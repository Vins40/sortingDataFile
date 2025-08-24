import java.io.IOException;



public class Main {
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
     //   E:\fileForRider.txt --sort=name --order=desc -o=file --path=E:\IdeaProjects\fileOutResult.txt
      //  --sort=salary --order=asc --stat -o=file --path=E:\IdeaProjects\fileOutResult.txt
      //  --sort=salary --order=asc --stat -o=file --path=output\statistica\statistics.txt
      //  --sort=salary --order=asc --stat --o=file --path=output\statistica\statistics.txt false
      //  --sort=salary --order=asc --stat -o=file --path=output\statistica\statistics.txt true
        //--sort=salary --order=asc --stat -o=file --path=output\statistica\statis/tics.txt false
        //--sort=salary --order=asc --stat true
    }
}
