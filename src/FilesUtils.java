import utils.MathUtils;
import utils.WorkerUtils;
import worker.Employee;
import worker.Manager;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilesUtils {
    private static final String FILES_ERROR = "Files not found";
    private static final String ERROR_CREATING_FOLDER = "Error creating folder! Please check the parameter path ";
    private static final String ERROR_WRITING_TO_FILE = "Error writing to the file!  ";
    private static final String ERROR_CREATING_FILE = "Error creating the file! Please check the parameter path!";

    private final List<String> filesList = new ArrayList();
    private final List<String> employeeList = new ArrayList();
    private final Map<String, String> managerList = new HashMap<>();
    private final List<String> departmentList = new ArrayList<>();
    private final List<String> errorDataList = new ArrayList();

    public static boolean isPathRight(String path) {

        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            if (createdDirectory(path)) {
                return true;
            }
        }
        return false;
    }

    public static void writeStatisticsToFile(List<String> listStatistic, String path) {
        if (!(listStatistic.size() > 1)) return;
        if (isPathRight(path) && listStatistic.size() > 0) {

            File file = new File(path);
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(Statistics.FIELD_STATISTIC + '\n');
                for (String line : listStatistic) {
                    writer.write("\n" + line);
                }
                writer.close();
            } catch (IOException e) {
                System.out.println(ERROR_WRITING_TO_FILE + path.substring(path.lastIndexOf('\\') + 1));
            }
        }
    }

    public List<String> getEmployeeList() {
        return employeeList;
    }

    public Map<String, String> getManagerList() {
        return managerList;
    }

    public void writeDataToFiles(List<Manager> managerList) {
        for (Manager manager : managerList) {
            String PREFIX = ".sb";
            String fileName = manager.getDepartment() + PREFIX;
            writeDepartmentToFile(manager, fileName);
        }
        writeErrorLogToFile();
    }

    public boolean getListFiles() {
        String userPatch = System.getProperty("user.dir");
        File file = new File(userPatch);
        if (file.exists()) {
            File[] listfiles = file.listFiles();
            for (File file1 : listfiles) {
                if (!file1.isDirectory() && file1.getName().endsWith(".sb")) {
                    filesList.add(file1.getName());
                }
            }
        }
        if (!(filesList.size() > 0)) {
            System.out.println(FILES_ERROR);
            return false;
        }
        return true;
    }

    public boolean readFileContents() throws IOException {
        if (!getListFiles()) return false;
        for (String fileName : filesList) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    parseLine(line.trim());
                }
            }
        }
        return true;
    }

    public void checkEmployeeToError(List<Integer> controlList) {
        if (controlList.size() != errorDataList.size()) {
            List<String> copyList = new ArrayList<>(employeeList);
            for (int i = 0; i < controlList.size(); i++) {
                copyList.set(controlList.get(i), null);
            }
            for (String s : copyList) {
                if (s != null) errorDataList.add(s);
            }
        }
    }

    private static boolean createdFile(String path) {
        File newFoldr = new File(path);
        if (!newFoldr.exists()) {
            try {
                boolean isCreated = newFoldr.createNewFile();
                if (!isCreated) {
                    System.out.println(ERROR_CREATING_FILE);
                }
            } catch (IOException e) {
                System.out.println(ERROR_CREATING_FILE);
                return false;
            }
        }
        return true;
    }

    private static boolean createdDirectory(String path) {
        File file = new File(path.substring(0, path.lastIndexOf('\\') + 1));
        if (!file.isDirectory()) {
            boolean newDirectory = file.mkdirs();

            if (!newDirectory) {
                System.out.println(ERROR_CREATING_FOLDER);
                return false;
            }
        }
        if (!createdFile(path)) {

            return false;
        }
        return true;
    }

    private void parseLine(String line) {
        String[] parsingLine = line.split(",");
        if (parsingLine.length != 5) {
            errorDataList.add(line);
            return;
        }
        for (int i = 0; i < parsingLine.length; i++) {
            parsingLine[i] = parsingLine[i].trim();
        }
        if (isValidManager(parsingLine)) {
            checkToManagerFromOneDepartment(parsingLine, line);

        } else if (isValidEmployee(parsingLine)) {
            employeeList.add(line.trim());
        } else {
            errorDataList.add(line);
        }
    }

    private void checkToManagerFromOneDepartment(String[] parsingLine, String line) {
        String department = parsingLine[4];
        if (!departmentList.contains(department)) {
            managerList.put(department, line);
            departmentList.add(department);
        } else {
            String existManager = managerList.remove(department);
            if (existManager != null) {
                errorDataList.add(existManager);
            }
            errorDataList.add(line);
        }
    }

    private boolean isValidManager(String[] parsingLine) {
        return WorkerUtils.isManager(parsingLine[0]) &&
            MathUtils.isInteger(parsingLine[1].trim()) &&
            !MathUtils.isNumber(parsingLine[2]) &&
            MathUtils.isNumber(parsingLine[3].trim()) &&
            !MathUtils.isNumber(parsingLine[4]);
    }

    private boolean isValidEmployee(String[] parsingLine) {
        return WorkerUtils.isEmployee(parsingLine[0].trim()) &&
            MathUtils.isInteger(parsingLine[1].trim()) &&
            !MathUtils.isNumber(parsingLine[2]) &&
            MathUtils.isNumber(parsingLine[3].trim()) &&
            MathUtils.isNumber(parsingLine[4].trim());
    }

    private void writeDepartmentToFile(Manager manager, String fileName) {
        FileWriter writer;
        try {
            File file = new File(fileName);
            writer = new FileWriter(file, false);

            writer.write(String.format("%s,%s,%s,%s\n", manager.getStatus(), manager.getIdentifier(), manager.getName(), manager.getSalary()));
            for (Employee employee : manager.getListEmployee()) {
                writer.write(employee.toString() + '\n');
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeErrorLogToFile() {
        if (!(errorDataList.size() > 0)) return;
        FileWriter writer;
        File file = null;
        try {
            file = new File("error.log");
            writer = new FileWriter(file, false);
            for (String line : errorDataList) {
                writer.write(line + '\n');
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(ERROR_CREATING_FILE + file.getName());
        }
    }
}
