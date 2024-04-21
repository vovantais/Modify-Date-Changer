public class DateChangerUtils {

    public static String getUtcDate() {
        long unixDate = System.currentTimeMillis() / 1000;
        return "Date(" + unixDate + "000)";
    }
}
