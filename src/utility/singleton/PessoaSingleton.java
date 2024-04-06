package utility.singleton;

import repository.ArquivoPaths;
import repository.RepositoryPessoa;

public enum PessoaSingleton {
    INSTANCE;

    private final RepositoryPessoa repositoryPessoa;

    PessoaSingleton() {
        repositoryPessoa = new RepositoryPessoa(ArquivoPaths.PESSOAS);
    }

    public RepositoryPessoa getRepositoryPessoa() {
        return repositoryPessoa;
    }
}
