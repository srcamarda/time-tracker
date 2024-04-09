package mockup;

import dto.ProjetoDTO;
import dto.TarefaDTO;
import dto.PessoaDTO;
import model.Pessoa;
import model.Projeto;
import model.Tag;
import model.Tarefa;
import repository.RepositoryPessoa;
import repository.RepositoryProjeto;
import repository.RepositoryTarefa;
import service.PessoaService;
import utility.Conversores;
import utility.TipoCargo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mockup {
    public static List<PessoaDTO> pessoaDTOList = new ArrayList<>();
    public static List<TarefaDTO> tarefaDTOList = new ArrayList<>();
    public static List<ProjetoDTO> projetoDTOList = new ArrayList<>();


    public static void init() {
        criarPessoas();
        criarTarefas();
        criarProjetos();
    }

    private static void criarInstancias() {

    }

    private static void criarPessoas() {
        Pessoa pessoa = new Pessoa.Builder().nome("Paulo").cargo(TipoCargo.JUNIOR).cpf("123.456.789-01").build();
        Pessoa pessoa1 = new Pessoa.Builder().nome("Pedro").cargo(TipoCargo.JUNIOR).cpf("123.456.789-02").build();

        pessoaDTOList.add(new dto.PessoaDTO(pessoa.getId(), pessoa.getUsername(), pessoa.getNome(), pessoa.getPlano(), pessoa.getCargo()));
        pessoaDTOList.add(new dto.PessoaDTO(pessoa1.getId(), pessoa1.getUsername(), pessoa1.getNome(), pessoa1.getPlano(), pessoa1.getCargo()));

        RepositoryPessoa.INSTANCE.salvarPessoa(pessoa);
        RepositoryPessoa.INSTANCE.salvarPessoa(pessoa1);
    }

    private static void criarTarefas() {
        Tarefa tarefa = new Tarefa.Builder()
                .titulo("Estruturar projeto")
                .descricao("Teste")
                .pessoaDTO(Conversores.converterParaDTO(PessoaService.buscarPessoa("igor")))
                .tag(Tag.URGENTE)
                .dataHoraInicio(LocalDateTime.of(2024, 4, 7, 10, 0))
                .dataHoraFim(LocalDateTime.of(2024, 4, 7, 10, 4))
                .build();

        Tarefa tarefa1 = new Tarefa.Builder()
                .titulo("Testar classes")
                .descricao("Teste")
                .pessoaDTO(pessoaDTOList.get(1))
                .tag(Tag.IMPORTANTE)
                .build();

        tarefaDTOList.add(Conversores.converterParaDTO(tarefa));
        tarefaDTOList.add(Conversores.converterParaDTO(tarefa1));

        RepositoryTarefa.INSTANCE.salvarTarefa(tarefa);
        RepositoryTarefa.INSTANCE.salvarTarefa(tarefa1);
    }

    private static void criarProjetos() {
        Projeto projeto = new Projeto.Builder()
                .titulo("TP Time Tracker")
                .descricao("Projeto Ada")
                .tags(List.of(Tag.IMPORTANTE))
                .tarefasDTO(tarefaDTOList)
                .build();

        ProjetoDTO projetoDTO = new ProjetoDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getPessoasDTO(),
                projeto.getDataHoraInicio(),
                projeto.getDataHoraFim(),
                projeto.getTags(),
                projeto.getTarefasDTO());

        Projeto projeto1 = new Projeto.Builder()
                .titulo("Projeto final")
                .descricao("Projeto Final Ada")
                .tags(List.of(Tag.URGENTE))
                .dataHoraInicio(LocalDateTime.of(2024, 4, 1, 12, 30))
                .dataHoraFim(LocalDateTime.of(2024, 4, 8, 23, 59))
                .pessoasDTO(pessoaDTOList)
                .build();

        ProjetoDTO projetoDTO1 = new ProjetoDTO(
                projeto1.getId(),
                projeto1.getTitulo(),
                projeto1.getDescricao(),
                projeto1.getPessoasDTO(),
                projeto1.getDataHoraInicio(),
                projeto1.getDataHoraFim(),
                projeto1.getTags(),
                projeto1.getTarefasDTO());

        projetoDTOList.add(projetoDTO);
        projetoDTOList.add(projetoDTO1);

        RepositoryProjeto.INSTANCE.salvarProjeto(projeto);
        RepositoryProjeto.INSTANCE.salvarProjeto(projeto1);
    }
}