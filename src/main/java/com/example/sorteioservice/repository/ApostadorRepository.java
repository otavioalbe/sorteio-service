package com.example.sorteioservice.repository;

import com.example.sorteioservice.entity.Apostador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApostadorRepository extends JpaRepository<Apostador, Long> {
}
