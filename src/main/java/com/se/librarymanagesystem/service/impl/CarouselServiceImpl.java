package com.se.librarymanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.librarymanagesystem.entity.Carousel;
import com.se.librarymanagesystem.mapper.CarouselMapper;
import com.se.librarymanagesystem.service.CarouselService;
import org.springframework.stereotype.Service;

@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {
}
