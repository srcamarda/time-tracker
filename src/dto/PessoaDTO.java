package dto;

import utility.TipoCargo;
import utility.TipoPlano;

import java.util.UUID;

public record PessoaDTO (UUID id,
                         String nome,
                         TipoPlano plano,
                         TipoCargo cargo) {}
