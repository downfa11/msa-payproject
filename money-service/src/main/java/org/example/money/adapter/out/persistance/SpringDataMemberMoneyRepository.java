package org.example.money.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataMemberMoneyRepository extends JpaRepository<MemberMoneyJpaEntity,Long> {
    List<MemberMoneyJpaEntity> findByMembershipId(@Param("membershipId") Long membershipId);
}
