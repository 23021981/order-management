package com.recruitment.assessment.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BOOKORDER")
public class BookOrder {
    @Id
    @Column(name = "orderId")
    String orderId;

    @Column(name = "orderBy")
    String orderBy;
    @Column(name = "orderDate")
    String orderDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    Set<BookItem> bookItems;
}
