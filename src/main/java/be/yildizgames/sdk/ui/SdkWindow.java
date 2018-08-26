package be.yildizgames.sdk.ui;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.createnew.ui.ProjectCreationWindow;
import be.yildizgames.sdk.feature.project.load.ui.ProjectLoadWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class SdkWindow {

    public void init(Configuration configuration) {
        SwtWindow window = new SwtWindow(new Shell());
        window.setWindowTitle("Yildiz-Engine SDK");
        window.setBackground(Color.rgb(50,50,50));
        window.show();

        Menu menu = window.createMenuBar();
        MenuItem fileMenuHeader = new MenuItem(menu, SWT.CASCADE);
        fileMenuHeader.setText("&File");
        Menu m = window.createSubMenu(fileMenuHeader);
        MenuItem newProject = new MenuItem(m, SWT.PUSH);
        newProject.setText("&New project");
        newProject.addSelectionListener(
                new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        new ProjectCreationWindow(window).init(configuration);
                    }
                }
        );
        MenuItem loadProject = new MenuItem(m, SWT.PUSH);
        loadProject.setText("&Load project");
        loadProject.addSelectionListener(
                new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        new ProjectLoadWindow(window).init(configuration);
                    }
                }
        );
        window.run();
    }
}
