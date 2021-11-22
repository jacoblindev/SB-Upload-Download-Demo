package com.jacoblindev.updnfiledemo.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.jacoblindev.updnfiledemo.exception.ApiException;
import com.jacoblindev.updnfiledemo.model.Tutorial;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    static final String TYPE = "text/csv";
    static final String[] HEADERS = { "Id", "Title", "Description", "Published" };

    // Private constructor to hide the implicit public one.
    // sonarlint(java:S1118)
    private CSVHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Tutorial> csvTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.builder().setHeader()
                        .setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build());) {

            List<Tutorial> tutorials = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Tutorial tutorial = new Tutorial(Long.parseLong(csvRecord.get("Id")), csvRecord.get("Title"),
                        csvRecord.get("Description"), Integer.parseInt(csvRecord.get("Published")));

                tutorials.add(tutorial);
            }
            return tutorials;
        } catch (Exception e) {
            throw new ApiException("Fail to parse CSV file: " + e.getMessage());
        }
    }

}
