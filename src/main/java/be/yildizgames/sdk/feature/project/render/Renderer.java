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
package be.yildizgames.sdk.feature.project.render;

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.common.shape.Box;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.graphic.GraphicObjectBuilder;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.RayProvider;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.graphic.ogre.OgreGraphicEngine;
import be.yildizgames.module.graphic.particle.ParticleEmitter;
import be.yildizgames.module.graphic.particle.ParticleSystem;
import be.yildizgames.module.window.input.MousePosition;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.swt.SwtWindowEngine;
import be.yildizgames.module.window.swt.widget.SwtWindowShell;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.items.BoxDefinition;
import be.yildizgames.sdk.feature.project.model.items.ParticleEmitterDef;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;
import be.yildizgames.sdk.feature.project.model.items.Scene;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
public class Renderer implements ProjectListener {

    private SwtWindowEngine windowEngine;

    private OgreGraphicEngine graphicEngine;

    private boolean run = false;

    private final Map<String, GraphicWorld> worlds = new HashMap<>();
    private Scene currentScene;

    public Renderer() {
        super();
    }

    public void init(WindowShell window) {
        this.windowEngine = new SwtWindowEngine((SwtWindowShell)window, new Coordinates(new Size(window.getSize().width - 150,window.getSize().height), new Position(150,0)));
        this.windowEngine.showCursor();
        this.graphicEngine = new OgreGraphicEngine(windowEngine, NativeResourceLoader.inJar());
        this.windowEngine.registerInput(new WindowInputListener() {

            @Override
            public void mouseLeftClick(MousePosition position) {
                Optional.ofNullable(worlds.get(currentScene.getName())).ifPresent(
                        w -> {
                            RayProvider rp = w.getDefaultCamera();
                            //Optional<EntityId> id = w.createQuery(rp).getEntity(position);
                            Point3D p = w.createGroundQuery(rp).getPoint(position);
                            System.out.println(p);
                            //id.ifPresent(System.out::println);
                        });

            }
        });

    }

    public void run() {
        this.run = true;
        while(run) {
            windowEngine.update();
            graphicEngine.update();
            //s.rotate(1,10);
        }
    }

    public void createWorld() {
        GraphicWorld w = this.graphicEngine.createWorld();
        w.getDefaultCamera().setPosition(0,100,100);
        w.getDefaultCamera().setDirection(0,-100,-100);
        this.worlds.put(w.getName(), w);
    }

    public void createParticleSystem(Scene scene, ParticleSystemDefinition def) {
        //TODO Add definition to graphic API
        this.createParticleSystem(this.worlds.get(scene.getName()), def);
    }

    public void createBox(Scene scene, BoxDefinition def) {
        //TODO Add definition to graphic API
        this.createBox(this.worlds.get(scene.getName()), def);
    }

    private void createBox(GraphicWorld w, BoxDefinition def) {
        ImplementationException.throwForNull(w);
        ImplementationException.throwForNull(def);
        GraphicObjectBuilder builder = w.createObject()
                .withShape(Box.box((int)def.getSize().x, (int)def.getSize().y, (int)def.getSize().z))
                .atPosition(def.getPosition())
                .withId(def.getId())
                .withMaterial(Material.get(def.getMaterial()));
        if(def.isStaticObject()) {
            builder.buildStatic();
        } else {
            builder.buildMovable();
        }
    }

    private void createParticleSystem(GraphicWorld w, ParticleSystemDefinition def) {
        ParticleSystem s = w.createParticleSystem();
        s.setPosition(def.getPosition());
        s.setQuota(def.getQuota());
        s.setSize(def.getSize());
        s.setMaterial(Material.get(def.getMaterial()));
        for(ParticleEmitterDef ped : def.getEmitters()) {
            ParticleEmitter e = s.addEmitter(ped.getType());
            e.setMinSpeed(ped.getMinSpeed());
            e.setMaxSpeed(ped.getMaxSpeed());
            e.setLifeTime(ped.getLifeTime());
            e.setRate(ped.getRate());
        }
        s.start();
    }

    private void materialize(Scene scene) {
        this.createWorld();
        for(ParticleSystemDefinition d: scene.getParticles()) {
            createParticleSystem(scene, d);
        }
        for(BoxDefinition d:scene.getModels().getBoxes()){
            createBox(scene, d);
        }
    }

    @Override
    public void onLoad(Project p) {
        this.materialize(p.scene);
        this.currentScene = p.scene;
    }

    @Override
    public void onUpdate(ParticleSystemDefinition definition) {
        this.createParticleSystem(this.currentScene, definition);
    }

    @Override
    public void onUpdate(BoxDefinition definition) {
        this.createBox(this.currentScene, definition);
    }
}
