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
package be.yildizgames.sdk.feature.project.properties.ui;

import be.yildizgames.module.graphic.particle.ParticleSystem;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.SwtWindowUtils;
import be.yildizgames.sdk.feature.project.properties.ui.items.PositionItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class ParticleObjectView {

    private final SwtWindow parent;
    private final PositionItem position;

    private ParticleSystem system;

    public ParticleObjectView(SwtWindow parent) {
        super();
        this.parent = parent;
        this.createTitle();
        this.position = new PositionItem(parent, this.createLabel("Position:", 1));
        this.createLabel("Name:", 0);
        this.createLabel("Quota:", 2);
        this.createLabel("Material:", 3);
        this.createLabel("Size:", 4);
    }

    private void createTitle() {
        Label l = this.parent.createLabel("Particle", SwtWindowUtils.ColorValue.WHITE, Display.getCurrent().getSystemFont());
        l.setSize(100,20);
        l.setVisible(true);
        l.setLocation(1055,25);
    }

    private Label createLabel(String value, int pos) {
        Label l = this.parent.createTextLine();
        l.setText(value);
        l.setSize(100,100);
        l.setVisible(true);
        l.setLocation(900,50 + pos * 15);
        return l;
    }

    public void setSystem(ParticleSystem system) {
        this.system = system;
        this.position.setMovable(system);
    }
}
