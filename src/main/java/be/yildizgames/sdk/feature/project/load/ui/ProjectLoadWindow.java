/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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
package be.yildizgames.sdk.feature.project.load.ui;

import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.load.formatter.JsonToObject;
import be.yildizgames.sdk.feature.project.load.persistence.FromFile;
import org.eclipse.swt.widgets.FileDialog;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ProjectLoadWindow {

    private final SwtWindow parent;

    private final List<ProjectListener> listeners;

    public ProjectLoadWindow(SwtWindow parent, List<ProjectListener> l) {
        super();
        this.parent = parent;
        this.listeners = l;
    }

    public void init(Configuration configuration) {
        FileDialog fd = this.parent.createOpenFileDialog("Open");
        fd.setFilterPath(configuration.rootPath);
        fd.setFilterExtensions(new String[] { "*.yzf" });
        String selected = fd.open();
        Optional.ofNullable(selected).ifPresent(s ->
        this.listeners.forEach(l -> l.onLoad(JsonToObject.toProject(FromFile.load(Paths.get(s))))));
    }
}
