package utility.converter;


import dto.TarefaDTO;
import model.Tarefa;

public class ConverterTarefaImp implements Converter<TarefaDTO, Tarefa> {
    
    @Override
    public Tarefa converterParaModel(TarefaDTO tarefa) {
        return new Tarefa.Builder().id(tarefa.id().toString())
                .titulo(tarefa.titulo())
                .descricao(tarefa.descricao())
                .pessoaDTO(tarefa.pessoa())
                .dataHoraInicio(tarefa.dataHoraInicio())
                .dataHoraFim(tarefa.dataHoraFim())
                .tag(tarefa.tag())
                .build();
    }

    @Override
    public TarefaDTO converterParaDTO(Tarefa tarefa) {
        return new TarefaDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPessoaDTO(),
                tarefa.getDataHoraInicio(),
                tarefa.getDataHoraFim(),
                tarefa.getTag());
    }
}

