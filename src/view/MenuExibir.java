package view;

public class MenuExibir {
    static void menuPrincipal() {
        System.out.print("""
                                
                ---- Menu Principal ----
                                
                1- Planilha de Horas
                2- Relatórios de Atividades
                3- Relatórios de Indicadores
                4- Sair
                                
                Selecione uma opção ->""");
    }

    static void RelatoriosAtividadesMenu() {
        System.out.println("""
                                
                ---- Relatórios de Atividades ----
                                
                1. Tempo Total Semanal
                2. Tempo Total Mensal
                3. Voltar
                                
                Selecione uma opção ->""");
    }

    static void RelatoriosIndicadoresMenu() {
        System.out.print("""
                                
                ---- Relatórios de Indicadores ----"
                                
                1- Média de Tempo por Dia
                2- Média de Tempo Geral
                3- Ranking de Tempo
                4- Voltar
                               
                Selecione uma opção ->""");
    }
}
