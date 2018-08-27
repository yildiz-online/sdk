package be.yildizgames.sdk.feature.project.createnew.ui;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.sdk.configuration.Configuration;
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
import be.yildizgames.sdk.feature.project.model.ProjectManager;
import be.yildizgames.sdk.feature.project.model.implementations.Engines;
import be.yildizgames.sdk.feature.project.model.items.Scene;
import be.yildizgames.sdk.feature.project.save.formatter.ObjectToJson;
import be.yildizgames.sdk.feature.project.save.persistence.ToFile;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ProjectCreationWindow {

    private final ProjectManager projectManager;
    private SwtWindow window;

    private final SwtWindow parent;

    public ProjectCreationWindow(SwtWindow parent, ProjectManager projectManager) {
        this.parent = parent;
        this.projectManager = projectManager;
    }

    public void init(Configuration configuration) {
        this.window = new SwtWindow(new Shell(this.parent.getShell()));
        this.window.setWindowTitle("Create new project");
        this.window.setBackground(Color.rgb(50,50,50));
        this.window.show();

        InputTextEntry name = new InputTextEntry(this.window, 0, "Project name");
        InputTextEntry author = new InputTextEntry(this.window, 1, "Project author");
        InputTextEntry group = new InputTextEntry(this.window, 2, "Project group");

        InputComboEntry licence = new InputComboEntry(this.window, 3, "Project licence");

        Button b = this.window.createButton();
        b.setSize(150,50);
        b.setLocation(this.window.getWidth() - 200,this.window.getHeight() - 100);
        b.setText("Create");
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
                            new Scene(1));
                    GeneratorHandler
                            .forProject(p, configuration)
                            .run();
                    projectManager.setProject(p);
                    ToFile.save(PathUtil.getRoot(p, configuration), ObjectToJson.fromProject(p));
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

    public void hide() {
        this.window.hide();
    }

    public void show() {
        this.window.show();
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
            Label label = parent.createTextLine();
            label.setText(txt);
            label.setLocation(50, 100 + position * 50);
            label.setSize(80, 20);
            return label;
        }

        protected abstract T generateInput(SwtWindow parent, Control source);

        private Label generateErrorLabel(SwtWindow parent, Control source) {
            Label error = parent.createTextLine();
            error.setLocation(source.getLocation().x + source.getSize().x + 50, source.getLocation().y);
            error.setSize(250, 20);
            return error;
        }
    }

    private static final class InputTextEntry extends InputEntry<Text> {

        private InputTextEntry(SwtWindow parent, int position, String label) {
            super(parent, position, label);
        }

        protected Text generateInput(SwtWindow parent, Control source) {
            Text t = parent.createInputBox();
            t.setLocation(source.getLocation().x + source.getSize().x + 50, source.getLocation().y);
            t.setSize(250,20);
            return t;
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
