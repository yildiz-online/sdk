package be.yildizgames.sdk.feature.project.render;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.geometry.Size2;
import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.graphic.ogre.OgreGraphicEngine;
import be.yildizgames.module.graphic.particle.ParticleEmitter;
import be.yildizgames.module.graphic.particle.ParticleSystem;
import be.yildizgames.module.window.input.MousePosition;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.SwtWindowEngine;
import be.yildizgames.sdk.feature.project.model.items.ParticleEmitterDef;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;

import java.util.HashMap;
import java.util.Map;

public class Renderer {

    private SwtWindowEngine windowEngine;

    private OgreGraphicEngine graphicEngine;

    private boolean run = false;

    private final Map<String, GraphicWorld> worlds = new HashMap<>();

    public void init(SwtWindow window) {
        this.run = true;
        this.windowEngine = new SwtWindowEngine(window, false);
        this.windowEngine.deleteLoadingResources();
        this.windowEngine.showCursor();
        window.onClose((e) -> run = false);
       /* this.graphicEngine = new OgreGraphicEngine(windowEngine, NativeResourceLoader.inJar());
        this.createWorld();
        ParticleSystemDefinition def = new ParticleSystemDefinition();
        def.setMaterial(Material.blue().getName());
        def.setPosition(Point3D.valueOf(0,0,-100));
        def.setSize(Size2.valueOf(2, 2));
        ParticleEmitterDef edef = new ParticleEmitterDef(ParticleEmitter.EmitterType.POINT, 100, 100, 10000, 70);
        def.addEmitter(edef);
        createParticleSystem(def);*/
        this.windowEngine.registerInput(new WindowInputListener() {
            @Override
            public void mouseLeftClick(MousePosition position) {
            }
        });
        while(run) {
            windowEngine.updateWindow();
           // graphicEngine.update();
            //s.rotate(1,10);
        }
    }

    public void createWorld() {
        GraphicWorld w = this.graphicEngine.createWorld();
        this.worlds.put(w.getName(), w);
    }

    public void createParticleSystem(ParticleSystemDefinition def) {
        //TODO Add definition to graphic API
        ParticleSystem s = this.worlds.get("sc").createParticleSystem();
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

    public void stop() {
        this.run = false;
    }
}
