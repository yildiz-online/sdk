package be.yildizgames.sdk.feature.project.createnew.ui;

import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.createnew.generator.GeneratorHandler;
import be.yildizgames.sdk.feature.project.model.Author;
import be.yildizgames.sdk.feature.project.model.GroupId;
import be.yildizgames.sdk.feature.project.model.Licence;
import be.yildizgames.sdk.feature.project.model.Name;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.implementations.Engines;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ProjectCreationWindow {

    public void init(Configuration configuration) {
        SwtWindow window = new SwtWindow(new Shell());
        window.setWindowTitle("Create new project");
        window.show();
        Text name = window.createInputBox();
        name.setSize(250,20);
        name.setLocation(100,100);
        Text author = window.createInputBox();
        author.setSize(250,20);
        author.setLocation(100,150);
        Text group = window.createInputBox();
        group.setSize(250,20);
        group.setLocation(100,200);
        Combo licence = window.createDropdown(Licence.values());
        licence.setSize(250,20);
        licence.setLocation(100,250);

        Button b = window.createButton();
        b.setSize(150,50);
        b.setLocation(window.getWidth() - 200,window.getHeight() - 100);
        b.setText("Create");
        b.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Project p = new Project(new Author(author.getText()), new Name(name.getText()), new GroupId(group.getText()), getFromIndex(licence.getSelectionIndex()), Engines.defaultNoNetwork());
                GeneratorHandler
                        .forProject(p, configuration)
                       .run();
            }
        });
        window.run();
    }

    private static Licence getFromIndex(int index) {
        return Licence.values()[index];
    }

}
