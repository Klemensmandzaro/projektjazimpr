package org.example.exeptions;

public class ResourceCantHaveAllNullValuesExceptions extends RuntimeException {
    public ResourceCantHaveAllNullValuesExceptions() {
        super("Przedmiot nie moze miec wszystkich pol pustych");
    }
}
