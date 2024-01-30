package com.recruitment.assessment.bookstore.utils;

import com.recruitment.assessment.bookstore.model.BookInventory;
import com.recruitment.assessment.bookstore.model.BookItem;
import com.recruitment.assessment.bookstore.model.BookOrder;
import com.recruitment.assessment.bookstore.model.BookOrderRequest;
import com.recruitment.assessment.bookstore.service.BookOrderServiceImpl;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderUtilsTest {

    @Mock
    RestTemplate mockRestTemplate;

    @InjectMocks
    OrderUtils orderUtilsTest;

    @Test
    public void testConvertRequestToBookOrder(){

        String bookInventoryUrl = "http://localhost:8071/bookStore/getBookInventory/001";
        BookOrderRequest bookOrderRequest = new BookOrderRequest();
        BookItem items = new BookItem();
        items.setBookId("001");
        items.setBookName("Test Book");
        items.setQuantity(2L);
        List<BookItem> bookItemList = new ArrayList<>();
        bookItemList.add(items);
        bookOrderRequest.setBookItemList(bookItemList);
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookId("001");
        bookInventory.setInStock(10L);
        ResponseEntity<BookInventory> bookInventoryResponse = new ResponseEntity<>(bookInventory, HttpStatus.OK);
        when(mockRestTemplate.exchange(bookInventoryUrl, HttpMethod.GET, null, BookInventory.class)).thenReturn(bookInventoryResponse);
        Pair<BookOrder, List<BookInventory>> orderInventoryPair =  orderUtilsTest.convertRequestToBookOrder(bookOrderRequest, bookInventoryUrl);

        assertNotNull(orderInventoryPair.getValue0());
        assertNotNull(orderInventoryPair.getValue1());
    }

    @Test
    public void testDoPostPostForObject(){
        BookInventory bookInventory = new BookInventory();
        List<BookInventory> bookInventoryList = new ArrayList<>();
        bookInventoryList.add(bookInventory);
        HttpEntity<List<BookInventory>> inventoryRequestEntity = new HttpEntity<>(bookInventoryList);
        when(mockRestTemplate.postForObject("updateBookInventoryUrl",inventoryRequestEntity,List.class)).thenReturn(null);
        orderUtilsTest.doPostPostForObject("updateBookInventoryUrl", bookInventoryList);
    }
}
