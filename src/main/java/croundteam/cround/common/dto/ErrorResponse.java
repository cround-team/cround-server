package croundteam.cround.common.dto;

public class ErrorResponse {
    private String data;
    private String message;

    public ErrorResponse(String message) {
        this(null, message);
    }

    public ErrorResponse(String data, String message) {
        this.data = data;
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
