package be.yildizgames.sdk.ui;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.createnew.ui.ProjectCreationWindow;
import be.yildizgames.sdk.feature.project.createnew.util.PathUtil;
import be.yildizgames.sdk.feature.project.load.ui.ProjectLoadWindow;
import be.yildizgames.sdk.feature.project.model.ProjectManager;
import be.yildizgames.sdk.feature.project.save.formatter.ObjectToJson;
import be.yildizgames.sdk.feature.project.save.persistence.ToFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class SdkWindow {

    public void init(Configuration configuration) {
        ProjectManager projectManager = new ProjectManager();
        SwtWindow window = new SwtWindow(new Shell());
        window.setWindowTitle("Yildiz-Engine SDK");
        window.setBackground(Color.rgb(50,50,50));
        window.show();

        Menu menu = window.createMenuBar();
        MenuItem fileMenuHeader = new MenuItem(menu, SWT.CASCADE);
        fileMenuHeader.setText("&File");
        Menu fileMenu = window.createSubMenu(fileMenuHeader);
        MenuItem newProject = new MenuItem(fileMenu, SWT.PUSH);
        newProject.setText("&New project");
        newProject.addSelectionListener(
                new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        new ProjectCreationWindow(window, projectManager).init(configuration);
                    }
                }
        );
        MenuItem loadProject = new MenuItem(fileMenu, SWT.PUSH);
        loadProject.setText("&Load project");
        loadProject.addSelectionListener(
                new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        new ProjectLoadWindow(window).init(configuration);
                    }
                }
        );
        MenuItem saveProject = new MenuItem(fileMenu, SWT.PUSH);
        saveProject.setText("&Save");
        saveProject.addSelectionListener(
                new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        projectManager.getProject().ifPresent(p -> ToFile.save(PathUtil.getRoot(p, configuration), ObjectToJson.fromProject(p)));
                    }
                }
        );
        MenuItem saveasProject = new MenuItem(fileMenu, SWT.PUSH);
        saveasProject.setText("&Save as");
        this.generateProjectTree(window);
        window.run();
    }

    private void generateProjectTree(SwtWindow parent) {
        final Tree tree = parent.createTree();
        tree.setSize(290, 290);
        TreeItem scene = new TreeItem(tree, 0);
        scene.setText("Scene");
        TreeItem model = new TreeItem(scene, 0);
        model.setText("Models");
        TreeItem light = new TreeItem(scene, 0);
        light.setText("Lights");
        TreeItem particle = new TreeItem(scene, 0);
        particle.setText("Particles");
        TreeItem camera = new TreeItem(scene, 0);
        camera.setText("Cameras");
        TreeItem material = new TreeItem(tree, 0);
        material.setText("Materials");
        TreeItem audio = new TreeItem(tree, 0);
        audio.setText("Audios");
        TreeItem ui = new TreeItem(tree, 0);
        ui.setText("UI");
    }
}
