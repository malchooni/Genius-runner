package name.yalsooni.genius.runner.definition;

public interface LifeCycle {
    void initialize() throws Exception;
    void execute() throws Exception;
}
