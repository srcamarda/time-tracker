import mockup.Mockup;
import model.Pessoa;
import model.Tarefa;
import repository.*;
import service.PessoaService;
import utility.TipoCargo;
import utility.TipoPlano;
import utility.singleton.PessoaSingleton;
import utility.singleton.ProjetoSingleton;
import utility.singleton.TarefaSingleton;

public class Main {
    public static void main(String[] args) {
        System.out.println(PessoaService.buscarPessoa("  JuliA   "));
    }
}