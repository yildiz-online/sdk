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
package be.yildizgames.sdk.feature.project.properties.ui.items;

import be.yildizgames.common.gameobject.Movable;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowTextLine;

import java.util.Optional;

public class PositionItem {

    private Movable movable;

    private final WindowInputBox posX;
    private final WindowInputBox posY;
    private final WindowInputBox posZ;

    public PositionItem(WindowShell parent, WindowTextLine label) {
        super();
        this.posX = this.createPos(parent, label, 0, "X value");
        this.posY = this.createPos(parent, label, 1, "Y value");
        this.posZ = this.createPos(parent, label, 2, "Z value");
    }

    private WindowInputBox createPos(WindowShell parent, WindowTextLine label, int i, String tooltip) {
        WindowInputBox pos = parent.createInputBox();
        pos.setCoordinates(new Coordinates(60,20, label.getLeft() + 150 + i * 70, label.getTop()));
        pos.setToolTip(tooltip);
        pos.onChange(() -> Optional.ofNullable(movable).ifPresent(
                m -> m.setPosition(checkInput(posX), checkInput(posY), checkInput(posZ))));
        return pos;
    }

    private static float checkInput(WindowInputBox t) {
        try {
            return Float.valueOf(t.getText());
        } catch (NumberFormatException e) {
            t.setText("0");
            return 0f;
        }
    }

    private void setValues(Point3D v) {
        this.posX.setText(String.valueOf(v.x));
        this.posY.setText(String.valueOf(v.y));
        this.posZ.setText(String.valueOf(v.z));
    }

    public void setMovable(Movable movable) {
        this.movable = movable;
        this.setValues(movable.getPosition());
    }
}
