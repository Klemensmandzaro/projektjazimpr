package org.example.exeptions;

public class ResourceNotExistException extends RuntimeException {
    public ResourceNotExistException() {
        super("Zasób nie istnieje");
    }
}
