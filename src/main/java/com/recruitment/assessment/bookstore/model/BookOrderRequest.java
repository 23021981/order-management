package com.recruitment.assessment.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BookOrderRequest {
    String orderId;
    String orderBy;
    List<BookItem> bookItemList;
}
