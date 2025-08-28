import enums.OrderType;
import enums.SortType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Attributs {

    public enum OutputType {FILE, CONSOLE}
    public enum ArgumentType {SORT, ORDER, STAT, OUTPUT, PATH, UNKNOWN}

    private static final String SEPARATOR = "=";
    private static final String DASH = "-";
    private static final String TO_DASH = "--";

    private static final String ERROR_INPUT = "Data entry error: ";
    private static final String MISSING_PARAMETER = "Parameter missing ";
    private static final String NO_STAT_ERROR = "Unknown parameter: ";

    private static final Set<String> VALID_SORT_ARGUMENT = Set.of(SortType.NAME.name().toLowerCase(),
        SortType.SALARY.name().toLowerCase());
    private static final Set<String> VALID_ORDER_ARGUMENT = Set.of(OrderType.ASC.name().toLowerCase(),
        OrderType.DESC.name().toLowerCase());
    private static final Set<String> VALID_OUTPUT_ARGUMENT = Set.of(OutputType.CONSOLE.name().toLowerCase(),
        OutputType.FILE.name().toLowerCase());

    public static boolean argStat = false;
    private boolean argSort = false;
    private boolean argOutputFile = false;

    private final List<String> valueArgsSort = new ArrayList<>();
    private String valueArgsPath;


    public List<String> getValueArgsSort() {
        return valueArgsSort;
    }

    public String getValueArgsPath() {
        return valueArgsPath;
    }
    public void setArgs(String[] args) {
        for (String parameter : args) {
            try {
                ArgumentType argumentType = getArgumentType(parameter);
                switch (argumentType) {
                    case SORT -> getSortArgument(parameter);
                    case ORDER -> getOrderArgument(parameter);
                    case STAT -> argStat = true;
                    case OUTPUT -> getOutputArgument(parameter);
                    case PATH -> getPathArgument(parameter);
                    case UNKNOWN -> gettingUnknownArgument(parameter);
                }

            } catch (Exception e) {
                System.out.println(ERROR_INPUT + parameter);
            }
        }
        validateFinalState();
    }

    private ArgumentType getArgumentType(String parameter) {
        String SORT_Short = "s";
        String OUTPUT_Short = "o";
        if (parameter.startsWith(TO_DASH + ArgumentType.SORT.name().toLowerCase() + SEPARATOR) ||
            parameter.startsWith(DASH + SORT_Short + SEPARATOR))
            return ArgumentType.SORT;
        if (parameter.startsWith(TO_DASH + ArgumentType.ORDER.name().toLowerCase() + SEPARATOR))
            return ArgumentType.ORDER;
        if (parameter.equals(TO_DASH + ArgumentType.STAT.name().toLowerCase()))
            return ArgumentType.STAT;
        if (parameter.startsWith(TO_DASH + ArgumentType.OUTPUT.name().toLowerCase() + SEPARATOR) ||
            parameter.startsWith(DASH + OUTPUT_Short + SEPARATOR))
            return ArgumentType.OUTPUT;
        if (parameter.startsWith(TO_DASH + ArgumentType.PATH.name().toLowerCase() + SEPARATOR))
            return ArgumentType.PATH;
        return ArgumentType.UNKNOWN;
    }

    private void getSortArgument(String currentParameter) {
        String argument = attributValue(currentParameter);
        if (VALID_SORT_ARGUMENT.contains(argument)) {
            valueArgsSort.add(argument);
            argSort = true;
        } else {
            System.out.println(ERROR_INPUT + ArgumentType.SORT.name().toLowerCase());
        }
    }

    private void getOrderArgument(String currentParameter) {
        if (!argSort) {
            System.out.println(ERROR_INPUT + MISSING_PARAMETER + ArgumentType.SORT.name().toLowerCase());
            return;
        }
        String argument = attributValue(currentParameter);

        if (VALID_ORDER_ARGUMENT.contains(argument)) {
            valueArgsSort.add(argument);
        } else {
            System.out.println(ERROR_INPUT + ArgumentType.ORDER.name().toLowerCase());
        }
    }

    private void getOutputArgument(String currentParameter) {
        if (!isStat(currentParameter)) return;
        String argument = attributValue(currentParameter);
        if (VALID_OUTPUT_ARGUMENT.contains(argument)) {
            argOutputFile = OutputType.FILE.name().toLowerCase().equals(argument);
        } else {
            System.out.println(ERROR_INPUT + ArgumentType.OUTPUT.name().toLowerCase());
            argStat = false;
        }
    }

    private void getPathArgument(String currentParameter) {
        if (!isStat(currentParameter)) return;
        if (!argOutputFile) {
            System.out.println(MISSING_PARAMETER + ArgumentType.ORDER.name().toLowerCase());
            System.out.println(NO_STAT_ERROR + currentParameter);
            argStat = false;
        }
        valueArgsPath = attributValue(currentParameter);

    }

    private void gettingUnknownArgument(String currentParameter) {
        System.out.println(NO_STAT_ERROR + currentParameter);
        if (argOutputFile && valueArgsPath != null) return;
        argStat = false;
    }

    private void validateFinalState() {
        if (argOutputFile && getValueArgsPath() == null) {
            System.out.println(MISSING_PARAMETER + ArgumentType.PATH.name().toLowerCase());
            argStat = false;
        }
    }

    private boolean isStat(String line) {
        if (argStat) {
            return true;
        } else {
            System.out.println(NO_STAT_ERROR + line);
            return false;
        }
    }

    private String attributValue(String s) {
        int valueSeparator = s.indexOf(SEPARATOR);
        return s.substring(valueSeparator + 1);
    }


}
