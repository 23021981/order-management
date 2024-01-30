package com.recruitment.assessment.bookstore.service;

import com.recruitment.assessment.bookstore.model.BookOrder;
import com.recruitment.assessment.bookstore.repository.BookOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderServiceImpl implements BookOrderService{

    @Autowired
    BookOrderRepository bookOrderRepository;

    @Override
    public BookOrder placeBookOrder(BookOrder bookOrder) {
        return bookOrderRepository.save(bookOrder);
    }

    @Override
    public BookOrder getBookOrder(String orderId) {

        return bookOrderRepository.findById(orderId).isPresent()?
                bookOrderRepository.findById(orderId).get():null;
    }

    @Override
    public String deleteOrder(String orderId) {
        bookOrderRepository.deleteById(orderId);
        return String.format("Deleted order Id : %s successfully !!", orderId);
    }

    @Override
    public BookOrder updateBookOrder(BookOrder bookOrder) {
        return bookOrderRepository.save(bookOrder);
    }


}
