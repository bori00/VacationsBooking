package service.service_impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import service.IOperationStatus;

@AllArgsConstructor
@Getter
public class OperationStatus implements IOperationStatus {
    private final boolean successful;
    private final String errorMessage;

    public static OperationStatus getSuccessfulOperationStatus() {
        return new OperationStatus(true, null);
    }

    public static OperationStatus getFailedOperationStatus(String errorMsg) {
        return new OperationStatus(false, errorMsg);
    }
}
