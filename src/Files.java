import worker.Employee;
import worker.Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Files {
    private List<String> filesList = new ArrayList();
    private List<String> employeeList = new ArrayList();
    private Map<String, String> managerList = new HashMap<>();
    private List<String> departmentList = new ArrayList<>();
    private List<String> errorDataList = new ArrayList();
    public static final String ERROR_CREATING_FOLDER = "Error creating folder! Please check the parameter path";
    public static final String ERROR_WRITING_TO_FILE = "Error writing to the file! ";
    public static final String ERROR_CREATING_FILE = "Error creating the file! Please check the parameter path!";
    private final String PREFIX = ".sb";
    private boolean isDataFiles = false;
    private static final String filesError = "Files not found";
    private String userPatch = "";

    public static boolean isPathRight(String path) {

        File file = new File(path);
        if (file.exists()) {
            return true;
        } else {
            if (createdDirectory(path) && createdFile(path)) {
                return true;
            }
        }
        return false;
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
        return true;
    }

    public void getListFiles() {
        userPatch = System.getProperty("user.dir");
        File file = new File(userPatch);
        if (file.exists()) {
            File[] listfiles = file.listFiles();
            for (File file1 : listfiles) {
                if (!file1.isDirectory() && file1.getName().endsWith(".sb")) {
                    filesList.add(file1.getName());
                }
            }
        }
        if (filesList.size() > 0) {
            isDataFiles = true;
        } else {
            System.out.println(filesError);
        }
    }

    public void readFileContents() throws IOException {
        getListFiles();
        for (int i = 0; i < filesList.size(); i++) {

            BufferedReader reader = new BufferedReader(new FileReader(filesList.get(i)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parsingLine = line.trim().split(",");
                if (parsingLine.length == 5) {
                    if (Utils.isManager(parsingLine[0]) && Utils.isInteger(parsingLine[1].trim()) && !Utils.isNumber(parsingLine[2]) && Utils.isNumber(parsingLine[3].trim()) && !Utils.isNumber(parsingLine[4])) {
                        if (!departmentList.contains(parsingLine[4].trim())) {
                            managerList.put(parsingLine[4].trim(), line.trim());
                            departmentList.add(parsingLine[4].trim());
                        } else {
                            if (managerList.containsKey(parsingLine[4])) {
                                errorDataList.add(managerList.get(parsingLine[4]));
                                managerList.remove(parsingLine[4]);
                            }
                            errorDataList.add(line.trim());
                        }

                    } else if (Utils.isEmployee(parsingLine[0].trim()) && Utils.isInteger(parsingLine[1].trim()) && !Utils.isNumber(parsingLine[2]) && Utils.isNumber(parsingLine[3].trim()) && Utils.isNumber(parsingLine[4].trim())) {
                        employeeList.add(line.trim());
                    } else {
                        errorDataList.add(line.trim());
                    }
                } else {
                    errorDataList.add(line.trim());
                }
            }
            reader.close();
        }
    }

    public void writeDataToFiles(List<Manager> managerList) {
        for (Manager manager : managerList) {
            String fileName = manager.getDepartment() + PREFIX;
            writeDepartmentToFile(manager, fileName);
        }
        writeErrorLogToFile();
    }

    private void writeDepartmentToFile(Manager manager, String fileName) {
        FileWriter writer;
        try {
            File file = new File(fileName);
            writer = new FileWriter(file, false);

            writer.write(String.format("%s,%s,%s,%s\n", manager.getStatus(), manager.getIdentifier(), manager.getName(), manager.getSalary()));
            for (Employee employee : manager.getListEmployee()) {
                writer.write(employee.toString()+'\n');
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeErrorLogToFile() {
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

    public static boolean createdFile(String path) {

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

    public static void writeStatisticsToFile(List<String> listStatistic, String path) {
        if (isPathRight(path)) {

            File file = new File(path);
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("department, min, max, mid \n");
                for (String line : listStatistic) {
                    writer.write("\n" + line);
                }
                writer.close();
            } catch (IOException e) {
                System.out.println(ERROR_WRITING_TO_FILE + path.substring(path.lastIndexOf('\\') + 1));
            }
        }
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

    public List<String> getErrorDataList() {
        return errorDataList;
    }

    public List<String> getEmployeeList() {
        return employeeList;
    }

    public Map<String, String> getManagerList() {
        return managerList;
    }
}
