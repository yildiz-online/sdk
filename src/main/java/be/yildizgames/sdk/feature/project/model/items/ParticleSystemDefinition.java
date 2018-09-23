/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
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
package be.yildizgames.sdk.feature.project.model.items;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.geometry.Size2;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystemDefinition implements Definition {

    private String name = "Particles";

    private int quota;

    private Point3D position = Point3D.ZERO;

    private Size2 size = Size2.ZERO;

    private final List<ParticleEmitterDef> emitters = new ArrayList<>();

    private String material = "empty";

    public int getQuota() {
        return this.quota;
    }

    public Point3D getPosition() {
        return this.position;
    }

    public Size2 getSize() {
        return this.size;
    }

    public List<ParticleEmitterDef> getEmitters() {
        return this.emitters;
    }

    public String getMaterial() {
        return material;
    }

    public void addEmitter(ParticleEmitterDef d) {
        this.emitters.add(d);
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public void setSize(Size2 size) {
        this.size = size;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
