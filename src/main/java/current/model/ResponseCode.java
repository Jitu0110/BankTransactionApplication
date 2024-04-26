package current.model;

public enum ResponseCode {
    APPROVED("APPROVED"),
    DECLINED("DECLINED");

    private final String value;

    ResponseCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
