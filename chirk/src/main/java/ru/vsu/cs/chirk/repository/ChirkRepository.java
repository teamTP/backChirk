package ru.vsu.cs.chirk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.chirk.entity.Chirk;
import ru.vsu.cs.chirk.entity.User;
import java.util.List;

@Repository
public interface ChirkRepository extends JpaRepository<Chirk, Long> {

    @NonNull
    Page<Chirk> findAllByIsVisibleOrderByDateDesc(@NonNull Pageable pageable, boolean isVisible);

    List<Chirk> findAllByUserOrderByDateDesc(User user);

    @NonNull
    Page<Chirk> findAllByUserOrderByDateDesc(User user, @NonNull Pageable pageable);

}
