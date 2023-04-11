package com.se.librarymanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.librarymanagesystem.entity.BookTag;
import com.se.librarymanagesystem.mapper.BookTagMapper;
import com.se.librarymanagesystem.service.BookTagService;
import org.springframework.stereotype.Service;

@Service
public class BookTagServiceImpl extends ServiceImpl<BookTagMapper, BookTag> implements BookTagService {
}
