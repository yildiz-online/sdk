package be.yildizgames.sdk.feature.project.tree;

import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.module.window.swt.TreeElement;
import be.yildizgames.sdk.feature.project.ProjectListener;
import be.yildizgames.sdk.feature.project.model.Project;
import be.yildizgames.sdk.feature.project.model.items.BoxDefinition;
import be.yildizgames.sdk.feature.project.model.items.ParticleSystemDefinition;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class ProjectTree implements ProjectListener {

    private final SwtWindow parent;

    private Tree tree;

    public ProjectTree(SwtWindow parent) {
        this.parent = parent;
        this.tree = parent.createTree(150, parent.getHeight());
    }

    @Override
    public void onLoad(Project p) {
        this.tree.dispose();

        TreeElement[] particles = p.scene.getParticles()
                .stream()
                .map(pdef -> new TreeElement(pdef.getName()))
                .toArray(TreeElement[]::new);

        this.tree = parent.createTree(150, parent.getHeight(),
                new TreeElement("Project",
                        new TreeElement("Scene",
                                new TreeElement("Models"),
                                new TreeElement("Lights"),
                                new TreeElement("Particles", particles),
                                new TreeElement("Cameras")),
                        new TreeElement("Materials"),
                        new TreeElement("Audios",
                                new TreeElement("SFX"),
                                new TreeElement("Musics")),
                        new TreeElement("UI")));
    }

    @Override
    public void onUpdate(ParticleSystemDefinition definition) {
        //FIXME fragile
        TreeItem particle = tree.getItem(0).getItem(0).getItem(2);
        TreeItem item = new TreeItem(particle, 0);
        item.setText(definition.getName());
        particle.setExpanded(true);
    }

    @Override
    public void onUpdate(BoxDefinition definition) {
        //FIXME fragile
        TreeItem mesh = tree.getItem(0).getItem(0).getItem(0);
        TreeItem item = new TreeItem(mesh, 0);
        item.setText(definition.getName());
        mesh.setExpanded(true);
    }
}
