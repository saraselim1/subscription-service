package com.fntech.subscriptionservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fntech.subscriptionservice.domain.Subscription;

@Repository
public interface SubscriptionRepository  extends JpaRepository<Subscription, Long>{

	Optional<Subscription> findByIdAndUserNumber(long subscriptionId, String userNumber);

	Optional<Subscription> findByUserNumber(String userNumber);

}
