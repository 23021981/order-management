package com.recruitment.assessment.bookstore.service;

import com.recruitment.assessment.bookstore.model.BookOrder;
import com.recruitment.assessment.bookstore.repository.BookOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookOrderServiceTest {

    @InjectMocks
    BookOrderServiceImpl bookOrderServiceTest;

    @Mock
    BookOrderRepository bookOrderRepository;

    @Test
    public void testPlaceBookOrder(){
        BookOrder bookOrderTest = new BookOrder();
        bookOrderTest.setOrderId("Order01");
        when(bookOrderRepository.save(any())).thenReturn(bookOrderTest);
        assertEquals("Order01", bookOrderServiceTest.placeBookOrder(bookOrderTest).getOrderId());

    }

    @Test
    public void testGetBookOrder(){
        BookOrder bookOrderTest = new BookOrder();
        bookOrderTest.setOrderId("Order01");
        when(bookOrderRepository.findById(any())).thenReturn(Optional.of(bookOrderTest));
        assertEquals("Order01", bookOrderServiceTest.getBookOrder("Order01").getOrderId());

    }

    @Test
    public void testDeleteOrder(){
        doNothing().when(bookOrderRepository).deleteById("Order01");
        assertEquals("Deleted order Id : Order01 successfully !!", bookOrderServiceTest.deleteOrder("Order01"));

    }

    @Test
    public void testUpdateBookOrder(){
        BookOrder bookOrderTest = new BookOrder();
        bookOrderTest.setOrderId("Order01");
        when(bookOrderRepository.save(any())).thenReturn(bookOrderTest);
        assertEquals("Order01", bookOrderServiceTest.updateBookOrder(bookOrderTest).getOrderId());

    }
}
