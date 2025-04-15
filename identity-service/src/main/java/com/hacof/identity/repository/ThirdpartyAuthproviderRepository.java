package com.hacof.identity.repository;

import com.hacof.identity.constant.ProviderName;
import com.hacof.identity.entity.ThirdpartyAuthprovider;
import com.hacof.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdpartyAuthproviderRepository extends JpaRepository<ThirdpartyAuthprovider, Long> {

    boolean existsByUserAndProviderName(User user, ProviderName providerName);

    Optional<ThirdpartyAuthprovider> findByUserAndProviderName(User user, ProviderName providerName);
}
