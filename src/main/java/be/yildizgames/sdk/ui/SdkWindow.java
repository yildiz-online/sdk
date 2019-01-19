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
package be.yildizgames.sdk.ui;

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.swt.MenuBarElement;
import be.yildizgames.module.window.swt.MenuElement;
import be.yildizgames.module.window.swt.SwtMenuBar;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.createnew.ui.ProjectCreationWindow;
import be.yildizgames.sdk.feature.project.load.ui.ProjectLoadWindow;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.items.BoxDefinition;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;
import be.yildizgames.sdk.feature.project.properties.ui.ParticleObjectView;
import be.yildizgames.sdk.feature.project.render.Renderer;
import be.yildizgames.sdk.feature.project.save.business.Save;
import be.yildizgames.sdk.feature.project.tree.ProjectTree;

import java.util.List;

public class SdkWindow implements ProjectListener {


    private Save save;

    private Renderer renderer;
    private SwtMenuBar bar;

    public SdkWindow() {
        super();
    }


    public void init(Configuration configuration) {
        SwtWindow window = new SwtWindow();
        window.setWindowTitle("Yildiz-Engine SDK");
        window.setBackground(Color.rgb(50));
        window.show();
        ProjectTree t = this.generateTreeView(window);
        this.renderer = generateMainView(window);
        this.save = new Save();
        List<ProjectListener> listeners = List.of(this, renderer, t, save);
        this.generateMenus(window, listeners, configuration);

        //this.generateObjectData(window);
        renderer.run();
    }

    private Renderer generateMainView(SwtWindow window) {
        Renderer renderer = new Renderer();
        renderer.init(window);
        return renderer;
    }

    private ProjectTree generateTreeView(SwtWindow window) {
        return new ProjectTree(window);
    }

    private void generateMenus(SwtWindow parent, List<ProjectListener> l, Configuration configuration) {

        this.bar = parent.createMenuBar(
                new MenuBarElement("File",
                        new MenuElement(1,"New", e ->  new ProjectCreationWindow(parent, l).init(configuration)),
                        new MenuElement(2,"Open", e -> new ProjectLoadWindow(parent, l).init(configuration)),
                        new MenuElement(3,"Save", e -> this.save.save(configuration))),
                new MenuBarElement("Create",
                        new MenuElement(4,"Particle system", e -> l.forEach(pl -> pl.onUpdate(new ParticleSystemDefinition()))),
                        new MenuElement(5,"Box", e -> l.forEach(pl -> pl.onUpdate(new BoxDefinition())))
                )
        );

        this.bar.getItemById(3).ifPresent(i -> i.setEnabled(false));
        this.bar.getItemById(4).ifPresent(i -> i.setEnabled(false));
        this.bar.getItemById(5).ifPresent(i -> i.setEnabled(false));
    }

    private void generateObjectData(SwtWindow parent) {
        ParticleObjectView particleObjectView = new ParticleObjectView(parent);
    }

    @Override
    public void onLoad(Project p) {
        this.bar.getItemById(3).ifPresent(i -> i.setEnabled(true));
        this.bar.getItemById(4).ifPresent(i -> i.setEnabled(true));
        this.bar.getItemById(5).ifPresent(i -> i.setEnabled(true));
    }

    @Override
    public void onUpdate(ParticleSystemDefinition definition) {
        ImplementationException.notYetImplemented();
    }

    @Override
    public void onUpdate(BoxDefinition definition) {
        ImplementationException.notYetImplemented();
    }
}
