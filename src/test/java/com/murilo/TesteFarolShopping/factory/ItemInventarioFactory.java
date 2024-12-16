package com.murilo.TesteFarolShopping.factory;

import com.murilo.TesteFarolShopping.domains.ItemInventario;
import com.murilo.TesteFarolShopping.domains.enums.Disponibilidade;
import com.murilo.TesteFarolShopping.domains.enums.Status;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioSaveRequestDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioUpdateRequestDTO;

import java.time.LocalDateTime;

public class ItemInventarioFactory {

    public static ItemInventario getItemInventarioInstance() {
        return new ItemInventario.Builder()
                .id(1L)
                .codigo("123456a")
                .numeroSerie("00001")
                .disponibilidade(Disponibilidade.DISPONIVEL)
                .status(Status.ATIVO)
                .descricao("Teste Descrição")
                .localizacao("Teste Localização")
                .dataMovimentacao(LocalDateTime.now())
                .build();
    }

    public static ItemInventarioSaveRequestDTO getItemInventarioSaveRequestInstance() {
        return new ItemInventarioSaveRequestDTO(
                "123456a",
                "Teste Descrição",
                "Teste Localização"
        );
    }

    public static ItemInventarioUpdateRequestDTO getItemInventarioUpdateRequestInstance() {
        return new ItemInventarioUpdateRequestDTO(
                1L,
                "123456b",
                Disponibilidade.DISPONIVEL.getDescricaoFormatada(),
                "Teste Alteração Descrição",
                "Teste Alteração Localização"
        );
    }

}
