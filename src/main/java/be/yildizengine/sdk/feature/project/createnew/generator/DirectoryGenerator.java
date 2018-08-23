package be.yildizengine.sdk.feature.project.createnew.generator;

import be.yildizengine.sdk.configuration.Configuration;
import be.yildizengine.sdk.feature.project.model.Project;
import be.yildizengine.sdk.feature.project.createnew.util.PathUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class DirectoryGenerator implements Generator{

    @Override
    public void generate(Project project, Configuration configuration) {
        try {
            Path base = PathUtil.getRoot(project, configuration);
            if(Files.exists(base)) {
                throw new GeneratorException("A directory with this name already exists");
            }
            Files.createDirectories(base);
            Files.createDirectory(base.resolve("media"));
            Path src = base.resolve("src");
            Path main = src.resolve("main");
            Path test = src.resolve("test");
            Files.createDirectories(main.resolve("java" + File.separator + project.groupId.toDirectory() + File.separator + "entrypoint"));
            Files.createDirectories(main.resolve("resources"));
            Files.createDirectories(test.resolve("java" + File.separator + project.groupId.toDirectory()));
            Files.createDirectories(test.resolve("resources"));
        } catch (IOException e) {
            throw new GeneratorException(e);
        }

    }
}
