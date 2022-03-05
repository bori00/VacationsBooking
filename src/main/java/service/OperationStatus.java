package service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OperationStatus {
    private final boolean successful;
    private final String errorMsg;

    public static OperationStatus getSuccessfulOperationStatus() {
        return new OperationStatus(true, null);
    }

    public static OperationStatus getFailedOperationStatus(String errorMsg) {
        return new OperationStatus(false, errorMsg);
    }
}
