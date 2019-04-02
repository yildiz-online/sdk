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
package be.yildizgames.sdk.feature.project.model.implementations;

/**
 * @author Grégory Van den Borre
 */
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
