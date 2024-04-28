package current.model;

public enum TypeOfRequest {
    LOAD("LOAD"),
    AUTHORIZATION("AUTHORIZATION");

    private final String value;

    TypeOfRequest(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
