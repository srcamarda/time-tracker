package view;

import model.Pessoa;
import model.Projeto;
import repository.ArquivoPaths;
import repository.RepositoryPessoa;
import repository.RepositoryProjeto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuPessoa {

    private static final ArquivoPaths arquivoPessoa = ArquivoPaths.PESSOAS;
    private static final ArquivoPaths arquivoProjeto = ArquivoPaths.PROJETOS;
    private static final RepositoryPessoa repositoryPessoa = new RepositoryPessoa(arquivoPessoa);
    private static final RepositoryProjeto repositoryProjeto = new RepositoryProjeto(arquivoProjeto);

    public static void planiliaDeHoras() {

        String pessoaId = MenuPrincipal.scanner.nextLine();
        List<Projeto> projetos = repositoryProjeto.carregarProjetos();
        Pessoa pesquisaPessoa = repositoryPessoa.buscarPessoa(pessoaId);

        if (pesquisaPessoa != null) {

            System.out.println("\nUsuario:" + pesquisaPessoa.getUsername() + "\nPlanilia de horas");

            for (Projeto projeto : projetos) {
                if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(pesquisaPessoa.getId()))) {

                    LocalDateTime inicio = projeto.getDataHoraInicio();
                    LocalDateTime fim = projeto.getDataHoraFim();

                    Duration duracaoProjeto = Duration.between(inicio, fim);

                    System.out.print("\n"+
                                     "\nProjeto: " + projeto.getTitulo() +
                                     "\nDia da Semana " + inicio.getDayOfWeek() +
                                     "\nDia: "+ diaFormatado(inicio) +
                                     "\nTempo decorrido-" + tempoFormatado(duracaoProjeto)+ "\n");
                }
            }
        }
    }


    public static void verificarPessoas() {
        List<Pessoa> pessoaList = repositoryPessoa.carregarPessoas();

        pessoaList.stream().forEach(pessoa -> System.out.println(pessoa.getId() + pessoa.getNome()));

    }

    public static void calcularMediaTempoGeral() {

        String id = MenuPrincipal.scanner.nextLine();
        Duration duracaoTotal = obterTempoProjetos(id);

        long totalDias = duracaoTotal.toDays();

        if (totalDias > 0) {
            Duration mediaGeral = duracaoTotal.dividedBy(totalDias);
            System.out.println(tempoFormatado(duracaoTotal));
        }
    }

    //Corrigir
    public static void calcularMediaPorDia() {

        //Corrigir erro

        String id = MenuPrincipal.scanner.nextLine();

        Duration duracaoTotal = obterTempoProjetos(id);
        System.out.println("Duracao Total " + tempoFormatado(duracaoTotal));

        System.out.println(tempoFormatado(duracaoTotal));
    }

    //Corrigir
    public static Duration obterTempoProjetos(String pessoaId) {

        List<Projeto> projetos = repositoryProjeto.carregarProjetos();

        Pessoa pesquisaPessoa = repositoryPessoa.buscarPessoa(pessoaId);

        int participacoes = 0;
        Duration duracaoTotal = Duration.ZERO;

        if (pesquisaPessoa != null) {
            for (Projeto projeto : projetos) {
                if (projeto.getPessoasDTO().stream().anyMatch(pessoa -> pessoa.id().equals(pesquisaPessoa.getId()))) {

                    LocalDateTime inicio = projeto.getDataHoraInicio();
                    System.out.print("inicio hora - " + inicio.getHour() + " ini min - " + inicio.getMinute() + " ini sec - " + inicio.getSecond() + "\n");

                    LocalDateTime fim = projeto.getDataHoraFim();
                    System.out.print("Fim hora - " + fim.getHour() + " Fim min - " + fim.getMinute() + " Fim sec - " + fim.getSecond() + "\n");

                    Duration duracaoProjeto = Duration.between(inicio, fim);

                    System.out.println(participacoes + " - Duração do projeto - " + tempoFormatado(duracaoProjeto));

                    duracaoTotal = duracaoTotal.plus(duracaoProjeto);
                    //Está somando até certo ponto após 2 projetos a mais ele reduz
                    System.out.println(participacoes + " Duracao Total" + tempoFormatado(duracaoTotal));

                    participacoes++;
                }
            }

            if (participacoes > 0) {

                return duracaoTotal;
            }

            System.out.println("Nenhuma participação encontrada");
            return Duration.ZERO;
        }

        System.out.println("Id não encontrado");
        return Duration.ZERO;
    }


    private static String tempoFormatado(Duration duracao) {
        int hora = duracao.toHoursPart();
        int minutos = duracao.toMinutesPart();
        int segundos = duracao.toSecondsPart();

        return hora + ":" + minutos + ":" + segundos;
    }

    private static String diaFormatado(LocalDateTime data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

}

