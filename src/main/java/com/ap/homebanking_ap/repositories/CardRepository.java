package com.ap.homebanking_ap.repositories;

import com.ap.homebanking_ap.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository <Card,Long>{
    boolean existsByNumber (String number);
}

