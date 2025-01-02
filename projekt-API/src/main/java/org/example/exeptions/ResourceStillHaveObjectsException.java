package org.example.exeptions;

public class ResourceStillHaveObjectsException extends RuntimeException{
    public ResourceStillHaveObjectsException() {
        super("Nie mozesz usunac zasobu, ktory ma jeszcze obiekty");
    }
}
