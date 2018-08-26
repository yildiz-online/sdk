package be.yildizgames.sdk.feature.project.model;

import be.yildizgames.sdk.feature.project.createnew.generator.GeneratorException;

public class GroupValidationException extends GeneratorException {

    private GroupValidationException(String msg) {
        super(msg);
    }

    static GroupValidationException empty() {
        return new GroupValidationException("Empty group");
    }
}
