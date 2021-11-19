package com.jacoblindev.updnfiledemo.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jacoblindev.updnfiledemo.model.Tutorial;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
    static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static final String[] HEADERS = { "Id", "Title", "Description", "Published" };
    static final String SHEET = "Tutorials";

    // Private constructor to hide the implicit public one.
    // sonarlint(java:S1118)
    private ExcelHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Tutorial> excelTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Tutorial> tutorials = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Tutorial tutorial = new Tutorial();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                    case 0:
                        tutorial.setId((long) currentCell.getNumericCellValue());
                        break;
                    case 1:
                        tutorial.setTitle(currentCell.getStringCellValue());
                        break;
                    case 2:
                        tutorial.setDescription(currentCell.getStringCellValue());
                        break;
                    case 3:
                        tutorial.setPublished((int) currentCell.getNumericCellValue());
                        break;
                    default:
                        break;
                    }

                    cellIdx++;
                }

                tutorials.add(tutorial);
            }

            workbook.close();
            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }

}
