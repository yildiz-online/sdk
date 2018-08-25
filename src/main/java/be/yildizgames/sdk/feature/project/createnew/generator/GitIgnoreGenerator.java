package be.yildizgames.sdk.feature.project.createnew.generator;

import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.createnew.util.FileUtil;
import be.yildizgames.sdk.feature.project.createnew.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class GitIgnoreGenerator implements Generator{

    @Override
    public void generate(Project project, Configuration configuration) {
        try {
            Path gi = PathUtil.getRoot(project, configuration).resolve(".gitignore");
            Files.copy(PathUtil.getFromTemplate(".gitignore"), gi);
            FileUtil.replacePlaceHolders(gi, project);
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }
}
