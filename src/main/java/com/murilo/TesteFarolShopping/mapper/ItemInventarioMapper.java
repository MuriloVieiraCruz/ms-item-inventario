package com.murilo.TesteFarolShopping.mapper;

import com.murilo.TesteFarolShopping.domains.ItemInventario;
import com.murilo.TesteFarolShopping.domains.enums.Disponibilidade;
import com.murilo.TesteFarolShopping.domains.enums.Status;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioResponseDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioSaveRequestDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioUpdateRequestDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItemInventarioMapper {

    public static ItemInventario toItemInventario(ItemInventarioSaveRequestDTO saveRequestDTO) {
        return new ItemInventario.Builder()
                .codigo(saveRequestDTO.getCodigo())
                .disponibilidade(Disponibilidade.DISPONIVEL)
                .status(Status.ATIVO)
                .descricao(saveRequestDTO.getDescricao())
                .localizacao(saveRequestDTO.getLocalizacao())
                .dataMovimentacao(LocalDateTime.now())
                .build();
    }

    public static ItemInventarioResponseDTO toItemInventarioResponse(ItemInventario itemInventario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataMovimentacaoFormatada = itemInventario.getDataMovimentacao().format(formatter);

        return new ItemInventarioResponseDTO(
                itemInventario.getId(),
                itemInventario.getCodigo(),
                itemInventario.getNumeroSerie(),
                itemInventario.getDisponibilidade().getDescricaoFormatada(),
                itemInventario.getStatus().getDescricaoFormatada(),
                itemInventario.getDescricao(),
                itemInventario.getLocalizacao(),
                dataMovimentacaoFormatada);
    }

    public static ItemInventario mapeiaValoresItemInventario(ItemInventarioUpdateRequestDTO updateRequestDTO, ItemInventario itemInventario) {
        itemInventario.setCodigo(updateRequestDTO.getCodigo());
        itemInventario.setDisponibilidade(Disponibilidade.encontrarPelaDescricao(updateRequestDTO.getDisponibilidade()));
        itemInventario.setDescricao(updateRequestDTO.getDescricao());
        itemInventario.setLocalizacao(updateRequestDTO.getLocalizacao());
        itemInventario.setDataMovimentacao(LocalDateTime.now());

        return itemInventario;
    }
}
