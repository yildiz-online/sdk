package be.yildizgames.sdk.feature.project.model;

public class Name {

    public final String value;

    public Name(String value) {
        super();
        if(value == null || value.isEmpty()) {
            throw NameValidationException.empty();
        }
        this.value = value;
    }
}
