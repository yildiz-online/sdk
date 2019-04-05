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
package be.yildizgames.sdk.feature.project.tree;

import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import be.yildizgames.sdk.CountIncrement;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.items.BoxDefinition;
import be.yildizgames.sdk.feature.project.model.items.Definition;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;

import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class ProjectTree implements ProjectListener {

    private static final int MODEL_ID = 2;

    private static final int PARTICLE_ID = 4;

    private final WindowShell parent;

    private WindowTreeRoot tree;

    public ProjectTree(WindowShell parent) {
        this.parent = parent;
        this.tree = parent.createTreeRoot(150, parent.getSize().height);
    }

    @Override
    public void onLoad(Project p) {
        this.tree.delete();

        WindowTreeElement[] particles = generate(50, p.scene.getParticles());
        WindowTreeElement[] boxes = generate(150, p.scene.getModels().getBoxes());

        this.tree = parent.createTreeRoot(150, parent.getSize().height,
                new WindowTreeElement(0,"Project",
                        new WindowTreeElement(1,"Scene",
                                new WindowTreeElement(MODEL_ID,"Models", boxes),
                                new WindowTreeElement(3,"Lights"),
                                new WindowTreeElement(PARTICLE_ID,"Particles", particles),
                                new WindowTreeElement(5,"Cameras")),
                        new WindowTreeElement(6,"Materials"),
                        new WindowTreeElement(7,"Audios",
                                new WindowTreeElement(8,"SFX"),
                                new WindowTreeElement(9,"Musics")),
                        new WindowTreeElement(10,"UI")));
    }

    private WindowTreeElement[] generate(int baseId, List<? extends Definition> definitions) {
        CountIncrement counter = new CountIncrement(baseId);
        return definitions
                .stream()
                .map(pdef -> new WindowTreeElement(counter.getAndIncrement(), pdef.getName()))
                .toArray(WindowTreeElement[]::new);
    }


    @Override
    public void onUpdate(ParticleSystemDefinition definition) {
        /*TreeItem particle = tree.getElement(PARTICLE_ID);
        TreeItem item = new TreeItem(particle, 0);
        item.setText(definition.getName());
        particle.setExpanded(true);*/
    }

    @Override
    public void onUpdate(BoxDefinition definition) {
        /*TreeItem mesh = tree.getElement(1);
        TreeItem item = new TreeItem(mesh, 0);
        item.setText(definition.getName());
        mesh.setExpanded(true);*/
    }
}
