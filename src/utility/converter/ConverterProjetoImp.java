package utility.converter;


import dto.ProjetoDTO;
import model.Projeto;

public class ConverterProjetoImp implements Converter<ProjetoDTO, Projeto>{


    @Override
    public Projeto converterParaModel(ProjetoDTO projetoDTO) {
        return new Projeto.Builder()
                .id(projetoDTO.id().toString())
                .titulo(projetoDTO.titulo())
                .descricao(projetoDTO.descricao())
                .pessoasDTO(projetoDTO.pessoas())
                .dataHoraInicio(projetoDTO.dataHoraInicio())
                .dataHoraFim(projetoDTO.dataHoraFim())
                .tags(projetoDTO.tags())
                .tarefasDTO(projetoDTO.tarefas())
                .build();
    }

    @Override
    public ProjetoDTO converterParaDTO(Projeto projeto) {
        return  new ProjetoDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getPessoasDTO(),
                projeto.getDataHoraInicio(),
                projeto.getDataHoraFim(),
                projeto.getTags(),
                projeto.getTarefasDTO());
    }
}
