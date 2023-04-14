package ru.vsu.cs.chirk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.chirk.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
