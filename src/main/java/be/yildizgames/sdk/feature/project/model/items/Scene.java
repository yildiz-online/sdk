package be.yildizgames.sdk.feature.project.model.items;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final int id;

    private final List<Model> models = new ArrayList<>();

    private final List<Light> lights = new ArrayList<>();

    private final List<ParticleSystem> particles = new ArrayList<>();

    private final List<Camera> cameras = new ArrayList<>();

    public Scene(int id) {
        super();
        this.id = id;
    }
}
