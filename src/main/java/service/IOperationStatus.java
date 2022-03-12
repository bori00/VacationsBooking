package service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import service.service_impl.OperationStatus;

public interface IOperationStatus {
    boolean isSuccessful();

    String getErrorMessage();
}
