package christmas.domain.constants.event.reward;

public enum Badge {
    STAR("별"),
    TREE("트리"),
    SANTA("산타");

    private final String printFormat;

    Badge(String printFormat) {
        this.printFormat = printFormat;
    }

    public String getPrintFormat() {
        return printFormat;
    }
}
