package org.buildcli;

import org.buildcli.core.ProjectCompiler;
import org.buildcli.core.ProjectInitializer;
import org.buildcli.core.ProjectRunner;
import org.buildcli.utils.PomUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "BuildCLI", mixinStandardHelpOptions = true, version = "BuildCLI 0.0.1",
        description = "BuildCLI - A CLI for Java Project Management")
public class BuildCLI implements Runnable {

    @Option(names = {"-i", "--init"}, description = "Initialize a new Java Project")
    boolean init;

    @Option(names = {"-c", "--compile"}, description = "Compile a Java Project")
    boolean compile;

    @Option(names = {"--add-dependency"}, description = "Add a dependency in 'groupId:artifactId' format")
    String dependency;

    @Option(names = {"-p", "--profile"}, description = "Create a configuration profile")
    String profile;

    @Option(names = {"--run"}, description = "Run the Java Project")
    boolean run;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new BuildCLI()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            if (init) {
                new ProjectInitializer().initializeProject();
            } else if (compile) {
                new ProjectCompiler().compileProject();
            } else if (dependency != null) {
                PomUtils.addDependencyToPom(dependency);
            } else if (profile != null) {
                ProjectInitializer.createProfileConfig(profile);
            } else if (run) {
                new ProjectRunner().runProject();
            } else {
                System.out.println("Welcome to BuildCLI - Java Project Management!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}