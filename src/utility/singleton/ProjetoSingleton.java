package utility.singleton;

import repository.ArquivoPaths;
import repository.RepositoryProjeto;

public enum ProjetoSingleton {
    INSTANCE;

    private final RepositoryProjeto repositoryProjeto;

    ProjetoSingleton() {
        repositoryProjeto = new RepositoryProjeto(ArquivoPaths.PESSOAS);
    }

    public RepositoryProjeto getRepositoryProjeto() {
        return repositoryProjeto;
    }
}
