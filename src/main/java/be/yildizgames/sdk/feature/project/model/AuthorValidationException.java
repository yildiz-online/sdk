package be.yildizgames.sdk.feature.project.model;

import be.yildizgames.sdk.feature.project.createnew.generator.GeneratorException;

public class AuthorValidationException extends GeneratorException {

    private AuthorValidationException(String msg) {
        super(msg);
    }

    private AuthorValidationException(Exception e) {
        super(e);
    }

    static AuthorValidationException empty() {
        return new AuthorValidationException("Empty author");
    }
}
