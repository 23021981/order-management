package com.recruitment.assessment.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BOOKITEM")
public class BookItem {
    @Id
    @Column(name = "bookId")
    String bookId;
    @Column(name = "quantity")
    Long quantity;
}
