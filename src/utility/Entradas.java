package utility;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class Entradas {
    public String obterCpfValidado(String cpf) {
        if (!Validadores.validaCPF(cpf))
            throw new IllegalArgumentException(Mensagens.ERRO_CPF.getMensagem());
        return cpf;
    }

    public String obterNomeValidado(String nome) {
        if (!Validadores.validaNome(nome))
            throw new IllegalArgumentException(Mensagens.ERRO_NOME.getMensagem());
        return nome;
    }

    public String obterUsernameValidado(String username, String nome) {
        if (!Validadores.validaUsername(username)) {
            throw new IllegalArgumentException(Mensagens.ERRO_USERNAME.getMensagem());
        } else if (username.isEmpty()) {
            Random random = new Random();
            username = nome.replace(" ", "_").toLowerCase() + random.nextInt(999);
        }
        return username;
    }

    public LocalDateTime obterDataValidada(String data) {
        if (!Validadores.validaData(data))
            throw new IllegalArgumentException(Mensagens.ERRO_DATA.getMensagem());
        return LocalDateTime.parse(data);
    }

    public TipoCargo obterCargoValidado(String cargo) {
        if (!Validadores.validaEnum(cargo) || TipoCargo.valueOf(cargo).toString().isEmpty())
            throw new IllegalArgumentException(Mensagens.ERRO_CARGO.getMensagem());
        return TipoCargo.valueOf(cargo);
    }

    public TipoPlano obterPlanoValidado(String plano) {
        if (!Validadores.validaEnum(plano) || TipoPlano.valueOf(plano).toString().isEmpty())
            throw new IllegalArgumentException(Mensagens.ERRO_PLANO.getMensagem());
        return TipoPlano.valueOf(plano);
    }

    public UUID obterUUIDValidado(String uuid) {
        if (!Validadores.validaUUID(uuid))
            throw new IllegalArgumentException(Mensagens.ERRO_UUID.getMensagem());
        return UUID.fromString(uuid);
    }
}