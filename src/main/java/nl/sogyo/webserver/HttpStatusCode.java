package nl.sogyo.webserver;

public enum HttpStatusCode {
    OK(200, "OK"), NotFound(404, "Not Found"), ServerError(500, "Server Error");

    private int code;
    private String description;

    private HttpStatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}