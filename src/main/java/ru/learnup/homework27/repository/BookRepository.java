package ru.learnup.homework27.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.homework27.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
