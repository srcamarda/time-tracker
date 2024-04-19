package com.dev.tptimetracker.view;

public class MenuExibir {
    static void menuPrincipal() {
        System.out.print("""
                
                ---- Menu Principal ----
                
                1 - Planilha de Horas
                2 - Relatórios de Atividades
                3 - Relatórios de Indicadores
                4 - Gerenciar Pessoas
                5 - Gerenciar Projetos
                6 - Gerenciar Tarefas
                7 - Sair
                
                Selecione uma opção ->""");
    }

    static void relatoriosAtividadesMenu() {
        System.out.println("""
                
                ---- Relatórios de Atividades ----
                
                1 - Tempo Total Semanal
                2 - Tempo Total Mensal
                3 - Tempo Total em um Periodo
                4 - Voltar
                
                Selecione uma opção ->""");
    }

    static void relatoriosIndicadoresMenu() {
        System.out.print("""
                
                ---- Relatórios de Indicadores ----
                
                1 - Média de Tempo por Dia
                2 - Média de Tempo Geral
                3 - Voltar
                
                Selecione uma opção ->""");
    }

    static void gerenciarPessoasMenu() {
        System.out.println("""
                
                ---- Gerenciar Pessoas ----
                
                1 - Cadastrar Pessoa
                2 - Listar Pessoas
                3 - Voltar
                
                Selecione uma opção ->""");
    }

    static void gerenciarProjetosMenu() {
        System.out.println("""
                
                ---- Gerenciar Projetos ----
                
                1 - Cadastrar Projeto
                2 - Listar Projetos
                3 - Adicionar Pessoa a Projeto
                4 - Adicionar Tarefa a Projeto
                5 - Adicionar Tag a Projeto
                6 - Voltar
                
                Selecione uma opção ->""");
    }

    static void gerenciarTarefasMenu() {
        System.out.println("""
                
                ---- Gerenciar Tarefas ----
                
                1 - Cadastrar Tarefa
                2 - Listar Tarefas
                3 - Voltar
                
                Selecione uma opção ->""");
    }
}