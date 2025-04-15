package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RoundRepository extends JpaRepository<Round, Long>, JpaSpecificationExecutor<Round> {
    Optional<Round> findFirstByHackathonIdOrderByRoundNumberAsc(Long hackathonId);

    List<Round> findAllByHackathonId(Long hackathonId);

    boolean existsByRoundNumberAndHackathonIdAndIdNot(Integer roundNumber, Long hackathonId, Long roundId);

    Optional<Round> findByHackathonIdAndRoundNumber(Long hackathonId, int roundNumber);
}
