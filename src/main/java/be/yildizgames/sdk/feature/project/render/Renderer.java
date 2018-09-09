package be.yildizgames.sdk.feature.project.render;

import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.graphic.ogre.OgreGraphicEngine;
import be.yildizgames.module.graphic.particle.ParticleEmitter;
import be.yildizgames.module.graphic.particle.ParticleSystem;
import be.yildizgames.module.window.input.MousePosition;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.SwtWindowEngine;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.items.ParticleEmitterDef;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;
import be.yildizgames.sdk.feature.project.model.items.Scene;

import java.util.HashMap;
import java.util.Map;

public class Renderer implements ProjectListener {

    private SwtWindowEngine windowEngine;

    private OgreGraphicEngine graphicEngine;

    private boolean run = false;

    private final Map<String, GraphicWorld> worlds = new HashMap<>();
    private Scene currentScene;

    public Renderer() {
        super();
    }

    public void init(SwtWindow window) {
        this.windowEngine = new SwtWindowEngine(window, new Coordinates(new Size(window.getWidth() - 150,window.getHeight()), new Position(150,0)));
        this.windowEngine.deleteLoadingResources();
        this.windowEngine.showCursor();
        window.onClose(e -> run = false);
        this.graphicEngine = new OgreGraphicEngine(windowEngine, NativeResourceLoader.inJar());
       /* ParticleSystemDefinition def = new ParticleSystemDefinition();
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

    }

    public void run() {
        this.run = true;
        while(run) {
            windowEngine.updateWindow();
            graphicEngine.update();
            //s.rotate(1,10);
        }
    }

    public void createWorld() {
        GraphicWorld w = this.graphicEngine.createWorld();
        this.worlds.put(w.getName(), w);
    }

    public void createParticleSystem(Scene scene, ParticleSystemDefinition def) {
        //TODO Add definition to graphic API
        this.createParticleSystem(this.worlds.get(scene.getName()), def);
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
}
