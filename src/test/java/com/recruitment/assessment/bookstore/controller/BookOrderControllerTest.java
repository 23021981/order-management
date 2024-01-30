package com.recruitment.assessment.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitment.assessment.bookstore.model.BookInventory;
import com.recruitment.assessment.bookstore.model.BookItem;
import com.recruitment.assessment.bookstore.model.BookOrder;
import com.recruitment.assessment.bookstore.model.BookOrderRequest;
import com.recruitment.assessment.bookstore.service.BookOrderService;
import com.recruitment.assessment.bookstore.utils.OrderUtils;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @InjectMocks
    BookOrderController bookOrderController;

    @Mock
    OrderUtils orderUtils;

    @Mock
    BookOrderService orderService;

    @Before
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(bookOrderController).build();
    }

    @Test
    public void testGetBookOrder() throws Exception {
        BookOrder bookOrder = new BookOrder();
        bookOrder.setOrderId("001");
        when(orderService.getBookOrder(any())).thenReturn(bookOrder);
        this.mockMvc.perform(get("/bookOrder/getBookOrder/{orderId}", "001").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    @Test
    public void testPlaceBookOrder() throws Exception {
        BookOrder bookOrder = new BookOrder();
        bookOrder.setOrderId("O001");
        bookOrder.setOrderDate(LocalDate.now().toString());
        bookOrder.setBookItems(new HashSet<>());

        BookInventory bookInventory = new BookInventory();
        List<BookInventory> bookInventoryList = new ArrayList<>();
        bookInventoryList.add(bookInventory);

        Pair<BookOrder, List<BookInventory>> orderInventoryPair = new Pair<>(new BookOrder(), new ArrayList<>());
        when(orderUtils.convertRequestToBookOrder(any(), any())).thenReturn(orderInventoryPair);
        when(orderService.placeBookOrder(any())).thenReturn(bookOrder);
        doNothing().when(orderUtils).doPostPostForObject("updateBookInventoryUrl", bookInventoryList);
        BookOrderRequest bookOrderRequest = new BookOrderRequest();
        bookOrderRequest.setOrderId("O001");
        BookItem items = new BookItem();
        items.setBookId("001");
        items.setBookName("Test Book");
        items.setQuantity(2L);
        List<BookItem> bookItemList = new ArrayList<>();
        bookItemList.add(items);
        bookOrderRequest.setBookItemList(bookItemList);

        MvcResult mvcResult = mockMvc.perform(post("/bookOrder/placeBookOrder")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookOrderRequest)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(bookOrder);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);

    }

    @Test
    public void testUpdateBookOrder() throws Exception {
        BookOrderRequest bookOrderRequest = new BookOrderRequest();
        bookOrderRequest.setOrderId("O001");

        BookItem items = new BookItem();
        items.setBookId("001");
        items.setBookName("Test Book");
        items.setQuantity(2L);
        List<BookItem> bookItemList = new ArrayList<>();
        bookItemList.add(items);
        bookOrderRequest.setBookItemList(bookItemList);

        BookInventory bookInventory = new BookInventory();
        List<BookInventory> bookInventoryList = new ArrayList<>();
        bookInventoryList.add(bookInventory);

        BookOrder bookOrder = new BookOrder();
        bookOrder.setOrderId("O001");
        bookOrder.setOrderDate(LocalDate.now().toString());
        bookOrder.setBookItems(new HashSet<>());

        Pair<BookOrder, List<BookInventory>> orderInventoryPair = new Pair<>(new BookOrder(), new ArrayList<>());
        when(orderUtils.convertRequestToBookOrder(any(), any())).thenReturn(orderInventoryPair);
        when(orderService.placeBookOrder(any())).thenReturn(bookOrder);
        doNothing().when(orderUtils).doPostPostForObject("updateBookInventoryUrl", bookInventoryList);

        MvcResult mvcResult = mockMvc.perform(put("/bookOrder/updateBookOrder")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookOrderRequest)))
                .andExpect(status().isOk()
                ).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(bookOrder);
        assertThat(expectedResponseBody).isEqualToIgnoringWhitespace(actualResponseBody);

    }

    @Test
    public void testDeleteBookOrder() throws Exception {
        this.mockMvc.perform(delete("/bookOrder/deleteBookOrder/{orderId}", "001").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
