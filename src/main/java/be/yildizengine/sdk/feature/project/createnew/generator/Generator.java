package be.yildizengine.sdk.feature.project.createnew.generator;

import be.yildizengine.sdk.configuration.Configuration;
import be.yildizengine.sdk.feature.project.model.Project;

public interface Generator {

    void generate(Project project, Configuration configuration);
}
