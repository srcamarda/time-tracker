import dto.PessoaDTO;
import model.Pessoa;
import service.PessoaService;
import service.ProjetoService;
import service.TarefaService;
import utility.Conversores;

public class Main {
    public static void main(String[] args) {
//        System.out.println(PessoaService.buscarPessoa("  JuliA   "));
//        System.out.println(ProjetoService.buscarProjeto("Ap"));
//        System.out.println(TarefaService.buscarTarefa("desen"));
        Pessoa pessoa = PessoaService.buscarPessoa("  igor   ");
        System.out.println(TarefaService.buscarTarefa(Conversores.converterParaDTO(pessoa)));

    }
}