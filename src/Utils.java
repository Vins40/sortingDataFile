public class Utils {

    private Utils() {
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

    public static boolean isEmployee(String worker) {

        return worker.equals("Employee");
    }

    public static boolean isManager(String worker) {

        return worker.equals("Manager");
    }
}
