package utils;

public final class MathUtils {

    private MathUtils() {
    }

    public static boolean isNumber(String number) {
        try {
            return Double.parseDouble(number) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String number) {
        try {
            return Integer.parseInt(number) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
