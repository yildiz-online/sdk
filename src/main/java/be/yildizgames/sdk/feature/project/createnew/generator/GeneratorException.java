package be.yildizgames.sdk.feature.project.createnew.generator;

public class GeneratorException extends RuntimeException {

    GeneratorException(String s) {
        super(s);
    }

    public GeneratorException(Exception e) {
        super(e);
    }
}
