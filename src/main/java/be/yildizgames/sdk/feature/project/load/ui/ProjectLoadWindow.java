package be.yildizgames.sdk.feature.project.load.ui;

import be.yildizgames.module.window.swt.SwtWindow;
import be.yildizgames.sdk.configuration.Configuration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

public class ProjectLoadWindow {

    private final SwtWindow parent;

    public ProjectLoadWindow(SwtWindow parent) {
        super();
        this.parent = parent;
    }

    public void init(Configuration configuration) {
        FileDialog fd = new FileDialog(this.parent.getShell(), SWT.OPEN);
        fd.setText("Open");
        fd.setFilterPath(configuration.rootPath);
        fd.setFilterExtensions(new String[] { "*.yzf" });
        String selected = fd.open();

    }
}
