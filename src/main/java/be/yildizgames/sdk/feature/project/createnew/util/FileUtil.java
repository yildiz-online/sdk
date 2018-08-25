package be.yildizgames.sdk.feature.project.createnew.util;

import be.yildizgames.sdk.feature.project.model.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    public static void replacePlaceHolders(Path file, Project project) throws IOException {
        String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        content = content
                .replaceAll("--NAME--", project.name.value)
                .replaceAll("--AUTHOR--" ,project.author.value)
                .replaceAll("--GROUP--", project.groupId.value);
        content = replaceEngine(content, "audio", project.engines.audio.name());
        content = replaceEngine(content, "graphic", project.engines.graphic.name());
        content = replaceEngine(content, "network", project.engines.network.name());
        content = replaceEngine(content, "physic", project.engines.physics.name());
        content = replaceEngine(content, "scripting", project.engines.scripting.name());
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));
    }

    private static String replaceEngine(String input, String context, String engine) throws IOException {
        return input
                .replaceAll("--" + context.toUpperCase() + "-LIN--",
                        new String(Files.readAllBytes(
                                PathUtil.getFromTemplate("engines/" + context + "/" + engine + "-LIN")),
                                StandardCharsets.UTF_8))
                .replaceAll("--" + context.toUpperCase() + "-WIN--",
                        new String(Files.readAllBytes(
                                PathUtil.getFromTemplate("engines/" + context + "/" + engine + "-WIN")),
                                StandardCharsets.UTF_8));
    }
}
