package be.yildizgames.sdk.feature.project.model;

public class GroupId {

    public final String value;

    public GroupId(String group) {
        super();
        this.value = group;
    }

    public String toDirectory() {
        return this.value.replaceAll("\\.", "/");
    }
}
