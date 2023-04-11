package com.se.librarymanagesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.se.librarymanagesystem.dto.BookDto;
import com.se.librarymanagesystem.entity.Book;

public interface BookService extends IService<Book> {
    public BookDto queryBookAndTag(Long id);
}
