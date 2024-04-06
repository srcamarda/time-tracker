package service;

import dto.PessoaDTO;
import mockup.Mockup;

import java.util.Optional;

public class PessoaService {
    public void criarPessoa(){}

    public PessoaDTO buscarPessoa(String username) {
        String usernameSearch = username.trim().toLowerCase();

        Optional<PessoaDTO> optionalPessoaDTO = Mockup.pessoaDTOList.stream()
                .filter(pessoaDTO -> pessoaDTO.username().equals(usernameSearch))
                .findFirst();

        if (optionalPessoaDTO.isPresent()) {
            return optionalPessoaDTO.get();
        } else {
            throw new IllegalArgumentException("Usuário não existe");
        }
    }
}
