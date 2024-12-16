package com.murilo.TesteFarolShopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioSaveRequestDTO;
import com.murilo.TesteFarolShopping.dtos.ItemInventarioUpdateRequestDTO;
import com.murilo.TesteFarolShopping.factory.ItemInventarioFactory;
import com.murilo.TesteFarolShopping.repositories.ItemInventarioRepository;
import com.murilo.TesteFarolShopping.services.ItemInventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemInventarioControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemInventarioRepository itemInventarioRepository;

    @Autowired
    private ItemInventarioService itemInventarioService;

    private final String baseUrl = "/api/v1/item-inventario";

    private Long existItemId;
    private Long nonExitedItemId;
    private ItemInventarioSaveRequestDTO itemInventarioSaveRequestDTO;
    private ItemInventarioUpdateRequestDTO itemInventarioUpdateRequestDTO;

    @BeforeEach
    void setup() {
        itemInventarioRepository.deleteAll();
        itemInventarioSaveRequestDTO = ItemInventarioFactory.getItemInventarioSaveRequestInstance();
        itemInventarioUpdateRequestDTO = ItemInventarioFactory.getItemInventarioUpdateRequestInstance();
        existItemId = itemInventarioService.create(itemInventarioSaveRequestDTO).getId();
        nonExitedItemId = 999L;
    }

    @Nested
    class create {

        @Test
        void casoItemInseridoComSucesso() throws Exception {
            itemInventarioSaveRequestDTO.setCodigo("123456b");

            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.codigo").value("123456b"))
                    .andExpect(jsonPath("$.numeroSerie").value("00002"))
                    .andExpect(jsonPath("$.disponibilidade").value("D"))
                    .andExpect(jsonPath("$.status").value("A"))
                    .andExpect(jsonPath("$.descricao").value("Teste Descrição"))
                    .andExpect(jsonPath("$.localizacao").value("Teste Localização"))
                    .andExpect(jsonPath("$.dataMovimentacao").exists());

            assertFalse(itemInventarioRepository.findAll().isEmpty(), "Item de inventário não encontrado no sistema");
        }

        @Test
        void casoItemInseridoComCodigoExistente() throws Exception {
            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("004"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("Já existe um item com o mesmo código"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(IllegalArgumentException.class);
                    });
        }

        @Test
        void casoItemInseridoComCodigoVazio() throws Exception {
            itemInventarioSaveRequestDTO.setCodigo("");

            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O código é obrigatório"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComCodigoNulo() throws Exception {
            itemInventarioSaveRequestDTO.setCodigo(null);

            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O código é obrigatório"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComDescricaoVazio() throws Exception {
            itemInventarioSaveRequestDTO.setDescricao("");

            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A descrição é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComDescricaoNulo() throws Exception {
            itemInventarioSaveRequestDTO.setDescricao(null);

            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A descrição é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComLocalizacaoVazio() throws Exception {
            itemInventarioSaveRequestDTO.setLocalizacao("");

            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A localização é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComLocalizacaoNulo() throws Exception {
            itemInventarioSaveRequestDTO.setLocalizacao(null);

            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioSaveRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A localização é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemSemDados() throws Exception {
            mockMvc.perform(post(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("001"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O corpo (body) da requisição possui erros ou não existe"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(HttpMessageNotReadableException.class);
                    });
        }
    }

    @Nested
    class update {

        @Test
        void casoItemAlteradoComSucesso() throws Exception {
            itemInventarioUpdateRequestDTO.setId(existItemId);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.codigo").value("123456b"))
                    .andExpect(jsonPath("$.numeroSerie").value("00001"))
                    .andExpect(jsonPath("$.disponibilidade").value("D"))
                    .andExpect(jsonPath("$.status").value("A"))
                    .andExpect(jsonPath("$.descricao").value("Teste Alteração Descrição"))
                    .andExpect(jsonPath("$.localizacao").value("Teste Alteração Localização"))
                    .andExpect(jsonPath("$.dataMovimentacao").exists());
        }

        @Test
        void casoItemInseridoComIdNaoExistente() throws Exception {
            itemInventarioUpdateRequestDTO.setId(nonExitedItemId);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("004"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O item informado não existe"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(IllegalArgumentException.class);
                    });
        }

        @Test
        void casoItemInseridoComIdNulo() throws Exception {
            itemInventarioUpdateRequestDTO.setId(null);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O id do item é obrigatório"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComCodigoExistente() throws Exception {
            itemInventarioSaveRequestDTO.setCodigo("123456b");
            Long segundoIdInserido = itemInventarioService.create(itemInventarioSaveRequestDTO).getId();
            itemInventarioUpdateRequestDTO.setCodigo("123456a");
            itemInventarioUpdateRequestDTO.setId(segundoIdInserido);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("004"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O código informado já existe em outro item de inventario"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(IllegalArgumentException.class);
                    });
        }

        @Test
        void casoItemInseridoComCodigoVazio() throws Exception {
            itemInventarioUpdateRequestDTO.setCodigo("");

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O código é obrigatório"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComCodigoNulo() throws Exception {
            itemInventarioUpdateRequestDTO.setCodigo(null);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O código é obrigatório"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComDisponibilidadeNulo() throws Exception {
            itemInventarioUpdateRequestDTO.setId(existItemId);
            itemInventarioUpdateRequestDTO.setDisponibilidade('\u0000');

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("004"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A disponibilidade é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(IllegalArgumentException.class);
                    });
        }

        @Test
        void casoItemInseridoComDisponibilidadeErrada() throws Exception {
            itemInventarioUpdateRequestDTO.setDisponibilidade('S');
            itemInventarioUpdateRequestDTO.setId(existItemId);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("004"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("Disponibilidade inexistente: S, informar I para indisponível ou D para disponível"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(IllegalArgumentException.class);
                    });
        }

        @Test
        void casoItemInseridoComDescricaoVazio() throws Exception {
            itemInventarioUpdateRequestDTO.setDescricao("");

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A descrição é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComDescricaoNulo() throws Exception {
            itemInventarioUpdateRequestDTO.setDescricao(null);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A descrição é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComLocalizacaoVazio() throws Exception {
            itemInventarioUpdateRequestDTO.setLocalizacao("");

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A localização é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemInseridoComLocalizacaoNulo() throws Exception {
            itemInventarioUpdateRequestDTO.setLocalizacao(null);

            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemInventarioUpdateRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("018"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A localização é obrigatória"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentNotValidException.class);
                    });
        }

        @Test
        void casoItemSemDados() throws Exception {
            mockMvc.perform(put(baseUrl)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{}"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class findById {

        @Test
        void casoItemIdComSucesso() throws Exception {
            mockMvc.perform(get(baseUrl + "/" + existItemId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.codigo").value("123456a"))
                    .andExpect(jsonPath("$.numeroSerie").value("00001"))
                    .andExpect(jsonPath("$.disponibilidade").value("D"))
                    .andExpect(jsonPath("$.status").value("A"))
                    .andExpect(jsonPath("$.descricao").value("Teste Descrição"))
                    .andExpect(jsonPath("$.localizacao").value("Teste Localização"))
                    .andExpect(jsonPath("$.dataMovimentacao").exists());
        }

        @Test
        void casoItemIdNulo() throws Exception {
            mockMvc.perform(get(baseUrl + "/" + null))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("007"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A URI possui valores inválidos"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentTypeMismatchException.class);
                    });
        }

        @Test
        void casoItemIdNaoEncontrado() throws Exception {
            mockMvc.perform(get(baseUrl + "/" + nonExitedItemId))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("004"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O item informado não existe"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(IllegalArgumentException.class);
                    });
        }
    }

    @Nested
    class findAll {
        @Test
        void casoItensEncontradosComSucesso() throws Exception {
            mockMvc.perform(get(baseUrl + "/listAll"))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class deleteById {

        @Test
        void casoItemDeletadoComSucesso() throws Exception {
            mockMvc.perform(delete(baseUrl + "/" + existItemId))
                    .andExpect(status().isOk());
        }

        @Test
        void casoItemIdNulo() throws Exception {
            mockMvc.perform(delete(baseUrl + "/" + "{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("007"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("A URI possui valores inválidos"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(MethodArgumentTypeMismatchException.class);
                    });
        }

        @Test
        void casoItemNaoEncontrado() throws Exception {
            mockMvc.perform(delete(baseUrl + "/" + nonExitedItemId))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.erros[0].codigo").value("004"))
                    .andExpect(jsonPath("$.erros[0].mensagem").value("O item informado não existe"))
                    .andExpect(result -> {
                        Throwable resolvedException = result.getResolvedException();
                        assertThat(resolvedException).isInstanceOf(IllegalArgumentException.class);
                    });
        }
    }
}
