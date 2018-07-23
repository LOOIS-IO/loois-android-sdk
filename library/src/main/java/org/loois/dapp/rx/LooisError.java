package org.loois.dapp.rx;

public class LooisError extends Throwable {

    public int code;

    private String message;

    /**
     * the message show to customer
     */
    private int messageId = -1;

    public LooisError(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "LooisError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
