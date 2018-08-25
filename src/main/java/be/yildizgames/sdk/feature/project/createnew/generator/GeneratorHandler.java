package be.yildizgames.sdk.feature.project.createnew.generator;

import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.feature.project.model.Project;

import java.util.ArrayList;
import java.util.List;

public class GeneratorHandler {

    private final Project project;

    private final Configuration configuration;

    private final List<Generator> generators = new ArrayList<>();

    private GeneratorHandler(Project project, Configuration configuration) {
        this.project = project;
        this.configuration = configuration;
    }

    private GeneratorHandler register(Generator generator) {
        this.generators.add(generator);
        return this;
    }

    public void run() {
        generators.forEach(g -> g.generate(this.project, this.configuration));
    }

    public static GeneratorHandler forProject(Project p, Configuration configuration) {
        return new GeneratorHandler(p, configuration)
                .register(new DirectoryGenerator())
                .register(new PomGenerator())
                .register(new GitIgnoreGenerator())
                .register(new LicenceGenerator())
                .register(new ReadmeGenerator());
    }
}
