package com.recruitment.assessment.bookstore.service;

import com.recruitment.assessment.bookstore.model.BookOrder;

import java.util.List;

public interface BookOrderService {

    BookOrder placeBookOrder(BookOrder bookOrder);
    BookOrder getBookOrder(String orderId);

    String deleteOrder(String orderId);

    BookOrder updateBookOrder(BookOrder bookOrder);

}
