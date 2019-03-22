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
import be.yildizgames.module.window.swt.SwtWindow;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import java.util.List;

public class ProjectCreationWindow {

    private final List<ProjectListener> listeners;
    private final SdkTranslation translation;
    private SwtWindow window;

    private final SwtWindow parent;

    public ProjectCreationWindow(SwtWindow parent, List<ProjectListener> l, SdkTranslation translation) {
        this.parent = parent;
        this.listeners = l;
        this.translation = translation;
    }

    public void init(Configuration configuration) {
        this.window = new SwtWindow(this.parent);
        this.window.setWindowTitle(translation.createProject());
        this.window.setBackground(Color.rgb(50,50,50));
        this.window.show();

        InputTextEntry name = new InputTextEntry(this.window, 0, translation.createProjectName());
        InputTextEntry author = new InputTextEntry(this.window, 1, translation.createProjectAuthor());
        InputTextEntry group = new InputTextEntry(this.window, 2, translation.createProjectGroup());

        InputComboEntry licence = new InputComboEntry(this.window, 3, translation.createProjectLicence());

        Button b = this.window.createButton();
        b.setSize(150,50);
        b.setLocation(this.window.getWidth() - 200,this.window.getHeight() - 100);
        b.setText(translation.create());
        b.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
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
                    window.hide();
                } catch (NameValidationException e) {
                    name.error.setText(e.getMessage());
                } catch (AuthorValidationException e) {
                    author.error.setText(e.getMessage());
                } catch (GroupValidationException e) {
                    group.error.setText(e.getMessage());
                }
            }
        });
        this.window.run();
    }

    private static Licence getFromIndex(int index) {
        return Licence.values()[index];
    }

    private static abstract class InputEntry<T extends Control> {

        final Label label;

        final T input;

        final Label error;

        private InputEntry(SwtWindow parent, int position, String txt) {
            super();
            this.label = generateLabel(parent, position, txt);
            this.input = generateInput(parent, label);
            this.error = generateErrorLabel(parent, input);
        }

        private Label generateLabel(SwtWindow parent, int position, String txt) {
            Label result = parent.createTextLine();
            result.setText(txt);
            result.setLocation(50, 100 + position * 50);
            result.setSize(80, 20);
            return result;
        }

        protected abstract T generateInput(SwtWindow parent, Control source);

        private Label generateErrorLabel(SwtWindow parent, Control source) {
            Label result = parent.createTextLine();
            result.setLocation(source.getLocation().x + source.getSize().x + 50, source.getLocation().y);
            result.setSize(250, 20);
            return result;
        }
    }

    private static final class InputTextEntry extends InputEntry<Text> {

        private InputTextEntry(SwtWindow parent, int position, String label) {
            super(parent, position, label);
        }

        protected Text generateInput(SwtWindow parent, Control source) {
            Text result = parent.createInputBox();
            result.setLocation(source.getLocation().x + source.getSize().x + 50, source.getLocation().y);
            result.setSize(250,20);
            return result;
        }
    }

    private static final class InputComboEntry extends InputEntry<Combo> {

        private InputComboEntry(SwtWindow parent, int position, String label) {
            super(parent, position, label);
        }

        protected Combo generateInput(SwtWindow parent, Control source) {
            Combo licence = parent.createDropdown(Licence.values());
            licence.setLocation(source.getLocation().x + source.getSize().x + 50,source.getLocation().y);
            licence.setSize(250,20);
            return licence;
        }
    }

}
