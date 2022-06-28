package ru.learnup.homework27.dao;

import org.springframework.stereotype.Component;
import ru.learnup.homework27.entity.Buyer;

import ru.learnup.homework27.repository.BuyerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SpringDataBuyerDao {

        private final BuyerRepository repository;

    public SpringDataBuyerDao(BuyerRepository repository) {
        this.repository = repository;
    }


    public Buyer findById(int id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Buyer> findAll() {
        List<Buyer> buyersRes = new ArrayList<>();
        Iterable<Buyer> buyers = repository.findAll();
        buyers.forEach(buyersRes::add);
        return buyersRes;
    }

    public void save(Buyer buyer ) {
        repository.save(buyer);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
