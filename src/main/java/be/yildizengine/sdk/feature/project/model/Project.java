package be.yildizengine.sdk.feature.project.model;

import be.yildizengine.sdk.feature.project.model.implementations.Engines;

public class Project {

    public final Author author;

    public final Name name;

    public final GroupId groupId;

    public final Licence licence;

    public final Engines engines;

    public Project(Author author, Name name, GroupId groupId, Licence licence, Engines engines) {
        super();
        this.author = author;
        this.name = name;
        this.groupId = groupId;
        this.licence = licence;
        this.engines = engines;
    }
}
