package be.yildizgames.sdk.feature.project.createnew.generator;

import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.createnew.util.PathUtil;
import be.yildizgames.sdk.feature.project.model.NameValidationException;
import be.yildizgames.sdk.feature.project.model.Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class DirectoryGenerator implements Generator{

    @Override
    public void generate(Project project, Configuration configuration) {
        try {
            Path base = PathUtil.getRoot(project, configuration);
            if(Files.exists(base)) {
                throw NameValidationException.exist();
            }
            Files.createDirectories(base);
            Files.createDirectory(base.resolve("media"));
            Path src = base.resolve("src");
            Path main = src.resolve("main");
            Path test = src.resolve("test");
            Files.createDirectories(main.resolve("java/" + project.groupId.toDirectory() + "/entrypoint"));
            Files.createDirectories(main.resolve("resources"));
            Files.createDirectories(test.resolve("java/" + project.groupId.toDirectory()));
            Files.createDirectories(test.resolve("resources"));
        } catch (IOException e) {
            throw new GeneratorException(e);
        }

    }
}
