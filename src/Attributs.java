import java.util.ArrayList;
import java.util.List;

public class Attributs {

    private String[] args;
    private final String separator = "=";
    private final String dash = "-";
    private final String toDash = "--";
    private List<String> value_args_sort = new ArrayList<>();
    private String value_args_path;
    public static final String SORT = "sort";
    private final String SORT_Short = "s";
    public static final String ORDER = "order";
    public static final String OUTPUT = "output";
    private final String OUTPUT_Short = "o";
    public static final String PATH = "path";
    private static final String STAT = "stat";
    public static final String NAME = "name";
    public static final String SALARY = "salary";
    private final String ASC = "asc";
    public static final String DESС = "desc";
    public static final String FILE = "file";
    public static final String CONSOLE = "console";

    private final String errorInput = "Data entry error: ";
    private final String missingParameter = "Parameter missing ";
    private final String noStatError = "Unknown parameter: ";
    public static boolean argSort = false;
    public static boolean argStat = false;

    public static boolean argOutputFile = false;

    public Attributs(String[] args) {
        this.args = args;
    }


    public void setArgs() {
        for (int i = 0; i <= args.length - 1; i++) {
            if (args[i].startsWith(toDash + SORT + separator) || args[i].startsWith(dash + SORT_Short + separator)) {

                String parameter = attributValue(args[i]);
                if (parameter.equals(NAME) || parameter.equals(SALARY)) {
                    value_args_sort.add(parameter);
                    argSort = true;
                } else {
                    System.out.println(errorInput + SORT);
                }

            } else if (args[i].startsWith(toDash + ORDER + separator)) {
                if (argSort) {
                    String parameter = attributValue(args[i]);
                    if (parameter.equals(ASC) || parameter.equals(DESС)) {
                        value_args_sort.add(parameter);
                    } else {
                        System.out.println(errorInput + ORDER);
                    }
                } else {
                    System.out.println(errorInput + missingParameter + SORT);
                }
            } else if (args[i].startsWith(toDash + STAT)) {
                argStat = true;
            } else if (args[i].startsWith(toDash + OUTPUT + separator) || args[i].startsWith(dash + OUTPUT_Short + separator)) {
                if (isStatTrue(args[i])) {
                    String parametr = attributValue(args[i]);
                    if (parametr.equals(CONSOLE) || parametr.equals(FILE)) {
                        if (parametr.equals(FILE)) argOutputFile = true;
                    } else {
                        System.out.println(errorInput + OUTPUT);
                        argStat=false;
                    }
                }
            } else if (args[i].startsWith(toDash + PATH + separator)) {
                if (isStatTrue(args[i])) {
                    String path = attributValue(args[i]);
                    if (argOutputFile) {
                        value_args_path=path;
                    }
                    else
                    {
                        System.out.println(missingParameter + OUTPUT);
                        System.out.println(noStatError + args[i]);
                        argStat=false;
                    }

                }
            }
            else {
                System.out.println(noStatError + args[i]);
                if(argStat) argStat=false;
            }
        }
        if(argOutputFile&&getValue_args_path()==null)
        {
            System.out.println(missingParameter + PATH);
            argStat=false;
        }
    }

    private boolean isStatTrue(String line) {

        if (argStat) {
            return true;
        } else {
            System.out.println(noStatError + line);
            argStat=false;
        return false;
        }

    }

    private String attributValue(String s) {
        int valueSeparator = s.indexOf(separator);
        return s.substring(valueSeparator + 1);
    }

    public List<String> getValue_args_sort() {
        return value_args_sort;
    }

    public String getValue_args_path() {
        return value_args_path;
    }
}
