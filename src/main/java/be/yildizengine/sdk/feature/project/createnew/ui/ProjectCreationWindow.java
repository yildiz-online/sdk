package be.yildizengine.sdk.feature.project.createnew.ui;

import javax.swing.*;
import java.awt.*;

public class ProjectCreationWindow {

    public void init() {
        JFrame frame = createFrame();
        JTextField projectName = new JTextField();
        projectName.setLocation(50,50);
        projectName.setVisible(true);
        projectName.setText("blablabal");
        frame.add(projectName);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("New project");
        frame.setSize(500,500);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);
        return frame;
    }
}
