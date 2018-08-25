package be.yildizgames.sdk.entrypoint;

import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.createnew.generator.GeneratorHandler;
import be.yildizgames.sdk.feature.project.createnew.ui.ProjectCreationWindow;
import be.yildizgames.sdk.feature.project.model.Author;
import be.yildizgames.sdk.feature.project.model.GroupId;
import be.yildizgames.sdk.feature.project.model.Licence;
import be.yildizgames.sdk.feature.project.model.Name;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.implementations.Engines;

public class EntryPoint {

    public static void main(String[] args) {
        Configuration configuration = new Configuration( System.getProperty("user.home") + "/test-projects");
        new ProjectCreationWindow().init(configuration);
    }
}
