package view.mensagens;

public enum MensagensMenu {
    OPCAO_INVALIDA("Opção inválida!"),
    RETORNA_MENU("Retornando ao menu principal"),
    SAIR_MENU("Até logo!"),
    REL_ATIVIDADES("Relatórios de Atividades"),
    REL_INDICADORES("Relatórios de Indicadores"),
    GER_PESSOAS("Gerenciar Pessoas"),
    GER_PROJETOS("Gerenciar Projetos"),
    GER_TAREFAS("Gerenciar Tarefas");

    private final String mensagem;

    MensagensMenu(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}