package utility.converter;

import dto.PessoaDTO;

public interface Converter<T,S>{

    S converterParaModel(T value);

    T converterParaDTO(S value);
}

