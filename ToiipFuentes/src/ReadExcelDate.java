


import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelDate
{
    public static void main(String[] args)
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("Balance y gráficos PR_20140511.xlsx"));
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell =  sheet.getRow(0).getCell(0);
            System.out.println(cell.getDateCellValue()); 
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(cell.getDateCellValue());
            System.out.println(myCal.get(Calendar.DAY_OF_MONTH));
            
            switch (cell.getCellType())
            {
                case Cell.CELL_TYPE_NUMERIC:
                    System.out.print(cell.getNumericCellValue() + "\t\0t");
                    break;
                case Cell.CELL_TYPE_STRING:
                    System.out.print(cell.getStringCellValue() + "\t\t");
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    //Not again
                    break;
            }
                    
            
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}