package be.yildizgames.sdk.feature.project.createnew.generator;

import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.createnew.util.FileUtil;
import be.yildizgames.sdk.feature.project.createnew.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class LicenceGenerator implements Generator{

    @Override
    public void generate(Project project, Configuration configuration) {
        try {
            Path lc = PathUtil.getRoot(project, configuration).resolve("LICENCE");
            Files.copy(PathUtil.getFromTemplate("licences/" + project.licence + "_en"), lc);
            FileUtil.replacePlaceHolders(lc, project);
        } catch (IOException e) {
            throw new GeneratorException(e);
        }
    }
}
