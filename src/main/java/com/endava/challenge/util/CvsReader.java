package com.endava.challenge.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvBadConverterException;
import org.springframework.core.io.ClassPathResource;


public class CvsReader {
    public static <T> List<T> convertFileToTargetObject(String path, Class<? extends T> type) {
        try{
            Reader reader = new InputStreamReader(new ClassPathResource(path).getInputStream());
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(type)
                    .withThrowExceptions(false)
                    .build();
            return csvToBean.parse();
        }catch (CsvBadConverterException e){
            e.getConverterClass();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
