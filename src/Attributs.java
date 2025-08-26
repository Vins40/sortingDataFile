import enums.OrderType;
import enums.SortType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Attributs {
    public enum OutputType {FILE, CONSOLE}
    public enum ArgumentType {SORT, ORDER, STAT, OUTPUT, PATH, UNKNOWN}
    public static boolean argStat = false;
    private static final String separator = "=";
    private static final String dash = "-";
    private static final String toDash = "--";
    private static final String errorInput = "Data entry error: ";
    private static final String missingParameter = "Parameter missing ";
    private static final String noStatError = "Unknown parameter: ";
    private static final Set<String> validSortArgument = Set.of(SortType.NAME.name().toLowerCase(),
        SortType.SALARY.name().toLowerCase());
    private static final Set<String> validOrderArgument = Set.of(OrderType.ASC.name().toLowerCase(),
        OrderType.DESC.name().toLowerCase());
    private static final Set<String> validOutputArgument = Set.of(OutputType.CONSOLE.name().toLowerCase(),
        OutputType.FILE.name().toLowerCase());

    private final List<String> valueArgsSort = new ArrayList<>();
    private String valueArgsPath;
    private boolean argSort = false;
    private boolean argOutputFile = false;

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
                System.out.println(errorInput + parameter);
            }
        }
        validateFinalState();
    }

    private ArgumentType getArgumentType(String parameter) {
        String SORT_Short = "s";
        String OUTPUT_Short = "o";
        if (parameter.startsWith(toDash + ArgumentType.SORT.name().toLowerCase() + separator) ||
            parameter.startsWith(dash + SORT_Short + separator))
            return ArgumentType.SORT;
        if (parameter.startsWith(toDash + ArgumentType.ORDER.name().toLowerCase() + separator))
            return ArgumentType.ORDER;
        if (parameter.equals(toDash + ArgumentType.STAT.name().toLowerCase()))
            return ArgumentType.STAT;
        if (parameter.startsWith(toDash + ArgumentType.OUTPUT.name().toLowerCase() + separator) ||
            parameter.startsWith(dash + OUTPUT_Short + separator))
            return ArgumentType.OUTPUT;
        if (parameter.startsWith(toDash + ArgumentType.PATH.name().toLowerCase() + separator))
            return ArgumentType.PATH;
        return ArgumentType.UNKNOWN;
    }

    private void getSortArgument(String currentParameter) {
        String argument = attributValue(currentParameter);
        if (validSortArgument.contains(argument)) {
            valueArgsSort.add(argument);
            argSort = true;
        } else {
            System.out.println(errorInput + ArgumentType.SORT.name().toLowerCase());
        }
    }

    private void getOrderArgument(String currentParameter) {
        if (!argSort) {
            System.out.println(errorInput + missingParameter + ArgumentType.SORT.name().toLowerCase());
            return;
        }
        String argument = attributValue(currentParameter);

        if (validOrderArgument.contains(argument)) {
            valueArgsSort.add(argument);
        } else {
            System.out.println(errorInput + ArgumentType.ORDER.name().toLowerCase());
        }
    }

    private void getOutputArgument(String currentParameter) {
        if (!isStat(currentParameter)) return;
        String argument = attributValue(currentParameter);
        if (validOutputArgument.contains(argument)) {
            argOutputFile = OutputType.FILE.name().toLowerCase().equals(argument);
        } else {
            System.out.println(errorInput + ArgumentType.OUTPUT.name().toLowerCase());
            argStat = false;
        }
    }

    private void getPathArgument(String currentParameter) {
        if (!isStat(currentParameter)) return;
        if (!argOutputFile) {
            System.out.println(missingParameter + ArgumentType.ORDER.name().toLowerCase());
            System.out.println(noStatError + currentParameter);
            argStat = false;
        }
        valueArgsPath = attributValue(currentParameter);

    }

    private void gettingUnknownArgument(String currentParameter) {
        System.out.println(noStatError + currentParameter);
        if (argOutputFile && valueArgsPath != null) return;
        argStat = false;
    }

    private void validateFinalState() {
        if (argOutputFile && getValueArgsPath() == null) {
            System.out.println(missingParameter + ArgumentType.PATH.name().toLowerCase());
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
