package christmas.utils.io;

import christmas.view.constants.PrintFormat;

public class Printer {
    private Printer() {
    }

    public static void printMessage(PrintFormat format) {
        System.out.println(format.getFormat());
    }

    public static void printFormattedMessage(PrintFormat format, Object object) {
        System.out.println(format.format(object));
    }

    public static void printException(IllegalArgumentException e) {
        System.out.println("[ERROR] : " + e.getMessage());
    }
}
