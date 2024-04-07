import service.PessoaService;
import service.ProjetoService;
import service.TarefaService;

public class Main {
    public static void main(String[] args) {
        System.out.println(PessoaService.buscarPessoa("  JuliA   "));
        System.out.println(ProjetoService.buscarProjeto("Ap"));
        System.out.println(TarefaService.buscarTarefa("desen"));
    }
}