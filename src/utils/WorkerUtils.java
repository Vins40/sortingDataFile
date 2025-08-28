package utils;

public final class WorkerUtils {
    public WorkerUtils() {
    }

    public static boolean isEmployee(String worker) {
        return worker.equals("Employee");
    }

    public static boolean isManager(String worker) {
        return worker.equals("Manager");
    }
}
