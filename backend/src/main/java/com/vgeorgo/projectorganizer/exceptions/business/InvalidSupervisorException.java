package com.vgeorgo.projectorganizer.exceptions.business;

        import lombok.Getter;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSupervisorException extends RuntimeException {
    @Getter
    private String message;
    @Getter
    private String fieldName;
    @Getter
    private Object fieldValue;

    public InvalidSupervisorException(String fieldName, Object fieldValue, String message) {
        super(message);
        this.message = String.format("%s. %s: '%s'", message, fieldName, fieldValue);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public InvalidSupervisorException(String fieldName, Object fieldValue) {
        this(fieldName, fieldValue, "Invalid provided supervisor");
    }
}
