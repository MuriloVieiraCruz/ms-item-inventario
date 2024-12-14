package com.murilo.TesteFarolShopping.config;

import com.murilo.TesteFarolShopping.domains.enums.Status;

import javax.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status != null) {
            return String.valueOf(status.getDescricaoFormatada());
        }

        return null;
    }

    @Override
    public Status convertToEntityAttribute(String descricaoFormatada) {
        if (descricaoFormatada != null && !descricaoFormatada.isBlank()) {
            return Status.encontrarPelaDescricao(descricaoFormatada.charAt(0));
        }

        return null;
    }
}
