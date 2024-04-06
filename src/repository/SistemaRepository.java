package repository;

public enum SistemaRepository {
    INSTANCE;

    private RepositoryPessoa repositoryPessoa;
    private RepositoryTarefa repositoryTarefa;
    private RepositoryProjeto repositoryProjeto;

    SistemaRepository() {
        repositoryPessoa = new RepositoryPessoa(ArquivoPaths.PESSOAS);
        repositoryTarefa = new RepositoryTarefa(ArquivoPaths.TAREFAS);
        repositoryProjeto = new RepositoryProjeto(ArquivoPaths.PROJETOS);
    }

    public RepositoryPessoa getRepositoryPessoa() {
        return repositoryPessoa;
    }

    public RepositoryTarefa getRepositoryTarefa() {
        return repositoryTarefa;
    }

    public RepositoryProjeto getRepositoryProjeto() {
        return repositoryProjeto;
    }
}
