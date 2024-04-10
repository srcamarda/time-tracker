package utility;

import model.Tag;
import view.mensagens.MensagensErro;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ValidadoresEntrada {
    public static String obterCpfValidado(String cpf) {
        if (!Validadores.validaCPF(cpf))
            throw new IllegalArgumentException(MensagensErro.ERRO_CPF.getMensagem());
        return cpf;
    }

    public static String obterNomeValidado(String nome) {
        if (!Validadores.validaNome(nome))
            throw new IllegalArgumentException(MensagensErro.ERRO_NOME.getMensagem());
        return nome;
    }

    public static String obterUsernameValidado(String username) {
        if (!Validadores.validaUsername(username)) {
            throw new IllegalArgumentException(MensagensErro.ERRO_USERNAME.getMensagem());
        }
        return username;
    }

    public static String obterTextoValidado(String texto) {
        if (!Validadores.validaTexto(texto))
            throw new IllegalArgumentException(MensagensErro.ERRO_TEXTO.getMensagem());
        return texto;
    }


    public static LocalDate obterDataValidada(String data) {
        if (!Validadores.validaData(data))
            throw new IllegalArgumentException(MensagensErro.ERRO_DATA.getMensagem());
        return LocalDate.parse(data);
    }
    public static LocalDateTime obterDataTimeValidada(String data) {
        if (!Validadores.validaDataTime(data))
            throw new IllegalArgumentException(MensagensErro.ERRO_DATA.getMensagem());
        return LocalDateTime.parse(data);
    }

    public static TipoCargo obterCargoValidado(String cargo) {
        if (!Validadores.validaEnum(cargo) || TipoCargo.valueOf(cargo).toString().isEmpty())
            throw new IllegalArgumentException(MensagensErro.ERRO_CARGO.getMensagem());
        return TipoCargo.valueOf(cargo);
    }

    public static TipoPlano obterPlanoValidado(String plano) {
        if (!Validadores.validaEnum(plano) || TipoPlano.valueOf(plano).toString().isEmpty())
            throw new IllegalArgumentException(MensagensErro.ERRO_PLANO.getMensagem());
        return TipoPlano.valueOf(plano);
    }

    public static Tag obterTagValidado(String tag) {
        if (!Validadores.validaEnum(tag) || Tag.valueOf(tag).toString().isEmpty())
            throw new IllegalArgumentException(MensagensErro.ERRO_CARGO.getMensagem());
        return Tag.valueOf(tag);
    }

    public static String obterUUIDValidado(String uuid) {
        if (!Validadores.validaUUID(uuid))
            throw new IllegalArgumentException(MensagensErro.ERRO_UUID.getMensagem());
        return uuid;
    }
}