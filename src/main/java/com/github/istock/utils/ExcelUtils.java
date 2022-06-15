package com.github.istock.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

import java.util.List;

/**
 * @author shuaixiaoji
 * @Description
 * @date 2022/6/15 17:21
 */
@Slf4j
public class ExcelUtils {

    public static <T> void simpleWrite(String fileName, List<T> data, Class<T> tClass) {
        ExcelWriter excelWriter = EasyExcel.write(fileName, tClass).build();
        // 创建sheet
        WriteSheet writeSheet = EasyExcel.writerSheet("sheetName").build();
        try {
            // 每次写入4000条
            if (data.size() > 4000) {
                List<List<T>> pageList = ListUtils.partition(data, 4000);
                for (int i = 0; i < pageList.size(); i++) {
                    excelWriter.write(pageList.get(i), writeSheet);
                }
                return;
            }
            excelWriter.write(data, writeSheet);
        } catch (Exception e){
            log.error("ExcelUtils.simpleWrite error,exception is {}",e);
        } finally {
            excelWriter.finish();
        }
    }
}
