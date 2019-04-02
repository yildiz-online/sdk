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
package be.yildizgames.sdk.feature.project.createnew.ui;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.window.widget.WindowWidget;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowTextLine;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.createnew.generator.GeneratorHandler;
import be.yildizgames.sdk.feature.project.createnew.util.PathUtil;
import be.yildizgames.sdk.feature.project.model.Author;
import be.yildizgames.sdk.feature.project.model.AuthorValidationException;
import be.yildizgames.sdk.feature.project.model.GroupId;
import be.yildizgames.sdk.feature.project.model.GroupValidationException;
import be.yildizgames.sdk.feature.project.model.Licence;
import be.yildizgames.sdk.feature.project.model.Name;
import be.yildizgames.sdk.feature.project.model.NameValidationException;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.implementations.Engines;
import be.yildizgames.sdk.feature.project.model.items.Scene;
import be.yildizgames.sdk.feature.project.save.formatter.ObjectToJson;
import be.yildizgames.sdk.feature.project.save.persistence.ToFile;
import be.yildizgames.sdk.ui.translation.SdkTranslation;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class ProjectCreationWindow {

    private final List<ProjectListener> listeners;
    private final SdkTranslation translation;
    private WindowShell window;

    private final WindowShell parent;

    public ProjectCreationWindow(WindowShell parent, List<ProjectListener> l, SdkTranslation translation) {
        this.parent = parent;
        this.listeners = l;
        this.translation = translation;
    }

    public void init(Configuration configuration) {
        this.window = this.parent.createChildWindow();
        this.window.setTitle(translation.createProject());
        this.window.setBackground(Color.rgb(50,50,50));
        this.window.open();

        InputTextEntry name = new InputTextEntry(this.window, 0, translation.createProjectName());
        InputTextEntry author = new InputTextEntry(this.window, 1, translation.createProjectAuthor());
        InputTextEntry group = new InputTextEntry(this.window, 2, translation.createProjectGroup());

        InputComboEntry licence = new InputComboEntry(this.window, 3, translation.createProjectLicence());

        WindowButtonText b = this.window.createTextButton();
        b.setCoordinates(new Coordinates(150,50, this.window.getScreenSize().width - 200,this.window.getScreenSize().height - 100));
        b.setText(translation.create());
        b.addMouseLeftClickListener(() -> {
                 name.error.setText("");
                author.error.setText("");
                group.error.setText("");
                try {
                    Project p = new Project(
                            new Name(name.input.getText()),
                            new Author(author.input.getText()),
                            new GroupId(group.input.getText()),
                            getFromIndex(licence.input.getSelectionIndex()),
                            Engines.defaultNoNetwork(),
                            new Scene("sc"));
                    GeneratorHandler
                            .forProject(p, configuration)
                            .run();
                    listeners.forEach(l -> l.onLoad(p));
                    ToFile.save(PathUtil.getRoot(p, configuration), ObjectToJson.fromProject(p));
                    window.close();
                } catch (NameValidationException e) {
                    name.error.setText(e.getMessage());
                } catch (AuthorValidationException e) {
                    author.error.setText(e.getMessage());
                } catch (GroupValidationException e) {
                    group.error.setText(e.getMessage());
                }
            }
        );
        this.window.open();
    }

    private static Licence getFromIndex(int index) {
        return Licence.values()[index];
    }

    private static abstract class InputEntry<T extends WindowWidget> {

        final WindowTextLine label;

        final T input;

        final WindowTextLine error;

        private InputEntry(WindowShell parent, int position, String txt) {
            super();
            this.label = generateLabel(parent, position, txt);
            this.input = generateInput(parent, label);
            this.error = generateErrorLabel(parent, input);
        }

        private WindowTextLine generateLabel(WindowShell parent, int position, String txt) {
            WindowTextLine result = parent.createTextLine();
            result.setText(txt);
            result.setPosition(50, 100 + position * 50);
            //result.setSize(80, 20);
            return result;
        }

        protected abstract T generateInput(WindowShell parent, WindowWidget source);

        private WindowTextLine generateErrorLabel(WindowShell parent, WindowWidget source) {
            WindowTextLine result = parent.createTextLine();
            result.setPosition(source.getRight() + 50, source.getTop());
            //result.setSize(250, 20);
            return result;
        }
    }

    private static final class InputTextEntry extends InputEntry<WindowInputBox> {

        private InputTextEntry(WindowShell parent, int position, String label) {
            super(parent, position, label);
        }

        protected WindowInputBox generateInput(WindowShell parent, WindowWidget source) {
            WindowInputBox result = parent.createInputBox();
            result.setCoordinates(new Coordinates(250,20, source.getRight() + 50, source.getTop()));
            return result;
        }
    }

    private static final class InputComboEntry extends InputEntry<WindowDropdown> {

        private InputComboEntry(WindowShell parent, int position, String label) {
            super(parent, position, label);
        }

        protected WindowDropdown generateInput(WindowShell parent, WindowWidget source) {
            WindowDropdown licence = parent.createDropdown();
            licence.setItems(Licence.values());
            licence.setCoordinates(new Coordinates(250,20, source.getRight() + 50, source.getTop()));
            return licence;
        }
    }

}
