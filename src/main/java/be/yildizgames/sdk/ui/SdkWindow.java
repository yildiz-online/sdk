package be.yildizgames.sdk.ui;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.swt.MenuBarElement;
import be.yildizgames.module.window.swt.MenuElement;
import be.yildizgames.module.window.swt.SwtGameWindow;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.TreeElement;
import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.createnew.ui.ProjectCreationWindow;
import be.yildizgames.sdk.feature.project.createnew.util.PathUtil;
import be.yildizgames.sdk.feature.project.load.ui.ProjectLoadWindow;
import be.yildizgames.sdk.feature.project.model.ProjectManager;
import be.yildizgames.sdk.feature.project.save.formatter.ObjectToJson;
import be.yildizgames.sdk.feature.project.save.persistence.ToFile;

public class SdkWindow {


    public SdkWindow() {
        super();
    }


    public void init(Configuration configuration) {
        ProjectManager projectManager = new ProjectManager();
        SwtWindow window = new SwtWindow();
        window.setWindowTitle("Yildiz-Engine SDK");
        window.setBackground(Color.rgb(50,50,50));
        window.show();
        this.generateMenus(window, projectManager, configuration);
        this.generateProjectTree(window);
        this.generateMainView(window);
        window.run();
    }

    private void generateMainView(SwtWindow window) {
        SwtGameWindow gameWindow = new SwtGameWindow();
        gameWindow.initialize(window, false);
        gameWindow.showCursor();
    //    gameWindow.deleteLoadingResources();
        gameWindow.getCanvas().setSize(100,100);
    }

    private void generateMenus(SwtWindow parent, ProjectManager projectManager, Configuration configuration) {
        parent.createMenuBar(
                new MenuBarElement("File",
                        new MenuElement("New", e ->  new ProjectCreationWindow(parent, projectManager).init(configuration)),
                        new MenuElement( "Open", e -> new ProjectLoadWindow(parent).init(configuration)),
                        new MenuElement("Save", e -> projectManager.getProject().ifPresent(p -> ToFile.save(PathUtil.getRoot(p, configuration), ObjectToJson.fromProject(p))))));
    }

    private void generateProjectTree(SwtWindow parent) {
        parent.createTree(150, parent.getHeight(),
                new TreeElement("Project",
                        new TreeElement("Scene",
                                new TreeElement("Models"),
                                new TreeElement("Lights"),
                                new TreeElement("Particles"),
                                new TreeElement("Cameras")),
                        new TreeElement("Materials"),
                        new TreeElement("Audios",
                                new TreeElement("SFX"),
                                new TreeElement("Musics")),
                        new TreeElement("UI")));
    }

}
