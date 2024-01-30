package com.recruitment.assessment.bookstore.repository;

import com.recruitment.assessment.bookstore.model.BookOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends CrudRepository<BookOrder,String> {
}
