package ru.gb.Buy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.Buy.model.UserBank;

@Repository
public interface UserBankRepository extends JpaRepository<UserBank, Long> {
}
