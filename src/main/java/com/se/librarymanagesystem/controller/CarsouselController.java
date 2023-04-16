package com.se.librarymanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.se.librarymanagesystem.common.R;
import com.se.librarymanagesystem.entity.Carousel;
import com.se.librarymanagesystem.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class CarsouselController {
    @Autowired
    CarouselService carouselService;
    @PostMapping("/carousel")
    /**
     * 返回轮播图表中的前4条数据
     */
    public R getCarouselList(){
        List<Carousel> list = null;
        Page<Carousel> page = new Page<>(1,4);
        LambdaQueryWrapper<Carousel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Carousel::getCarouselId);
        carouselService.page(page,queryWrapper);
        list = page.getRecords();
        return R.success(list);

    }
}
