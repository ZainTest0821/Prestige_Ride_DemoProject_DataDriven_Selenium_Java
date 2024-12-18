package DataProviderUtility;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class DataProviderClass{

    public static Object[][] getExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet(sheetName);

        // Find the number of rows and columns in the sheet
        int rowCount = sheet.getPhysicalNumberOfRows();
        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

        // Create a 2D array to hold the data
        Object[][] data = new Object[rowCount - 1][columnCount];

        // Iterate over the rows and columns and store the data in the 2D array
        Iterator<Row> rowIterator = sheet.iterator();
        int i = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (i > 0) { // Skip the header row
                for (int j = 0; j < columnCount; j++) {
                    data[i - 1][j] = row.getCell(j).toString();
                }
            }
            i++;
        }

        workbook.close();
        return data;
    }
}
