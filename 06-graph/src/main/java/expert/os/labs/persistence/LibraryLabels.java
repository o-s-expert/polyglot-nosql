package expert.os.labs.persistence;

import java.util.function.Supplier;

public enum LibraryLabels implements Supplier<String> {

    IS("is");

    private final String value;

    LibraryLabels(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}
