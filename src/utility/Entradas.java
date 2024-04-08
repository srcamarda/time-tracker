package utility;

import model.Tag;

import java.time.LocalDateTime;
import java.util.Random;

public class Entradas {
    public static String obterCpfValidado(String cpf) {
        if (!Validadores.validaCPF(cpf))
            throw new IllegalArgumentException(Mensagens.ERRO_CPF.getMensagem());
        return cpf;
    }

    public static String obterNomeValidado(String nome) {
        if (!Validadores.validaNome(nome))
            throw new IllegalArgumentException(Mensagens.ERRO_NOME.getMensagem());
        return nome;
    }

    public static String obterUsernameValidado(String username, String nome) {
        if (!Validadores.validaUsername(username)) {
            throw new IllegalArgumentException(Mensagens.ERRO_USERNAME.getMensagem());
        } else if (username.isEmpty()) {
            Random random = new Random();
            username = nome.replace(" ", "_").toLowerCase() + random.nextInt(999);
        }
        return username;
    }

    public static String obterTextoValidado(String texto) {
        if (!Validadores.validaTexto(texto))
            throw new IllegalArgumentException(Mensagens.ERRO_TEXTO.getMensagem());
        return texto;
    }


    public static LocalDateTime obterDataValidada(String data) {
        if (!Validadores.validaData(data))
            throw new IllegalArgumentException(Mensagens.ERRO_DATA.getMensagem());
        return LocalDateTime.parse(data);
    }

    public static TipoCargo obterCargoValidado(String cargo) {
        if (!Validadores.validaEnum(cargo) || TipoCargo.valueOf(cargo).toString().isEmpty())
            throw new IllegalArgumentException(Mensagens.ERRO_CARGO.getMensagem());
        return TipoCargo.valueOf(cargo);
    }

    public static TipoPlano obterPlanoValidado(String plano) {
        if (!Validadores.validaEnum(plano) || TipoPlano.valueOf(plano).toString().isEmpty())
            throw new IllegalArgumentException(Mensagens.ERRO_PLANO.getMensagem());
        return TipoPlano.valueOf(plano);
    }

    public static Tag obterTagValidado(String tag) {
        if (!Validadores.validaEnum(tag) || Tag.valueOf(tag).toString().isEmpty())
            throw new IllegalArgumentException(Mensagens.ERRO_CARGO.getMensagem());
        return Tag.valueOf(tag);
    }

    public static String obterUUIDValidado(String uuid) {
        if (!Validadores.validaUUID(uuid))
            throw new IllegalArgumentException(Mensagens.ERRO_UUID.getMensagem());
        return uuid;
    }
}