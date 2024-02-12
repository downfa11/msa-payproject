package org.example.money.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMoneyChangingRepository extends JpaRepository<MoneyChangingRequestJpaEntity,Long> {
}
