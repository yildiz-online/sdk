package be.yildizgames.sdk.feature.project.model;

import be.yildizgames.sdk.feature.project.model.implementations.Engines;
import be.yildizgames.sdk.feature.project.model.items.Scene;

public class Project {

    public final Author author;

    public final Name name;

    public final GroupId groupId;

    public final Licence licence;

    public final Engines engines;

    public final Scene scene;

    public Project(Name name, Author author, GroupId groupId, Licence licence, Engines engines, Scene scene) {
        super();
        this.author = author;
        this.name = name;
        this.groupId = groupId;
        this.licence = licence;
        this.engines = engines;
        this.scene = scene;
    }
}
