package be.yildizengine.sdk.feature.project.createnew.util;

import be.yildizengine.sdk.configuration.Configuration;
import be.yildizengine.sdk.feature.project.model.Project;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {

    public static Path getRoot(Project project, Configuration configuration) {
        return Paths.get(configuration.rootPath,  project.name.value);
    }

    public static Path getFromTemplate(String f) {
        File file = new File(PathUtil.class.getClassLoader().getResource("template/" + f).getFile());
        return Paths.get(file.getAbsoluteFile().toURI());
    }
}
