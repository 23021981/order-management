package com.recruitment.assessment.bookstore.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookDetailRepositoryTest {

    @Autowired
    BookOrderRepository bookOrderRepository;

    @Test
    public void testAddAccountProduct(){
        assertFalse(bookOrderRepository.findById("001").isPresent());
      //  assertEquals("Atul",bookOrderRepository.findById("001").get().getOrderBy());
    }

}