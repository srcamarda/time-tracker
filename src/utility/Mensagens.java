package utility;

public enum Mensagens {
    ERRO_CPF("CPF inválido"),
    ERRO_NOME("Nome inválido"),
    ERRO_USERNAME("Username inválido"),
    ERRO_DATA("Data inválida"),
    ERRO_CARGO("Cargo inválido"),
    ERRO_PLANO("Plano inválido"),
    ERRO_UUID("UUID inválido");

    private final String mensagem;

    Mensagens(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}