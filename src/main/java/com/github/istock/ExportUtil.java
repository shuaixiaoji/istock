package com.github.istock;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.istock.enums.InterfacesEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * util
 *
 * @author mac-huanglc
 * @since 2022/6/17
 */
@Slf4j
public class ExportUtil {

    public static void exportData(List<Object> data, String code) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        Map<String, List<Object>> dataMap = new HashMap<>(10);
        dataMap.put(code, data);

        Consumer<ExcelWriter> writerConsumer = excelWriter -> writeData(dataMap, excelWriter);
        
        String fileName = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_14) + ".xlsx";
        export("/Users/mac-huanglc/stock/" + fileName, writerConsumer);
        log.info("exportData end. name = {}", fileName);
    }    
    
    public static void exportData(Map<String, List<Object>> dataMap) {
        if (MapUtils.isEmpty(dataMap)) {
            return;
        }
        Consumer<ExcelWriter> writerConsumer = excelWriter -> writeData(dataMap, excelWriter);
        
        String fileName = DateUtils.format(new Date(), DateUtils.DATE_FORMAT_14) + ".xlsx";
        export("/Users/mac-huanglc/stock/" + fileName, writerConsumer);
        log.info("exportDataMap end. name = {}", fileName);
    }

    private static void writeData(Map<String, List<Object>> dataMap, ExcelWriter excelWriter) {
        if (MapUtils.isEmpty(dataMap)) {
            return;
        }
        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> data = entry.getValue();

            InterfacesEnums interfaceEnum = InterfacesEnums.getEnumByUrl(key);
            if (Objects.isNull(interfaceEnum)) {
                continue;
            }
            Class<?> clas = interfaceEnum.getClas();
            List<Object> objects = dataConvert(data, clas);
            // 支持多个sheet写入
            excelWriter.write(objects, EasyExcel.writerSheet(interfaceEnum.getDesc()).head(clas).build());
        }
        
    }

    private static List<Object> dataConvert(List<Object> data, Class<?> clas) {
        try {
            Method method = clas.getDeclaredMethod("convertJsonToBean", Object.class);
            Object instance = clas.getInterfaces();
            method.setAccessible(true);

            List<Object> convertResult = new ArrayList<>();
            for (Object obj : data) {
                Object result = method.invoke(instance, obj);
                convertResult.add(result);
            }
            return convertResult;
        } catch (Exception exception) {
            log.info("dataConvert error.");
        }
        return new ArrayList<>();
    }

    private static void export(String fileName, Consumer<ExcelWriter> writerConsumer) {
        ExcelWriter writer =
            EasyExcel.write(fileName).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
        writerConsumer.accept(writer);
        writer.finish();
    }
}
