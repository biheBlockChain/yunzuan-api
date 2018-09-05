package one.bihe.yunzuan.api;

public class ResultData<T> {
    private int code;
    private int errorCode;
    private String message;
    private T data;

    public static <T> ResultData<T> createError(String message) {
        return createError(0, message);
    }

    public static <T> ResultData<T> createError(int errorCode, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.code = 0;
        resultData.errorCode = errorCode;
        resultData.message = message;
        return resultData;
    }

    public boolean isSuccess() {
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
