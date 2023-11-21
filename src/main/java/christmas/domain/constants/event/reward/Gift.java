package christmas.domain.constants.event.reward;

public enum Gift {
    CHAMPAGNE("샴페인", 25_000);

    private final String printFormat;
    private final int price;

    Gift(String printFormat, int price) {
        this.printFormat = printFormat;
        this.price = price;
    }

    public String getPrintFormat() {
        return printFormat;
    }

    public int getPrice() {
        return price;
    }
}
