package com.murilo.TesteFarolShopping.controllers;

import com.murilo.TesteFarolShopping.domains.ItemInventario;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioResponseDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioSaveRequestDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioUpdateRequestDTO;
import com.murilo.TesteFarolShopping.mapper.ItemInventarioMapper;
import com.murilo.TesteFarolShopping.services.ItemInventarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/item-inventario")
public class ItemInventarioController {

    private final ItemInventarioService itemInventarioService;

    ItemInventarioController(ItemInventarioService itemInventarioService) {
        this.itemInventarioService = itemInventarioService;
    }

    @PostMapping
    public ResponseEntity<ItemInventarioResponseDTO> create(@RequestBody @Valid ItemInventarioSaveRequestDTO itemRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ItemInventarioMapper.toItemInventarioResponse(itemInventarioService.create(itemRequestDTO)));
    }

    @PutMapping
    public ResponseEntity<ItemInventarioResponseDTO> update(@RequestBody @Valid ItemInventarioUpdateRequestDTO itemRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(ItemInventarioMapper.toItemInventarioResponse(itemInventarioService.update(itemRequestDTO)));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemInventarioResponseDTO> findById(@PathVariable("itemId") Long itemId) {
        return ResponseEntity.status(HttpStatus.OK).body(ItemInventarioMapper.toItemInventarioResponse(itemInventarioService.findById(itemId)));
    }

    @GetMapping("/listAll")
    public ResponseEntity<Page<ItemInventarioResponseDTO>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<ItemInventario> paginaOriginal = itemInventarioService.findAll(page, size);
        List<ItemInventarioResponseDTO> dtoList = paginaOriginal.getContent().stream()
                .map(ItemInventarioMapper::toItemInventarioResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(
                new PageImpl<>(dtoList, paginaOriginal.getPageable(), paginaOriginal.getTotalElements())
        );
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable("itemId") Long itemId) {
        itemInventarioService.deleteById(itemId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
