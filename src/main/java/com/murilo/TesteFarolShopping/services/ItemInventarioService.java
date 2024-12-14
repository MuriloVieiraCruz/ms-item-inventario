package com.murilo.TesteFarolShopping.services;

import com.google.common.base.Preconditions;
import com.murilo.TesteFarolShopping.domains.ItemInventario;
import com.murilo.TesteFarolShopping.domains.enums.Disponibilidade;
import com.murilo.TesteFarolShopping.domains.enums.Status;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioSaveRequestDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioUpdateRequestDTO;
import com.murilo.TesteFarolShopping.mapper.ItemInventarioMapper;
import com.murilo.TesteFarolShopping.repositories.ItemInventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ItemInventarioService {

    private static final String CODIGO_INICIAL = "00001";
    private final ItemInventarioRepository itemInventarioRepository;

    ItemInventarioService(ItemInventarioRepository itemInventarioRepository) {
        this.itemInventarioRepository = itemInventarioRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public ItemInventario create(ItemInventarioSaveRequestDTO itemRequestDTO) {
        isCodigoExist(itemRequestDTO.getCodigo());
        String ultimoNumero = carregarUltimoNumeroSerie();
        String numeroSerie = gerarProximoNumeroSerie(ultimoNumero);

        Preconditions.checkArgument(numeroSerie.matches("\\d{5}"), "Número de série inválido");

        ItemInventario itemInventario = ItemInventarioMapper.toItemInventario(itemRequestDTO);
        itemInventario.setNumeroSerie(numeroSerie);
        return itemInventarioRepository.save(itemInventario);
    }

    @Transactional(rollbackFor = Exception.class)
    public ItemInventario update(ItemInventarioUpdateRequestDTO itemRequestDTO) {

        ItemInventario itemEncontrado = findById(itemRequestDTO.getId());
        verificaCodigoItem(itemRequestDTO);
        return ItemInventarioMapper.mapeiaValoresItemInventario(itemRequestDTO, itemEncontrado);
    }

    @Transactional(readOnly = true)
    public ItemInventario findById(Long itemId) {
        Preconditions.checkNotNull(itemId, "O id do item é necessário para consulta");

        Optional<ItemInventario> itemExistente = itemInventarioRepository.findByIdAndStatus(itemId, Status.ATIVO);
        Preconditions.checkArgument(itemExistente.isPresent(), "O item informado não existe");
        return itemExistente.get();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long itemId) {
        ItemInventario itemEncontrado = findById(itemId);
        itemEncontrado.setStatus(Status.INATIVO);
        itemEncontrado.setDisponibilidade(Disponibilidade.INDISPONIVEL);
        itemInventarioRepository.save(itemEncontrado);
    }

    private void isCodigoExist(String codigo) {
        boolean isCodigoExistente = itemInventarioRepository.existsByCodigo(codigo);
        Preconditions.checkArgument(!isCodigoExistente, "Já existe um item com o mesmo código");
    }

    private String carregarUltimoNumeroSerie() {
        return itemInventarioRepository.carregarUltimoNumeroSerie();
    }

    private String gerarProximoNumeroSerie(String ultimoNumeroSerie) {
        if (Objects.isNull(ultimoNumeroSerie)) {
            return CODIGO_INICIAL;
        }

        int numeroAtual = Integer.parseInt(ultimoNumeroSerie);
        int proximoNumero = numeroAtual + 1;
        return String.format("%05d", proximoNumero);
    }

    private void verificaCodigoItem(ItemInventarioUpdateRequestDTO item) {
        Optional<ItemInventario> itemCarregado = itemInventarioRepository.findByCodigo(item.getCodigo());
        itemCarregado.ifPresent(itemInventario -> Preconditions.checkArgument(itemInventario.getId().equals(item.getId()), "O código informado já existe em outro item de inventario"));
    }
}