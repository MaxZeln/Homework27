package ru.learnup.homework27.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.homework27.entity.Buyer;

@Repository
public interface BuyerRepository extends CrudRepository <Buyer, Integer> {
}
