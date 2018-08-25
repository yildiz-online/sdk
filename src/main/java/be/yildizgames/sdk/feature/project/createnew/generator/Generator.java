package be.yildizgames.sdk.feature.project.createnew.generator;

import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.model.Project;

public interface Generator {

    void generate(Project project, Configuration configuration);
}
