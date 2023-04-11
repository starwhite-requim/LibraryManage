package com.se.librarymanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.librarymanagesystem.entity.Book;
import com.se.librarymanagesystem.mapper.BookMapper;
import com.se.librarymanagesystem.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
}
