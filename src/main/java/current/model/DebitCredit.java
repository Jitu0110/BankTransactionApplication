package current.model;

public enum DebitCredit {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String value;

    DebitCredit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
