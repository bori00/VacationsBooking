package service;

public interface IOperationStatus {
    boolean isSuccessful();

    String getErrorMessage();
}
