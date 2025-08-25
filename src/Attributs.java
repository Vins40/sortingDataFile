import java.util.ArrayList;
import java.util.List;

public class Attributs {

    public static final String PATH = "path";
    public static boolean argStat = false;
    static final String NAME = "name";
    static final String SALARY = "salary";
    static final String DESС = "desc";
    private static final String SORT = "sort";
    private static final String ORDER = "order";
    private static final String OUTPUT = "output";
    private static final String FILE = "file";
    private static final String CONSOLE = "console";
    private static final String STAT = "stat";
    private static final String separator = "=";
    private static final String dash = "-";
    private static final String toDash = "--";
    private static final String errorInput = "Data entry error: ";
    private static final String missingParameter = "Parameter missing ";
    private static final String noStatError = "Unknown parameter: ";
    private final List<String> valueArgsSort = new ArrayList<>();
    private String valueArgsPath;
    private boolean argSort = false;
    private boolean argOutputFile = false;
    public void setArgs(String[] args) {
        for (int i = 0; i <= args.length - 1; i++) {
            String currentParameter = args[i];
            try {
                if (isSortArgument(currentParameter)) {
                    getSortArgument(currentParameter);
                } else if (isOrderArrgument(currentParameter)) {
                    getOrderArgument(currentParameter);
                } else if (isStatArgument(currentParameter)) {
                    argStat = true;
                } else if (isOutputArgument(currentParameter)) {
                    getOutputArgument(currentParameter);
                } else if (isPathArgument(currentParameter)) {
                    getPathArgument(currentParameter);
                } else {
                    gettingUnknownArgument(currentParameter);
                }
            } catch (Exception e) {
                System.out.println(errorInput + currentParameter);
            }
        }
        validateFinalState();
    }

    private boolean isSortArgument(String currentParameter) {
        String SORT_Short = "s";
        return currentParameter.startsWith(toDash + SORT + separator) ||
            currentParameter.startsWith(dash + SORT_Short + separator);
    }

    private boolean isOrderArrgument(String currentParameter) {
        return currentParameter.startsWith(toDash + ORDER + separator);
    }

    private boolean isStatArgument(String currentParameter) {
        return currentParameter.startsWith(toDash + STAT);
    }

    private boolean isOutputArgument(String currentParameter) {
        String OUTPUT_Short = "o";
        return currentParameter.startsWith(toDash + OUTPUT + separator) ||
            currentParameter.startsWith(dash + OUTPUT_Short + separator);
    }

    private boolean isPathArgument(String currentParameter) {
        return currentParameter.startsWith(toDash + PATH + separator);
    }

    private void getSortArgument(String currentParameter) {
        String parameter = attributValue(currentParameter);
        if (NAME.equals(parameter) || SALARY.equals(parameter)) {
            valueArgsSort.add(parameter);
            argSort = true;
        } else {
            System.out.println(errorInput + SORT);
        }
    }

    private void getOrderArgument(String currentParameter) {
        if (!argSort) {
            System.out.println(errorInput + missingParameter + SORT);
            return;
        }
        String ASC = "asc";
        String parameter = attributValue(currentParameter);

        if (ASC.equals(parameter) || DESС.equals(parameter)) {
            valueArgsSort.add(parameter);
        } else {
            System.out.println(errorInput + ORDER);
        }
    }

    private void getOutputArgument(String currentParameter) {
        if (!isStat(currentParameter)) return;
        String parameter = attributValue(currentParameter);
        if (CONSOLE.equals(parameter) || FILE.equals(parameter)) {
            argOutputFile = FILE.equals(parameter);
        } else {
            System.out.println(errorInput + OUTPUT);
            argStat = false;
        }
    }

    private void getPathArgument(String currentParameter) {
        if (!isStat(currentParameter)) return;
        {
            if (!argOutputFile) {
                System.out.println(missingParameter + OUTPUT);
                System.out.println(noStatError + currentParameter);
                argStat = false;
            }
            valueArgsPath = attributValue(currentParameter);
        }
    }

    private void gettingUnknownArgument(String currentParameter) {
        System.out.println(noStatError + currentParameter);
        argStat = false;
    }

    private void validateFinalState() {
        if (argOutputFile && getValueArgsPath() == null) {
            System.out.println(missingParameter + PATH);
            argStat = false;
        }
    }

    private boolean isStat(String line) {
        if (argStat) {
            return true;
        } else {
            System.out.println(noStatError + line);
            return false;
        }
    }

    private String attributValue(String s) {
        int valueSeparator = s.indexOf(separator);
        return s.substring(valueSeparator + 1);
    }

    public List<String> getValueArgsSort() {
        return valueArgsSort;
    }

    public String getValueArgsPath() {
        return valueArgsPath;
    }
}
