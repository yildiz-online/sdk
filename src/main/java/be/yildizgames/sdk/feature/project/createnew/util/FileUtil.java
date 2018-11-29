/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */
package be.yildizgames.sdk.feature.project.createnew.util;

import be.yildizgames.sdk.feature.project.model.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    private FileUtil() {
        super();
    }

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
