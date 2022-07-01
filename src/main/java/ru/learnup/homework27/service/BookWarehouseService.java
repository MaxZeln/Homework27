package ru.learnup.homework27.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.learnup.homework27.dao.SpringDataBook_WarehouseDao;
import ru.learnup.homework27.entity.Book_Warehouse;
import ru.learnup.homework27.exeption.NotEnoughItems;
import ru.learnup.homework27.repository.Book_WarehouseRepository;

import javax.persistence.*;

@Service
@Slf4j
public class BookWarehouseService {

    private final SpringDataBook_WarehouseDao WareHouseDao;
    private final TransactionTemplate transactionTemplate;
    private final Book_WarehouseRepository repository;
    private final EntityManager entityManager;


    public BookWarehouseService(SpringDataBook_WarehouseDao wareHouseDao,
                                TransactionTemplate transactionTemplate,
                                Book_WarehouseRepository repository,
                                EntityManager entityManager) {
        this.WareHouseDao = wareHouseDao;
        this.transactionTemplate = transactionTemplate;
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_UNCOMMITTED,
            timeout = 3,
            readOnly = false,
            rollbackFor = {RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class}
    )
    public void transactionBuyABook(int id) {

//        book_warehouse.setBooks_amount(1);

        Book_Warehouse book_warehouse = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        try {

            if (book_warehouse.getBooks_amount() <= 0) {
                throw new NotEnoughItems("данная книга была куплена другим пользователем");
            }

            System.out.print("\nКнига: ");
            System.out.println(book_warehouse.getBook().getTitle());
            System.out.print("колличество на складе: ");
            System.out.println(book_warehouse.getBooks_amount());


            entityManager.lock(book_warehouse, LockModeType.OPTIMISTIC);

            int amount = book_warehouse.getBooks_amount() - 1;
            book_warehouse.setBooks_amount(amount);


            System.out.println(book_warehouse.getBooks_amount());

            System.out.println("\nТовар успешно преобретён, спасибо за покупку!");

        } catch (NotEnoughItems e) {
            System.out.println(book_warehouse.getBooks_amount());
            System.out.println(e);
            
        } catch (RollbackException e) {
            System.out.println("ГРУСТЬ, ПЕЧАЛЬ=" + book_warehouse);
            e.printStackTrace();
        }
    }

}
