package ru.learnup.homework27.repository;

import org.springframework.data.repository.CrudRepository;
import ru.learnup.homework27.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
