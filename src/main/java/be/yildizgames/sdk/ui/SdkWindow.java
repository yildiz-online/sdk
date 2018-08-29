package be.yildizgames.sdk.ui;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.libloader.NativeResourceLoader;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.shape.Box;
import be.yildizgames.module.color.Color;
import be.yildizgames.module.graphic.GraphicObject;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.graphic.ogre.OgreGraphicEngine;
import be.yildizgames.module.window.swt.MenuBarElement;
import be.yildizgames.module.window.swt.MenuElement;
import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.SwtWindowEngine;
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
        SwtWindowEngine w = new SwtWindowEngine(window, false);
        w.deleteLoadingResources();
        w.showCursor();
        OgreGraphicEngine engine = new OgreGraphicEngine(w, NativeResourceLoader.inJar());
        GraphicWorld world = engine.createWorld();
        GraphicObject o = world.createMovableObject(EntityId.WORLD, Box.cube(50), Material.green(), Point3D.valueOf(0,0,-100));
        world.createPointLight("t", Point3D.ZERO);
        world.getDefaultCamera().setDirection(Point3D.BASE_DIRECTION);
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
