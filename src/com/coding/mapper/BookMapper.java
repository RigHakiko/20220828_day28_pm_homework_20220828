package com.coding.mapper;

import com.coding.model.Book;

import java.util.List;

public interface BookMapper {
    int addBookMany(List<Book> books);
}
