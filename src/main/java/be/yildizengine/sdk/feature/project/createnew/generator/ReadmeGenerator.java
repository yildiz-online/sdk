package be.yildizengine.sdk.feature.project.createnew.generator;

import be.yildizengine.sdk.configuration.Configuration;
import be.yildizengine.sdk.feature.project.model.Project;
import be.yildizengine.sdk.feature.project.createnew.util.FileUtil;
import be.yildizengine.sdk.feature.project.createnew.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ReadmeGenerator implements Generator{

    @Override
    public void generate(Project project, Configuration configuration) {
        try {
            Path rm = PathUtil.getRoot(project, configuration).resolve("README.md");
            Files.copy(PathUtil.getFromTemplate("README.md"), rm);
            FileUtil.replacePlaceHolders(rm, project);
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }
}
