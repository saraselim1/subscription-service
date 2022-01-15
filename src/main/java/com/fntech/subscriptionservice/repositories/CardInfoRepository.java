package com.fntech.subscriptionservice.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fntech.subscriptionservice.domain.CardInfo;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

	ArrayList<CardInfo> getAllCardsByUserNumber(String userNumber);

	int countByUserNumber(String userNumber);

	void deleteByIdAndUserNumber(long id, String userNumber);

	Optional<CardInfo> findByIdAndUserNumber(long id, String userNumber);

	Optional<CardInfo> findByCardNumber(String cardNumber);

	Optional<CardInfo> findByCardNumberAndUserNumber(String cardNumber, String userNumber);
}
