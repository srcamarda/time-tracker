package com.dev.tptimetracker.controller;

import com.dev.tptimetracker.dto.PessoaDTO;
import com.dev.tptimetracker.model.Pessoa;
import com.dev.tptimetracker.model.Projeto;
import com.dev.tptimetracker.model.Tag;
import com.dev.tptimetracker.model.Tarefa;
import com.dev.tptimetracker.utility.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dev.tptimetracker.service.PessoaService;
import com.dev.tptimetracker.service.ProjetoService;
import com.dev.tptimetracker.service.TarefaService;

public class MenuAcoes {

    private static Scanner scanner = new Scanner(System.in);

    public static void planilhaDeHoras() {

        String username = ValidadoresEntrada.obterUsernameValidado
                (EntradaHelper.obterDado("Digite o username: ", scanner));

        MenuRelatorios.planilhaDeHoras(username);
    }

    public static void tempoTotalPeriodo() {

        try {

            String username = ValidadoresEntrada.obterUsernameValidado
                    (EntradaHelper.obterDado("Digite o username: ", scanner));

            LocalDate dataInicio = ValidadoresEntrada.obterDataValidada
                    (EntradaHelper.obterDado("Digite uma data inicial do periodo desejado (Formato  dd/MM/yyyy): ", scanner));

            LocalDate dataFim = ValidadoresEntrada.obterDataValidada
                    (EntradaHelper.obterDado("Digite uma data final do periodo desejado (Formato  dd/MM/yyyy): ", scanner));

            MenuRelatorios.relatorioNoPeriodo(username, dataInicio, dataFim);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void tempoTotalSemanal() {

        try {

            String username = ValidadoresEntrada.obterUsernameValidado
                    (EntradaHelper.obterDado("Digite o username: ", scanner));

            LocalDate dataString = ValidadoresEntrada.obterDataValidada
                    (EntradaHelper.obterDado("Digite uma data na semana desejada (Formato  dd/MM/yyyy): ", scanner));

            MenuRelatorios.relatorioSemanal(username, dataString);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void tempoTotalMensal() {

        try {

            String username = ValidadoresEntrada.obterUsernameValidado
                    (EntradaHelper.obterDado("Digite o username: ", scanner));

            int diaMes = Integer.parseInt(ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite o número referente ao mês desejado: ", scanner)));

            if (diaMes < 1 || diaMes > 12) {
                System.out.println("Mês Inválido.");
                return;
            }

            LocalDate dataMes = LocalDate.of(2024, diaMes, 1);
            MenuRelatorios.relatorioMensal(username, dataMes);

        } catch (IllegalArgumentException e) {
            System.out.println("Mês Inválido.");
        }
    }

    public static void mediaTempoPorDia() {
        System.out.println("Opção selecionada: Média de Tempo por Dia");
        MenuRelatorios.calcularMediaPorDia();
    }

    public static void mediaTempoGeral() {
        System.out.println("Opção selecionada: Média de Tempo Geral");
        MenuRelatorios.calcularMediaTempoGeral();
    }

    public static void cadastrarPessoa() {
        try {
            String nome = ValidadoresEntrada.obterNomeValidado
                    (EntradaHelper.obterDado("Digite o nome: ", scanner));

            String username = ValidadoresEntrada.obterUsernameValidado
                    (EntradaHelper.obterDado("Digite o username: ", scanner));

            String cpf = ValidadoresEntrada.obterCpfValidado
                    (EntradaHelper.obterDado("Digite o cpf: ", scanner));

            TipoCargo cargo = ValidadoresEntrada.obterCargoValidado
                    (EntradaHelper.obterDado("Digite o cargo: ", scanner));

            TipoPlano plano = ValidadoresEntrada.obterPlanoValidado
                    (EntradaHelper.obterDado("Digite o plano: ", scanner));

            Pessoa pessoa = new Pessoa.Builder()
                    .username(username)
                    .nome(nome)
                    .cpf(cpf)
                    .cargo(cargo)
                    .plano(plano)
                    .build();

            PessoaService.criarPessoa(pessoa);

            System.out.println("Pessoa cadastrada com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarPessoas() {
        System.out.println(PessoaService.buscarPessoas(""));
    }

    public static void cadastrarTarefa() {
        try {
            String titulo = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite o título: ", scanner));

            String descricao = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite a descrição: ", scanner));

            PessoaDTO pessoa = Conversores.converterParaDTO
                    (PessoaService.buscarPessoa
                            (ValidadoresEntrada.obterUsernameValidado
                                    (EntradaHelper.obterDado("Digite o username: ", scanner))));

            LocalDateTime dataInicio = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de início: ", scanner));

            LocalDateTime dataFinal = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de final: ", scanner));

            Tag tag = ValidadoresEntrada.obterTagValidado
                    (EntradaHelper.obterDado("Digite a tag: ", scanner));

            Tarefa tarefa = new Tarefa.Builder()
                    .titulo(titulo)
                    .descricao(descricao)
                    .pessoaDTO(pessoa)
                    .dataHoraInicio(dataInicio)
                    .dataHoraFim(dataFinal)
                    .tag(tag)
                    .build();

            TarefaService.criarTarefa(tarefa);

            System.out.println("Tarefa cadastrada com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarTarefas() {
        System.out.println(TarefaService.buscarTarefas(""));
    }

    public static void cadastrarProjeto() {
        try {
            String titulo = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite o título: ", scanner));

            String descricao = ValidadoresEntrada.obterTextoValidado
                    (EntradaHelper.obterDado("Digite a descrição: ", scanner));

            LocalDateTime dataInicio = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de início: ", scanner));

            LocalDateTime dataFinal = ValidadoresEntrada.obterDataTimeValidada
                    (EntradaHelper.obterDado("Digite a data de final: ", scanner));

            PessoaDTO pessoa = Conversores.converterParaDTO
                    (PessoaService.buscarPessoa
                            (ValidadoresEntrada.obterUsernameValidado
                                    (EntradaHelper.obterDado("Digite o username do responsável: ", scanner))));

            List<PessoaDTO> pessoas = new ArrayList<>();
            pessoas.add(pessoa);

            Projeto projeto = new Projeto.Builder()
                    .titulo(titulo)
                    .descricao(descricao)
                    .pessoasDTO(pessoas)
                    .dataHoraInicio(dataInicio)
                    .dataHoraFim(dataFinal)
                    .build();

            ProjetoService.criarProjeto(projeto);

            System.out.println("Projeto cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void adicionarPessoaAProjeto() {
        try {
            String titulo = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título do projeto: ", scanner));

            String username = ValidadoresEntrada
                    .obterUsernameValidado(EntradaHelper.obterDado("Digite o username da pessoa: ", scanner));

            boolean pessoaFoiAdicionada = ProjetoService.adicionarPessoa(titulo, username);

            String mensagem = pessoaFoiAdicionada
                    ? "Pessoa vinculada ao projeto com sucesso!"
                    : "Erro ao vincular pessoa, verifique os dados...";

            System.out.println(mensagem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void adicionarTarefaAProjeto() {
        try {
            String tituloProjeto = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título do projeto: ", scanner));

            String tituloTarefa = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título da tarefa: ", scanner));

            boolean tarefaFoiAdicionada = ProjetoService.adicionarTarefa(tituloProjeto, tituloTarefa);

            String mensagem = tarefaFoiAdicionada
                    ? "Tarefa adicionada ao projeto com sucesso!"
                    : "Erro ao adicionar tarefa, verifique os dados...";

            System.out.println(mensagem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void adicionarTagAProjeto() {
        try {
            String titulo = ValidadoresEntrada
                    .obterTextoValidado(EntradaHelper.obterDado("Digite o título do projeto: ", scanner));

            Tag tituloTarefa = ValidadoresEntrada
                    .obterTagValidado(EntradaHelper.obterDado("Informe a tag: ", scanner));

            boolean tagFoiAdicionada = ProjetoService.adicionarTag(titulo, tituloTarefa);

            String mensagem = tagFoiAdicionada
                    ? "Tag adicionada ao projeto com sucesso!"
                    : "Erro ao adicionar Tag, verifique os dados...";

            System.out.println(mensagem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarProjetos() {
        System.out.println(ProjetoService.buscarProjetos(""));
    }

}