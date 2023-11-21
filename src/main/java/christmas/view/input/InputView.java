package christmas.view.input;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.constants.event.EventConstants;
import christmas.view.constants.PrintFormat;
import christmas.utils.io.Printer;

public class InputView {
    private InputView() {
    }

    public static String readDate() {
        Printer.printFormattedMessage(PrintFormat.ASK_FOR_DATE, EventConstants.DECEMBER.getValue());
        return Console.readLine();
    }

    public static String readOrder() {
        Printer.printMessage(PrintFormat.ASK_FOR_ORDER);
        return Console.readLine();
    }
}
