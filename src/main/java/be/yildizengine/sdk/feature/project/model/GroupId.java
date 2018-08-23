package be.yildizengine.sdk.feature.project.model;

import java.io.File;

public class GroupId {

    public final String value;

    public GroupId(String group) {
        super();
        this.value = group;
    }

    public String toDirectory() {
        return this.value.replaceAll("\\.", File.separator);
    }
}
