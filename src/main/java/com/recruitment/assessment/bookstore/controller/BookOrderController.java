package com.recruitment.assessment.bookstore.controller;

import com.recruitment.assessment.bookstore.model.BookInventory;
import com.recruitment.assessment.bookstore.model.BookOrder;
import com.recruitment.assessment.bookstore.model.BookOrderRequest;
import com.recruitment.assessment.bookstore.service.BookOrderService;
import com.recruitment.assessment.bookstore.utils.OrderUtils;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookOrder")
public class BookOrderController {

    @Autowired
    BookOrderService orderService;

    @Value("${book.detail.inventory.get.Url}")
    String getBookInventoryUrl;

    @Value("${book.detail.inventory.update.Url}")
    String updateBookInventoryUrl;

    @Autowired
    OrderUtils orderUtils;

    @GetMapping(value = "/getBookOrder/{orderId}")
    public ResponseEntity<BookOrder> getBookOrder(@PathVariable("orderId") String bookId){
        BookOrder bookOrder = orderService.getBookOrder(bookId);
        return new ResponseEntity<>(bookOrder, HttpStatus.OK);
    }

    @PostMapping(path= "/placeBookOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookOrder> addBookOrder(@RequestBody BookOrderRequest orderRequest){
        Pair<BookOrder, List<BookInventory>> bookOrderInventoryPair = orderUtils.convertRequestToBookOrder(orderRequest, getBookInventoryUrl);
        BookOrder orderResponseEntity = orderService.placeBookOrder(bookOrderInventoryPair.getValue0());
        orderUtils.doPostPostForObject(updateBookInventoryUrl, bookOrderInventoryPair.getValue1());
        return new ResponseEntity<>(orderResponseEntity,HttpStatus.OK);
    }

    @DeleteMapping(value ="/deleteBookOrder/{bookId}")
    public ResponseEntity<String> deleteBookOrder(@PathVariable("bookId") String bookId){
        return new ResponseEntity<>(orderService.deleteOrder(bookId),HttpStatus.OK);
    }

    @PutMapping (value ="/updateBookOrder", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookOrder> updateBookOrder(@RequestBody BookOrderRequest orderRequest){
        Pair<BookOrder, List<BookInventory>> bookOrderInventoryPair = orderUtils.convertRequestToBookOrder(orderRequest, getBookInventoryUrl);
        BookOrder orderResponseEntity = orderService.placeBookOrder(bookOrderInventoryPair.getValue0());
        orderUtils.doPostPostForObject(updateBookInventoryUrl, bookOrderInventoryPair.getValue1());
        return new ResponseEntity<>(orderResponseEntity,HttpStatus.OK);
    }
}
