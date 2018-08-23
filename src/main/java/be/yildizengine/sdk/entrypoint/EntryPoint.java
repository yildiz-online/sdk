package be.yildizengine.sdk.entrypoint;

import be.yildizengine.sdk.configuration.Configuration;
import be.yildizengine.sdk.feature.project.createnew.generator.GeneratorHandler;
import be.yildizengine.sdk.feature.project.model.Author;
import be.yildizengine.sdk.feature.project.model.GroupId;
import be.yildizengine.sdk.feature.project.model.Licence;
import be.yildizengine.sdk.feature.project.model.Name;
import be.yildizengine.sdk.feature.project.model.Project;
import be.yildizengine.sdk.feature.project.model.implementations.Engines;

public class EntryPoint {

    public static void main(String[] args) {
        //new ProjectCreationWindow().init();
        Configuration configuration = new Configuration("/home/gregory/test-projects");
        Project p = new Project(new Author("author"), new Name("myProject"), new GroupId("be.test.sdk"), Licence.MIT, Engines.defaultNoNetwork());
        GeneratorHandler
                .forProject(p, configuration)
                .run();
    }
}
