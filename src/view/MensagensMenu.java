package view;

public enum MensagensMenu {
    OPCAO_INVALIDA("Opção inválida!"),
    RETORNA_MENU("Retornando ao menu principal");

    private final String mensagem;

    MensagensMenu(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}