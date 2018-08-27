package be.yildizgames.sdk.feature.project.save.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ToFile {

    private ToFile() {
        super();
    }

    public static void save(Path path, String content) {
        try {
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
