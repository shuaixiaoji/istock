package com.github.istock.entity;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author shuaixiaoji
 * @Description 评分 & 关注度
 * @date 2022/6/16 22:47
 */
@Data
public class MonthlyAnalysisEntity {

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

    // 10月均线价
    @ExcelProperty("10月均线价格")
    private BigDecimal month10Avg;

    // 30月均线价
    @ExcelProperty("30月均线价格")
    private BigDecimal month30Avg;

    @ExcelProperty("现价与30日价百分差")
    private BigDecimal gapRate;

    @ExcelProperty("流通市值(单位：亿)")
    private BigDecimal circulateStock;

    @ExcelProperty("总股本(单位：亿)")
    private BigDecimal totalStock;
}
