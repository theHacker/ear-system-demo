package biz.thehacker.demo.earsystem.model;

import java.util.Objects;

public class GenericResponse {

    private final boolean success;

    /** Error message, only present for {@code success == false} */
    private final String errorMessage;

    private GenericResponse(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static GenericResponse success() {
        return new GenericResponse(true, null);
    }

    public static GenericResponse failure(String errorMessage) {
        return new GenericResponse(
                false,
                Objects.requireNonNull(errorMessage)
        );
    }
}
