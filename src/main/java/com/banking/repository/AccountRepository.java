package com.banking.repository;

import com.banking.model.entity.AccountEntity;
import com.banking.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
    List<AccountEntity> findByUserEntity(UserEntity userEntity);
    boolean existsByAccountNumber(String accountNumber);
}