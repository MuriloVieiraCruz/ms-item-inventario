package com.murilo.TesteFarolShopping.service;

import com.murilo.TesteFarolShopping.domains.ItemInventario;
import com.murilo.TesteFarolShopping.domains.enums.Status;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioSaveRequestDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioUpdateRequestDTO;
import com.murilo.TesteFarolShopping.factory.ItemInventarioFactory;
import com.murilo.TesteFarolShopping.mapper.ItemInventarioMapper;
import com.murilo.TesteFarolShopping.repositories.ItemInventarioRepository;
import com.murilo.TesteFarolShopping.services.ItemInventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ItemInventarioServiceTest {

    @InjectMocks
    private ItemInventarioService itemInventarioService;

    @Mock
    private ItemInventarioRepository itemInventarioRepository;

    @Mock
    private ItemInventarioMapper mapper;

    private Long existItemInventarioId;
    private Long nonExistItemInventarioId;
    private String existCodigo;
    private String nonExistCodigo;
    private ItemInventarioSaveRequestDTO itemInventarioSaveRequestDTO;
    private ItemInventarioUpdateRequestDTO itemInventarioUpdateRequestDTO;
    private ItemInventario itemInventario;

    @BeforeEach
    void setup() {
        itemInventarioSaveRequestDTO = ItemInventarioFactory.getItemInventarioSaveRequestInstance();
        itemInventarioUpdateRequestDTO = ItemInventarioFactory.getItemInventarioUpdateRequestInstance();
        itemInventario = ItemInventarioFactory.getItemInventarioInstance();
        existItemInventarioId = 1L;
        nonExistItemInventarioId = 999L;
        existCodigo = "123456a";
    }

    @Nested
    class create {

        @Test
        void casoItemInseridoComSucesso() {
            when(itemInventarioRepository.existsByCodigo(
                    eq(itemInventarioSaveRequestDTO.getCodigo())
            )).thenReturn(false);

            when(itemInventarioRepository.save(any()))
                    .thenReturn(itemInventario);

            ItemInventario response = itemInventarioService.create(itemInventarioSaveRequestDTO);

            assertNotNull(response);
            assertEquals(itemInventario.getId(), response.getId());
            assertEquals(itemInventario.getCodigo(), response.getCodigo());
            assertEquals(itemInventario.getNumeroSerie(), response.getNumeroSerie());
            assertEquals(itemInventario.getDisponibilidade(), response.getDisponibilidade());
            assertEquals(itemInventario.getStatus(), response.getStatus());
            assertEquals(itemInventario.getDescricao(), response.getDescricao());
            assertEquals(itemInventario.getLocalizacao(), response.getLocalizacao());
            assertNotNull(response.getDataMovimentacao());

            verify(itemInventarioRepository, atLeastOnce()).existsByCodigo(
                    eq(itemInventarioSaveRequestDTO.getCodigo())
            );
            verify(itemInventarioRepository, atLeastOnce()).save(any());
        }

        @Test
        void casoItemInseridoComCodigoExistente() throws Exception {
            when(itemInventarioRepository.existsByCodigo(
                    eq(existCodigo)
            )).thenReturn(true);

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.create(itemInventarioSaveRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).existsByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemInseridoComCodigoExcedendoCaracteres() throws Exception {
            itemInventarioSaveRequestDTO.setCodigo("1234567a");

            when(itemInventarioRepository.existsByCodigo(
                    eq(itemInventarioSaveRequestDTO.getCodigo())
            )).thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.create(itemInventarioSaveRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).existsByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
        }

        @Test
        void casoItemInseridoComDescricaoExcedendoCaracteres() throws Exception {
            itemInventarioSaveRequestDTO.setDescricao("a".repeat(101));

            when(itemInventarioRepository.existsByCodigo(
                    eq(itemInventarioSaveRequestDTO.getCodigo())
            )).thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.create(itemInventarioSaveRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).existsByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
        }

        @Test
        void casoItemInseridoComLocalizacaoExcedendoCaracteres() throws Exception {
            itemInventarioSaveRequestDTO.setLocalizacao("a".repeat(251));

            when(itemInventarioRepository.existsByCodigo(
                    eq(itemInventarioSaveRequestDTO.getCodigo())
            )).thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.create(itemInventarioSaveRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).existsByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
        }

        @Test
        void testCaseExceptionTriggersRollbackCreate() {
            when(itemInventarioRepository.save(any()))
                    .thenThrow(RuntimeException.class);

            assertThrows(RuntimeException.class, () -> itemInventarioService.create(itemInventarioSaveRequestDTO));

            verify(itemInventarioRepository).save(any());
        }
    }

    @Nested
    class update {

        @Test
        void casoItemAlteradoComSucesso() {
            when(itemInventarioRepository.findByIdAndStatus(
                    eq(existItemInventarioId),
                    eq(Status.ATIVO)
            )).thenReturn(Optional.of(itemInventario));

            when(itemInventarioRepository.findByCodigo(
                    any()
            )).thenReturn(Optional.empty());

            when(itemInventarioRepository.save(
                    any()
            )).thenReturn(itemInventario);

            ItemInventario response = itemInventarioService.update(itemInventarioUpdateRequestDTO);

            assertNotNull(response);
            assertEquals(1L, response.getId());
            assertEquals("123456b", response.getCodigo());
            assertEquals("00001", response.getNumeroSerie());
            assertEquals("D", String.valueOf(response.getDisponibilidade().getDescricaoFormatada()));
            assertEquals("A", String.valueOf(response.getStatus().getDescricaoFormatada()));
            assertEquals("Teste Alteração Descrição", response.getDescricao());
            assertEquals("Teste Alteração Localização", response.getLocalizacao());
            assertNotNull(response.getDataMovimentacao());

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, atLeastOnce()).findByCodigo(any());
            verify(itemInventarioRepository, atLeastOnce()).save(any());
        }

        @Test
        void casoItemIdNulo() {
            itemInventarioUpdateRequestDTO.setId(null);

            assertThrows(NullPointerException.class, () -> itemInventarioService.update(itemInventarioUpdateRequestDTO));

            verify(itemInventarioRepository, never()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, never()).findByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemIdNaoEncontrado() {
            itemInventarioUpdateRequestDTO.setId(nonExistItemInventarioId);

            when(itemInventarioRepository.findByIdAndStatus(
                    any(),
                    any()
            )).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.findById(nonExistItemInventarioId));

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, never()).findByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemInseridoComCodigoExistente() throws Exception {
            ItemInventario itemExistente = new ItemInventario();
            itemExistente.setId(50L);
            itemExistente.setCodigo(itemInventarioUpdateRequestDTO.getCodigo());

            when(itemInventarioRepository.findByIdAndStatus(
                    any(),
                    any()
            )).thenReturn(Optional.of(itemInventario));

            when(itemInventarioRepository.findByCodigo(
                    any()
            )).thenReturn(Optional.of(itemExistente));

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.update(itemInventarioUpdateRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, atLeastOnce()).findByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemInseridoComCodigoExcedendoCaracteres() throws Exception {
            itemInventarioUpdateRequestDTO.setCodigo("1234567a");

            when(itemInventarioRepository.findByIdAndStatus(
                    any(),
                    any()
            )).thenReturn(Optional.of(itemInventario));

            when(itemInventarioRepository.findByCodigo(
                    any()
            )).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.update(itemInventarioUpdateRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, atLeastOnce()).findByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemInseridoComDescricaoExcedendoCaracteres() throws Exception {
            itemInventarioUpdateRequestDTO.setDescricao("a".repeat(101));

            when(itemInventarioRepository.findByIdAndStatus(
                    eq(existItemInventarioId),
                    eq(Status.ATIVO)
            )).thenReturn(Optional.of(itemInventario));

            when(itemInventarioRepository.findByCodigo(
                    any()
            )).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.update(itemInventarioUpdateRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, atLeastOnce()).findByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemInseridoComLocalizacaoExcedendoCaracteres() throws Exception {
            itemInventarioUpdateRequestDTO.setLocalizacao("a".repeat(251));

            when(itemInventarioRepository.findByIdAndStatus(
                    eq(existItemInventarioId),
                    eq(Status.ATIVO)
            )).thenReturn(Optional.of(itemInventario));

            when(itemInventarioRepository.findByCodigo(
                    any()
            )).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.update(itemInventarioUpdateRequestDTO));

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, atLeastOnce()).findByCodigo(any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }
    }

    @Nested
    class findById {

        @Test
        void casoItemIdComSucesso() {
            when(itemInventarioRepository.findByIdAndStatus(
                    eq(existItemInventarioId),
                    eq(Status.ATIVO)
            )).thenReturn(Optional.of(itemInventario));

            ItemInventario response = itemInventarioService.findById(existItemInventarioId);

            assertNotNull(response);
            assertEquals(itemInventario.getId(), response.getId());
            assertEquals(itemInventario.getCodigo(), response.getCodigo());
            assertEquals(itemInventario.getNumeroSerie(), response.getNumeroSerie());
            assertEquals(itemInventario.getDisponibilidade(), response.getDisponibilidade());
            assertEquals(itemInventario.getStatus(), response.getStatus());
            assertEquals(itemInventario.getDescricao(), response.getDescricao());
            assertEquals(itemInventario.getLocalizacao(), response.getLocalizacao());
            assertNotNull(response.getDataMovimentacao());

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(
                    eq(existItemInventarioId),
                    eq(Status.ATIVO)
            );
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemIdNulo() {
            assertThrows(NullPointerException.class, () -> itemInventarioService.findById(null));

            verify(itemInventarioRepository, never()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemIdNaoEncontrado() {
            when(itemInventarioRepository.findByIdAndStatus(
                    eq(nonExistItemInventarioId),
                    eq(Status.ATIVO)
            )).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.findById(nonExistItemInventarioId));

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(
                    eq(nonExistItemInventarioId),
                    eq(Status.ATIVO)
            );
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }
    }

    @Nested
    class deleteById {

        @Test
        void casoItemDeletadoComSucesso() {
            when(itemInventarioRepository.findByIdAndStatus(
                    eq(existItemInventarioId),
                    eq(Status.ATIVO)
            )).thenReturn(Optional.of(itemInventario));

            when(itemInventarioRepository.save(any())).thenReturn(itemInventario);

            itemInventarioService.deleteById(existItemInventarioId);

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(
                    eq(existItemInventarioId),
                    eq(Status.ATIVO)
            );
            verify(itemInventarioRepository, atLeastOnce()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemIdNulo() {
            assertThrows(NullPointerException.class, () -> itemInventarioService.deleteById(null));

            verify(itemInventarioRepository, never()).findByIdAndStatus(any(), any());
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }

        @Test
        void casoItemNaoEncontrado() {
            when(itemInventarioRepository.findByIdAndStatus(
                    eq(nonExistItemInventarioId),
                    eq(Status.ATIVO)
            )).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> itemInventarioService.deleteById(nonExistItemInventarioId));

            verify(itemInventarioRepository, atLeastOnce()).findByIdAndStatus(
                    eq(nonExistItemInventarioId),
                    eq(Status.ATIVO)
            );
            verify(itemInventarioRepository, never()).save(any());
            verifyNoMoreInteractions(itemInventarioRepository);
        }
    }
}
