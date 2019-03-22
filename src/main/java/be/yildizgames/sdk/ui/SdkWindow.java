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
import be.yildizgames.common.geometry.Point3D;
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
import be.yildizgames.sdk.feature.project.model.items.Material;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;
import be.yildizgames.sdk.feature.project.properties.ui.ParticleObjectView;
import be.yildizgames.sdk.feature.project.render.Renderer;
import be.yildizgames.sdk.feature.project.save.business.Save;
import be.yildizgames.sdk.feature.project.tree.ProjectTree;
import be.yildizgames.sdk.ui.translation.SdkTranslation;

import java.util.List;

public class SdkWindow implements ProjectListener {

    private static final int CREATE_BOX = 5;

    private static final int CREATE_PARTICLE_SYSTEM = 4;

    private static final int SAVE = 3;


    private final Save save;

    private final Renderer renderer;
    private SwtMenuBar bar;
    private final SdkTranslation translation;

    private SdkWindow(Configuration configuration) {
        super();
        SwtWindow window = new SwtWindow();
        this.translation = new SdkTranslation();
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


    public static SdkWindow create(Configuration configuration) {
        return new SdkWindow(configuration);
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
                new MenuBarElement(translation.menuFile(),
                        new MenuElement(1, translation.menuNew(), e ->  new ProjectCreationWindow(parent, l, this.translation).init(configuration)),
                        new MenuElement(2, translation.menuOpen(), e -> new ProjectLoadWindow(parent, l).init(configuration)),
                        new MenuElement(SAVE, translation.menuSave(), e -> this.save.save(configuration))),
                new MenuBarElement(translation.menuCreate(),
                        new MenuElement(CREATE_PARTICLE_SYSTEM, translation.menuParticleSystem(), e -> l.forEach(pl -> pl.onUpdate(new ParticleSystemDefinition()))),
                        new MenuElement(CREATE_BOX,translation.menuBox(), e -> l.forEach(pl -> pl.onUpdate(new BoxDefinition())))
                )
        );

        this.bar.getItemById(SAVE).ifPresent(i -> i.setEnabled(false));
        this.bar.getItemById(CREATE_PARTICLE_SYSTEM).ifPresent(i -> i.setEnabled(false));
        this.bar.getItemById(CREATE_BOX).ifPresent(i -> i.setEnabled(false));
    }

    private void generateObjectData(SwtWindow parent) {
        ParticleObjectView particleObjectView = new ParticleObjectView(parent);
    }

    @Override
    public void onLoad(Project p) {
        this.bar.getItemById(SAVE).ifPresent(i -> i.setEnabled(true));
        this.bar.getItemById(CREATE_PARTICLE_SYSTEM).ifPresent(i -> i.setEnabled(true));
        this.bar.getItemById(CREATE_BOX).ifPresent(i -> i.setEnabled(true));
    }

    @Override
    public void onUpdate(ParticleSystemDefinition definition) {
        ImplementationException.notYetImplemented();
    }

    @Override
    public void onUpdate(BoxDefinition definition) {

    }
}
