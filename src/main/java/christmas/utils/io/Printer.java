package christmas.utils.io;

import christmas.view.constants.PrintFormat;

public class Printer {
    private Printer() {
    }

    public static void printMessage(PrintFormat format) {
        System.out.println(format.getFormat());
    }

    public static void printFormattedMessage(PrintFormat format, Object... args) {
        System.out.println(format.format(args));
    }

    public static void printException(IllegalArgumentException e) {
        System.out.println("[ERROR] " + e.getMessage());
    }

    public static void printSeparator() {
        System.out.println();
    }
}
