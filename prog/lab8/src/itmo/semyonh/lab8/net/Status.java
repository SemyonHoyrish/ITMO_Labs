package itmo.semyonh.lab8.net;

public enum Status {
    PROCESSED("processed"),
    UNKNOWN_COMMAND("unknown command"),
    FAILED("failed"),
    NOT_RECEIVED("not received"),;

    private String string;
    private String reason;

    Status(String string) {
        this.string = string;
    }

    void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public String toString() {
        return string;
    }
}
