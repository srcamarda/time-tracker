package utility.converter;

import dto.PessoaDTO;
import model.Pessoa;

public class ConverterPessoaImp implements Converter<PessoaDTO, Pessoa>{


    @Override
    public Pessoa converterParaModel(PessoaDTO pessoaDTO) {
        return new Pessoa.Builder()
                .id(pessoaDTO.id().toString())
                .username(pessoaDTO.username())
                .nome(pessoaDTO.nome())
                .plano(pessoaDTO.plano())
                .cargo(pessoaDTO.cargo())
                .build();
    }

    @Override
    public PessoaDTO converterParaDTO(Pessoa pessoa) {
        return new PessoaDTO(pessoa.getId(), pessoa.getUsername(), pessoa.getNome(), pessoa.getPlano(), pessoa.getCargo());
    }
}
