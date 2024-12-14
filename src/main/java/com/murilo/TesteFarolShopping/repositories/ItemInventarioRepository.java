package com.murilo.TesteFarolShopping.repositories;

import com.murilo.TesteFarolShopping.domains.ItemInventario;
import com.murilo.TesteFarolShopping.domains.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {

    @Query("SELECT MAX(i.numeroSerie) FROM ItemInventario i")
    String carregarUltimoNumeroSerie();

    boolean existsByCodigo(String codigo);

    Optional<ItemInventario> findByCodigo(String codigo);

    Optional<ItemInventario> findByIdAndStatus(Long id, Status status);
}
