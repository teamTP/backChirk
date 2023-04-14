package ru.vsu.cs.chirk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.chirk.entity.Chirk;

@Repository
public interface ChirkRepository extends JpaRepository<Chirk, Long> {
    
}
