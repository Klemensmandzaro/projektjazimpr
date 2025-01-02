package org.example.exeptions;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException() {
        super("Przedmiot juz istnieje");
    }
}
