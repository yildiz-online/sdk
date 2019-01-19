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

import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.TreeElement;
import be.yildizgames.module.window.swt.TreeRoot;
import be.yildizgames.sdk.CountIncrement;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.items.BoxDefinition;
import be.yildizgames.sdk.feature.project.model.items.Definition;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;
import org.eclipse.swt.widgets.TreeItem;

import java.util.List;

public class ProjectTree implements ProjectListener {

    private static final int MODEL_ID = 2;

    private static final int PARTICLE_ID = 4;

    private final SwtWindow parent;

    private TreeRoot tree;

    public ProjectTree(SwtWindow parent) {
        this.parent = parent;
        this.tree = parent.createTree(150, parent.getHeight());
    }

    @Override
    public void onLoad(Project p) {
        this.tree.delete();

        TreeElement[] particles = generate(50, p.scene.getParticles());
        TreeElement[] boxes = generate(150, p.scene.getModels().getBoxes());

        this.tree = parent.createTree(150, parent.getHeight(),
                new TreeElement(0,"Project",
                        new TreeElement(1,"Scene",
                                new TreeElement(MODEL_ID,"Models", boxes),
                                new TreeElement(3,"Lights"),
                                new TreeElement(PARTICLE_ID,"Particles", particles),
                                new TreeElement(5,"Cameras")),
                        new TreeElement(6,"Materials"),
                        new TreeElement(7,"Audios",
                                new TreeElement(8,"SFX"),
                                new TreeElement(9,"Musics")),
                        new TreeElement(10,"UI")));
    }

    private TreeElement[] generate(int baseId, List<? extends Definition> definitions) {
        CountIncrement counter = new CountIncrement(baseId);
        return definitions
                .stream()
                .map(pdef -> new TreeElement(counter.getAndIncrement(), pdef.getName()))
                .toArray(TreeElement[]::new);
    }


    @Override
    public void onUpdate(ParticleSystemDefinition definition) {
        TreeItem particle = tree.getElement(PARTICLE_ID);
        TreeItem item = new TreeItem(particle, 0);
        item.setText(definition.getName());
        particle.setExpanded(true);
    }

    @Override
    public void onUpdate(BoxDefinition definition) {
        TreeItem mesh = tree.getElement(MODEL_ID);
        TreeItem item = new TreeItem(mesh, 0);
        item.setText(definition.getName());
        mesh.setExpanded(true);
    }
}
