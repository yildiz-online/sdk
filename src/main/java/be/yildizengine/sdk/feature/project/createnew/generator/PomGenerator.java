package be.yildizengine.sdk.feature.project.createnew.generator;

import be.yildizengine.sdk.configuration.Configuration;
import be.yildizengine.sdk.feature.project.model.Project;
import be.yildizengine.sdk.feature.project.createnew.util.FileUtil;
import be.yildizengine.sdk.feature.project.createnew.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class PomGenerator implements Generator{

    @Override
    public void generate(Project project, Configuration configuration) {
        try {
            Path pom = PathUtil.getRoot(project, configuration).resolve("pom.xml");
            Files.copy(PathUtil.getFromTemplate("pom.xml"), pom);
            FileUtil.replacePlaceHolders(pom, project);
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }
}
