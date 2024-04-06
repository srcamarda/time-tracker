import mockup.Mockup;
import model.Pessoa;
import repository.ArquivoPaths;
import repository.RepositoryPessoa;
import repository.RepositoryProjeto;
import repository.RepositoryTarefa;
import utility.TipoCargo;
import utility.TipoPlano;

public class Main {
    public static void main(String[] args) {
        //Mockup.init();
        RepositoryPessoa repositoryPessoa = new RepositoryPessoa(ArquivoPaths.PESSOAS);
        RepositoryTarefa repositoryTarefa = new RepositoryTarefa(ArquivoPaths.TAREFAS, repositoryPessoa);
        RepositoryProjeto repositoryProjeto = new RepositoryProjeto(ArquivoPaths.PROJETOS, repositoryPessoa, repositoryTarefa);
    }
}