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
package be.yildizgames.sdk.ui;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.swt.MenuBarElement;
import be.yildizgames.module.window.swt.MenuElement;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.TreeElement;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.createnew.ui.ProjectCreationWindow;
import be.yildizgames.sdk.feature.project.createnew.util.PathUtil;
import be.yildizgames.sdk.feature.project.load.ui.ProjectLoadWindow;
import be.yildizgames.sdk.feature.project.model.ProjectManager;
import be.yildizgames.sdk.feature.project.properties.ui.ParticleObjectView;
import be.yildizgames.sdk.feature.project.render.Renderer;
import be.yildizgames.sdk.feature.project.save.formatter.ObjectToJson;
import be.yildizgames.sdk.feature.project.save.persistence.ToFile;

public class SdkWindow {


    public SdkWindow() {
        super();
    }


    public void init(Configuration configuration) {
        ProjectManager projectManager = new ProjectManager();
        SwtWindow window = new SwtWindow();
        window.setWindowTitle("Yildiz-Engine SDK");
        window.setBackground(Color.rgb(50,50,50));
        window.show();
        this.generateMenus(window, projectManager, configuration);
        this.generateProjectTree(window);
        this.generateObjectData(window);
        this.generateMainView(window);
    }

    private void generateMainView(SwtWindow window) {
        Renderer renderer = new Renderer();
        renderer.init(window);
    }

    private void generateMenus(SwtWindow parent, ProjectManager projectManager, Configuration configuration) {
        parent.createMenuBar(
                new MenuBarElement("File",
                        new MenuElement("New", e ->  new ProjectCreationWindow(parent, projectManager).init(configuration)),
                        new MenuElement( "Open", e -> new ProjectLoadWindow(parent).init(configuration)),
                        new MenuElement("Save", e -> projectManager.getProject().ifPresent(p -> ToFile.save(PathUtil.getRoot(p, configuration), ObjectToJson.fromProject(p))))));
    }

    private void generateProjectTree(SwtWindow parent) {
        parent.createTree(150, parent.getHeight(),
                new TreeElement("Project",
                        new TreeElement("Scene",
                                new TreeElement("Models"),
                                new TreeElement("Lights"),
                                new TreeElement("Particles"),
                                new TreeElement("Cameras")),
                        new TreeElement("Materials"),
                        new TreeElement("Audios",
                                new TreeElement("SFX"),
                                new TreeElement("Musics")),
                        new TreeElement("UI")));
    }

    private void generateObjectData(SwtWindow parent) {
        ParticleObjectView particleObjectView = new ParticleObjectView(parent);
    }

}
