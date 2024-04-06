package utility.singleton;

import repository.ArquivoPaths;
import repository.RepositoryTarefa;

public enum TarefaSingleton {
    INSTANCE;

    private final RepositoryTarefa repositoryTarefa;

    TarefaSingleton() {
        repositoryTarefa = new RepositoryTarefa(ArquivoPaths.TAREFAS);
    }

    public RepositoryTarefa getRepositoryTarefa() {
        return repositoryTarefa;
    }
}
