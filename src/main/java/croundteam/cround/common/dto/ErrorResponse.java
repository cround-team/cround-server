package croundteam.cround.common.dto;

public class ErrorResponse<T> {
    private T data;
    private String message;

    public ErrorResponse(String message) {
        this(null, message);
    }

    public ErrorResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
