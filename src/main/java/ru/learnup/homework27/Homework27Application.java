package ru.learnup.homework27;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.homework27.service.BookWarehouseService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Slf4j
public class Homework27Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Homework27Application.class, args);
        BookWarehouseService service1 = context.getBean(BookWarehouseService.class);
        BookWarehouseService service2 = context.getBean(BookWarehouseService.class);

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        pool.submit(new Runnable() {
            @Override
            public void run() {
                service2.transactionBuyABook(2);
            }
        });

        pool.submit(new Runnable() {
            @Override
            public void run() {
                service1.transactionBuyABook(2);
            }
        });

//        pool.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                service1.transactionBuyABook(2);
//            }
//        });



        //        pool.submit(new Runnable() {
//            @Override
//            public void run() {
//                service1.transactionBuyABook(2);
//            }
//        });
//
//        pool.submit(new Runnable() {
//            @Override
//            public void run() {
//                service2.transactionBuyABook(2);
//            }
//        });


//        new Thread(() -> service.transactionBuyABook(2)).start();
//        new Thread(() -> service.transactionBuyABook(2)).start();

        pool.shutdown();

    }
}
