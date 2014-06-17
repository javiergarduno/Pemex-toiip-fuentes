import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Fuentes {

	public static void main(String[] args) throws InterruptedException {

		Calendar calendar = Calendar.getInstance();		
		/* Calendar manual set */
		//calendar.set(Calendar.YEAR, 2014);          	
		//calendar.set(Calendar.MONTH,0);
		//calendar.set(Calendar.DAY_OF_MONTH,1);

		HideToSystemTray frame =  new HideToSystemTray();
		
		int daysToCopy = 2;

		while(true){

			calendar.add(Calendar.DATE, -daysToCopy);
			for (int i = 0; i < daysToCopy; i++) {

				calendar.add(Calendar.DATE, +1);
				System.out.println("--" + calendar.getTime());

				String dayOfMonth = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH) ) ; 
				String month_1 = String.format("%02d", calendar.get(Calendar.MONTH)+1);
				String monthName = MonthName(calendar.get(Calendar.MONTH));
				int year = calendar.get(Calendar.YEAR);

				String SrcDirBase = "Y:/TOIIP/Fuentes/";
				String DestDirectory  = DestDirName(calendar);

				//Petroliferos
				
				//searching excel files

				File searchDir = new File(SrcDirBase + "Petrolíferos ORIGINALES/" + year + "/" + monthName + "/");
				FileFilter fileFilter = new WildcardFileFilter("Balance y gráficos PR*.xlsx");
				File[] files = searchDir.listFiles(fileFilter);
				for (int j = 0; j < files.length; j++) {
					System.out.println(files[j]);
					Calendar fileCalendar = ReadExcelDate(files[j]);
					
					String fileDayOfMonth = String.format("%02d", fileCalendar.get(Calendar.DAY_OF_MONTH) ) ; 
					String fileMonth_1 = String.format("%02d", fileCalendar.get(Calendar.MONTH)+1);
					//String file_MonthName = MonthName(fileCalendar.get(Calendar.MONTH));
					int fileYear = fileCalendar.get(Calendar.YEAR);

					Report balanceGraficos = new Report(files[j]);
					//balanceGraficos.setSrctDir( SrcDirBase + "Petrolíferos ORIGINALES/" + year + "/" + monthName + "/");			
					//balanceGraficos.setSrcFile("Balance y gráficos PR "+dayOfMonth+" "+monthName.toLowerCase().substring(0,3)+" "+year+ ".xlsx"); 
					balanceGraficos.setDestDir(DestDirectory);
					balanceGraficos.setDestFile("Balance y gráficos PR_" + fileYear + fileMonth_1 + fileDayOfMonth +".xlsx");
					balanceGraficos.CopyReport(frame);

				}
				
				
				/*

				//Petroliferos fin de mes
				Report balanceGraficosCierre = new Report();
				balanceGraficosCierre.setSrctDir( SrcDirBase + "Petrolíferos ORIGINALES/" + year + "/" + monthName + "/");			
				balanceGraficosCierre.setSrcFile("Balance y gráficos PR cierre de " + monthName.toLowerCase().substring(0,3)+" "+year+ ".xlsx"); 
				balanceGraficosCierre.setDestDir(DestDirectory);
				balanceGraficosCierre.setDestFile("Balance y gráficos PR_" + year + month_1 + dayOfMonth +"cierre.xlsx");
				//if cierre de mes not exists
				balanceGraficosCierre.CopyReport(frame);


				//Crudo por campos
				
				//PODIM
				ReportAgg podim =  new ReportAgg();
				podim.setSrctDir(SrcDirBase + "PODIM/" + year +"/"+ monthName + "/" );
				podim.setSrcFile("PODIM"+monthName.toUpperCase()+".xlsm");
				podim.setDestDir(DestDirectory);
				podim.setDestFile("PODIM_"+ year + month_1 + dayOfMonth +".xlsm");
				podim.CopyReportBySDate(calendar,frame);

				//petroquimicos
				Report ppq = new Report();
				ppq.setSrctDir(SrcDirBase + "Petroquímicos/" + year + "/" + monthName + "/" );
				ppq.setSrcFile("ReportPPQ_" + dayOfMonth + month_1 + (year-2000) + ".xlsx");
				ppq.setDestDir(DestDirectory);
				ppq.setDestFile("ReportPPQ_"+ year + month_1 + dayOfMonth +".xlsx");
				ppq.CopyReport(frame);


				//Rohoy crudo
				Report rohoy = new Report();
				rohoy.setSrctDir(SrcDirBase + "Rohoy/Crudo/" + year + "/" + monthName + "/");
				rohoy.setSrcFile("Resumen Operativo Crudo "+ dayOfMonth + monthName.toLowerCase().substring(0,3) + (year-2000) + ".xlsx");
				rohoy.setDestDir(DestDirectory);
				rohoy.setDestFile("RohoyCrd_" + year + month_1 + dayOfMonth + ".xlsx");
				rohoy.CopyReport(frame);

				//Ventas diarias
				Report ventasDiarias = new Report();
				ventasDiarias.setSrctDir(SrcDirBase + "Ventas Diarias/" + year + "/" + monthName + "/");
				ventasDiarias.setSrcFile("Ventas diarias "+ year +" "+ dayOfMonth  +" "+ monthName.toUpperCase() +".xlsx");
				ventasDiarias.setDestDir(DestDirectory);
				ventasDiarias.setDestFile("Ventas diarias " + year +  month_1 + dayOfMonth  +".xlsx");
				ventasDiarias.CopyReport(frame);
				*/
			}
			
			System.out.println("-- fin del a copia");
			Thread.sleep(10 * 1000);
		}
	}



	static String DestDirName(Calendar calendar){
		String DestDirBase = "Y:/Servicio social/2014/Javier Garduño/PruebasTransitorio Carga Masiva Nuevo TOIIP/";
		String monthDirName = String.format("%02d", calendar.get(Calendar.MONTH)+1 ) +
				"_" + MonthName(calendar.get(Calendar.MONTH)).substring(0,3) +
				"_"+ calendar.get(Calendar.YEAR) + "/";
		return DestDirBase+monthDirName;
	}


	static String MonthName(int month){
		switch (month) {
		case 0:
			return "Enero";
		case 1:
			return "Febrero";	          
		case 2:
			return "Marzo";	    	   
		case 3:
			return "Abril";
		case 4:
			return "Mayo";
		case 5:
			return "Junio";
		case 6:
			return "Julio";
		case 7:
			return "Agosto";
		case 8:
			return "Septiembre";
		case 9:
			return "Octubre";
		case 10:
			return "Noviembre";
		case 11:
			return "Diciembre";	
		}
		return "";
	}
	
	static Calendar  ReadExcelDate(File filepath)
    {
        try
        {
            FileInputStream file = new FileInputStream(filepath);
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell =  sheet.getRow(9).getCell(41);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
           String cellValue = sdf.format(cell.getDateCellValue());
           System.out.println(cellValue); 
            
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(cell.getDateCellValue());
            
            System.out.println(myCal.get(Calendar.DAY_OF_MONTH) + "/"
            		+ myCal.get(Calendar.MONTH)+1 + "/"
            		+ myCal.get(Calendar.YEAR));            
            file.close();
            return myCal;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
    }
}
