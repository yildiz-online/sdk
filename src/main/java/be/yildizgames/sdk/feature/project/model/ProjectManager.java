package be.yildizgames.sdk.feature.project.model;

import java.util.Optional;

public class ProjectManager {

    private Project project;

    public void setProject(Project p) {
        this.project = p;
    }

    public Optional<Project> getProject() {
        return Optional.ofNullable(this.project);
    }
}
