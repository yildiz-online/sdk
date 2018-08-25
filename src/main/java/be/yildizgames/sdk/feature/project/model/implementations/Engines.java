package be.yildizgames.sdk.feature.project.model.implementations;

public class Engines {

    public final Audio audio;

    public final Graphic graphic;

    public final Network network;

    public final Physics physics;

    public final Scripting scripting;

    public final Window window;

    public Engines(Audio audio, Graphic graphic, Network network, Physics physics, Scripting scripting, Window window) {
        super();
        this.audio = audio;
        this.graphic = graphic;
        this.network = network;
        this.physics = physics;
        this.scripting = scripting;
        this.window = window;
    }

    public static Engines defaultAll() {
        return new Engines(Audio.OPENAL, Graphic.OGRE3D, Network.NETTYIO, Physics.BULLET, Scripting.RUBY, Window.SWT);
    }

    public static Engines defaultNoNetwork() {
        return new Engines(Audio.OPENAL, Graphic.OGRE3D, Network.NONE, Physics.BULLET, Scripting.RUBY, Window.SWT);
    }
}
