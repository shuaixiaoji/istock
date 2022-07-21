package com.github.istock.entity;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @author shuaixiaoji
 * @Description 下影响分析实体
 * @date 2022/6/16 22:47
 */
@Data
public class LowerShadowEntity {

    // 编码
    @ExcelProperty("编码")
    private String code;

    // 名称
    @ExcelProperty("名称")
    private String name;

    // 统计数据日期
    @ExcelProperty("统计时间")
    private String staticDate;

    // 当前价
    @ExcelProperty("当前价格")
    private BigDecimal recentPrice;


    @ExcelProperty("流通市值(单位：亿)")
    private BigDecimal circulateStock;

    @ExcelProperty("总股本(单位：亿)")
    private BigDecimal totalStock;
}
