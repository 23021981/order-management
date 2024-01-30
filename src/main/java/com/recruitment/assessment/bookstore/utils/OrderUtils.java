package com.recruitment.assessment.bookstore.utils;

import com.recruitment.assessment.bookstore.model.BookInventory;
import com.recruitment.assessment.bookstore.model.BookItem;
import com.recruitment.assessment.bookstore.model.BookOrder;
import com.recruitment.assessment.bookstore.model.BookOrderRequest;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Component
public class OrderUtils {

    @Autowired
    RestTemplate restTemplate;


    public Pair<BookOrder, List<BookInventory>> convertRequestToBookOrder(BookOrderRequest orderRequest, String getBookInventoryUrl){
        BookOrder order = new BookOrder();
        order.setOrderId(orderRequest.getOrderId());
        order.setOrderBy(orderRequest.getOrderBy());
        order.setOrderDate(LocalDate.now().toString());
        Set<BookItem> bookItemSet = new HashSet<>();
        List<BookInventory> bookInventoryList = new ArrayList<>();
        BookInventory bookInventory = null;

        for(BookItem bookItem :orderRequest.getBookItemList()){
            ResponseEntity<BookInventory> bookInventoryResponse = restTemplate.exchange(getBookInventoryUrl+bookItem.getBookId(), HttpMethod.GET, null, BookInventory.class);
            if(Objects.nonNull(bookInventoryResponse))
                bookInventory = bookInventoryResponse.getBody();

            if(Objects.nonNull(bookInventory)){
                Long inStock = bookInventory.getInStock();
                if(inStock > bookItem.getQuantity()){
                    bookItemSet.add(bookItem);
                    bookInventory.setInStock(inStock - bookItem.getQuantity());
                    bookInventoryList.add(bookInventory);
                }
            }
        }
        order.setBookItems(bookItemSet);
        Pair<BookOrder, List<BookInventory>> bookOrderInventoryPair= new Pair<>(order, bookInventoryList);
        return bookOrderInventoryPair;
    }

    public void doPostPostForObject(String updateBookInventoryUrl, List<BookInventory> bookInventoryList){
        HttpEntity<List<BookInventory>> inventoryRequestEntity = new HttpEntity<>(bookInventoryList);
        restTemplate.postForObject(updateBookInventoryUrl,inventoryRequestEntity,List.class);
    }
}
