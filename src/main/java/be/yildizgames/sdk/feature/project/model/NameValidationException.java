package be.yildizgames.sdk.feature.project.model;

import be.yildizgames.sdk.feature.project.createnew.generator.GeneratorException;

public class NameValidationException extends GeneratorException {

    private NameValidationException(String msg) {
        super(msg);
    }

    static NameValidationException empty() {
        return new NameValidationException("Name is empty");
    }

    public static NameValidationException exist() {
        return new NameValidationException("A directory with this name already exists");
    }
}
