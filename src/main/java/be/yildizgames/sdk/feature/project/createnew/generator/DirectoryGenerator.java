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
