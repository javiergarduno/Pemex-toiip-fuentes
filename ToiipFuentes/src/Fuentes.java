import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Fuentes {

	public static void main(String[] args) throws InterruptedException {

		/* Calendar manual set */
		//calendar.set(Calendar.YEAR, 2014);          	
		//calendar.set(Calendar.MONTH,0);
		//calendar.set(Calendar.DAY_OF_MONTH,1);
		String SrcDirBase = "Y:/TOIIP/Fuentes/";

		HideToSystemTray frame =  new HideToSystemTray();
		int monthsToCopy = 6;

		while(true){
			Calendar calendar = Calendar.getInstance();	
			System.out.println("--" + calendar.getTime());

			String dayOfMonth = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH) ) ; 
			String month_1 = String.format("%02d", calendar.get(Calendar.MONTH)+1);
			String monthName = MonthName(calendar.get(Calendar.MONTH));
			int year = calendar.get(Calendar.YEAR);

			String DestDirectory  = DestDirName(calendar);
			
			//PODIM			
			ReportAgg podim =  new ReportAgg();
			podim.setSrctDir(SrcDirBase + "PODIM/" + year +"/"+ monthName + "/" );
			podim.setSrcFile("PODIM"+monthName.toUpperCase()+".xlsm");
			podim.setDestDir(DestDirectory);
			podim.setDestFile("PODIM_"+ year + month_1 + dayOfMonth +".xlsm");
			podim.CopyReportBySDate(calendar,frame);

			calendar.add(Calendar.MONTH, -monthsToCopy);
			
			for (int i = 0; i < monthsToCopy; i++) {
				System.out.println("i=" + i + "<" + monthsToCopy );
				System.out.println("dentro del ciclo antes de incremento" + calendar.getTime());
				calendar.add(Calendar.MONTH, +1);
				System.out.println("--" + calendar.getTime());

				dayOfMonth = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH) ) ; 
				month_1 = String.format("%02d", calendar.get(Calendar.MONTH)+1);
				monthName = MonthName(calendar.get(Calendar.MONTH) );
				year = calendar.get(Calendar.YEAR);

				DestDirectory  = DestDirName(calendar);
				
				File searchDir = null;
				FileFilter fileFilter = null;
				
				
				
				

				
				//Petroliferos
				searchDir = new File(SrcDirBase + "Petrolíferos ORIGINALES/" + year + "/" + monthName + "/");
				fileFilter = new WildcardFileFilter("Balance y ?r?ficos PR*.xlsx");
				File[] filesPetroliferos = searchDir.listFiles(fileFilter);
				Arrays.sort(filesPetroliferos, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
				
				for (int j = 0; j < filesPetroliferos.length; j++) {
					System.out.println(filesPetroliferos[j]);				
										 
					Calendar fileCalendar = ReadExcelDatePetroliferos(filesPetroliferos[j]);
					
					String fileDayOfMonth = String.format("%02d", fileCalendar.get(Calendar.DAY_OF_MONTH) ) ; 
					String fileMonth_1 = String.format("%02d", fileCalendar.get(Calendar.MONTH)+1);
					//String file_MonthName = MonthName(fileCalendar.get(Calendar.MONTH));
					int fileYear = fileCalendar.get(Calendar.YEAR);

					Report balanceGraficos = new Report(filesPetroliferos[j]);					
					balanceGraficos.setDestDir(DestDirName(fileCalendar));
					balanceGraficos.setDestFile("Balance y gráficos PR_" + fileYear + fileMonth_1 + fileDayOfMonth +".xlsx");
					balanceGraficos.CopyReport(frame);
					if(filesPetroliferos[j].toString().matches("(.*)ierre(.*)")){
						System.out.println("Fin de mes: " + filesPetroliferos[j]);
						balanceGraficos.OverrideReport(frame);
					}					
				}
				
				
				//Petroquimicos
				searchDir = new File(SrcDirBase + "Petroquímicos/" + year + "/" + monthName + "/");
				fileFilter = new WildcardFileFilter("ReportPPQ_*.xlsx");
				File[] filesPetroquimicos = searchDir.listFiles(fileFilter);
				Arrays.sort(filesPetroquimicos, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
				
				for (int j = 0; j < filesPetroquimicos.length; j++) {
					System.out.println(filesPetroquimicos[j]);
					
					Calendar fileCalendar = ReadExcelDatePetroqumicos(filesPetroquimicos[j], calendar);

					String fileDayOfMonth = String.format("%02d", fileCalendar.get(Calendar.DAY_OF_MONTH) ) ; 
					String fileMonth_1 = String.format("%02d", fileCalendar.get(Calendar.MONTH)+1);
					//String file_MonthName = MonthName(fileCalendar.get(Calendar.MONTH));
					int fileYear = fileCalendar.get(Calendar.YEAR);

					Report petroquimicos = new Report(filesPetroquimicos[j]);					
					petroquimicos.setDestDir(DestDirName(fileCalendar));
					petroquimicos.setDestFile("ReportPPQ_"+ fileYear + fileMonth_1 + fileDayOfMonth +".xlsx");
					petroquimicos.CopyReport(frame);					
				}
				
				
				//Ventas diarias
				
				searchDir = new File(SrcDirBase + "Ventas Diarias/" + year + "/" + monthName + "/");
				fileFilter = new WildcardFileFilter("Ventas diarias*.xlsx");
				File[] filesVentas = searchDir.listFiles(fileFilter);
				Arrays.sort(filesVentas, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
				
				for (int j = 0; j < filesVentas.length; j++) {
					System.out.println(filesVentas[j]);	
					
					Calendar fileCalendar = ReadExcelDateVentas(filesVentas[j]);
					
					String fileDayOfMonth = String.format("%02d", fileCalendar.get(Calendar.DAY_OF_MONTH) ) ; 
					String fileMonth_1 = String.format("%02d", fileCalendar.get(Calendar.MONTH)+1);
					//String file_MonthName = MonthName(fileCalendar.get(Calendar.MONTH));
					int fileYear = fileCalendar.get(Calendar.YEAR);
					
					Report ventas = new Report(filesVentas[j]);					 
					ventas.setDestDir(DestDirName(fileCalendar));
					ventas.setDestFile("Ventas diarias " + fileYear +  fileMonth_1 + fileDayOfMonth  +".xlsx");
					ventas.CopyReport(frame);
				}
				
				
				//Rohoy crudo
				//IMPORTANTE: este debe tener la fecha del dia siguiente pero se debe de guardar en
				// la carpeta de mes anteior, por ejemplo, si trae informacion del 31 de marzo entonces el nombre debe decir 1 de abril, 
				// pero se debe de guardar en la carpeta de marzo.
				//
				searchDir = new File(SrcDirBase + "Rohoy/Crudo/" + year + "/" + monthName + "/");
				fileFilter = new WildcardFileFilter("Resumen Operativo Crudo*.xlsx");
				File[] filesRohoy = searchDir.listFiles(fileFilter);
				Arrays.sort(filesRohoy, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
				
				for (int j = 0; j < filesRohoy.length; j++) {
					System.out.println(filesRohoy[j]);				 
					
					Calendar fileCalendar = ReadExcelDateRohoy(filesRohoy[j]);
					
					String fileDayOfMonth = String.format("%02d", fileCalendar.get(Calendar.DAY_OF_MONTH) ) ; 
					String fileMonth_1 = String.format("%02d", fileCalendar.get(Calendar.MONTH)+1);
					//String file_MonthName = MonthName(fileCalendar.get(Calendar.MONTH));
					int fileYear = fileCalendar.get(Calendar.YEAR);
					
					Report rohoy = new Report(filesRohoy[j]);
					rohoy.setDestDir(DestDirName(fileCalendar));
										
					fileCalendar.add(Calendar.DAY_OF_YEAR, 1);
					fileDayOfMonth = String.format("%02d", fileCalendar.get(Calendar.DAY_OF_MONTH) ) ; 
					fileMonth_1 = String.format("%02d", fileCalendar.get(Calendar.MONTH)+1);
					//String file_MonthName = MonthName(fileCalendar.get(Calendar.MONTH));
					fileYear = fileCalendar.get(Calendar.YEAR);
			        System.out.println("fecha de archvo mas  un dia: " + fileCalendar.getTime());
					
					rohoy.setDestFile("RohoyCrd_" + fileYear + fileMonth_1 + fileDayOfMonth + ".xlsx");
					rohoy.CopyReport(frame);
				}
				

												
				//Crudo por campos
			
				searchDir = new File(SrcDirBase + "Crudo por campos/" + year + "/" + monthName + "/");
				fileFilter = new WildcardFileFilter("Crudo por campos*.xls");
				File[] filesCrudoCampos = searchDir.listFiles(fileFilter);
				//Arrays.sort(filesCrudoCampos, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
				System.out.println("Buscando en: "  + searchDir);
				for (int j = 0; j < filesCrudoCampos.length; j++) {
					if(filesCrudoCampos[j].toString().matches(".*[a-zA-z0-9]+\\.xls"))
					{
						System.out.println("Procesando" + filesCrudoCampos[j]  );						

						Calendar fileCalendar = ReadExcelDateCrudoCampos(filesCrudoCampos[j],calendar);
												
						String fileDayOfMonth = String.format("%02d", fileCalendar.get(Calendar.DAY_OF_MONTH) ) ; 
						String fileMonth_1 = String.format("%02d", fileCalendar.get(Calendar.MONTH)+1);
						//String file_MonthName = MonthName(fileCalendar.get(Calendar.MONTH));
						int fileYear = fileCalendar.get(Calendar.YEAR);
						
						
						Report CrudoCampos = new Report(filesCrudoCampos[j]);					 
						CrudoCampos.setDestDir(DestDirName(fileCalendar));
						CrudoCampos.setDestFile("Crudo por campos " + fileYear +  fileMonth_1 + fileDayOfMonth  +".xls");
						CrudoCampos.CopyReport(frame);
						
						
					}
					
					
				}
				
			}
			
			System.out.println("-- fin del a copia");
			Thread.sleep(100 * 1000);
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
	
	static int MonthNametoInt(String monthName){
		if (monthName.equalsIgnoreCase("Enero")){
			return 0;			
		}
		if (monthName.equalsIgnoreCase("Febrero")){
			return 1;			
		}
		if (monthName.equalsIgnoreCase("Marzo")){
			return 2;			
		}
		if (monthName.equalsIgnoreCase("Abril")){
			return 3;			
		}
		if (monthName.equalsIgnoreCase("Mayo")){
			return 4;			
		}
		if (monthName.equalsIgnoreCase("Junio")){
			return 5;			
		}
		if (monthName.equalsIgnoreCase("Julio")){
			return 6;			
		}
		if (monthName.equalsIgnoreCase("Agosto")){
			return 7;			
		}
		if (monthName.equalsIgnoreCase("Septiembre")){
			return 8;			
		}
		if (monthName.equalsIgnoreCase("Octubre")){
			return 9;			
		}
		if (monthName.equalsIgnoreCase("Noviembre")){
			return 10;			
		}
		if (monthName.equalsIgnoreCase("Diciembre")){
			return 11;			
		}
		return -1;	
	}
	
	static Calendar  ReadExcelDatePetroliferos(File filepath)
    {
        try
        {
            FileInputStream file = new FileInputStream(filepath);
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell =  sheet.getRow(9).getCell(41);
                      
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(cell.getDateCellValue());                 
            
            file.close();
            return myCal;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
    }
	
	
	static Calendar  ReadExcelDatePetroqumicos(File filepath, Calendar calendar)
    {
        try
        {
        	Calendar fileCalendar = (Calendar) calendar.clone();
            FileInputStream file = new FileInputStream(filepath);
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
    		
    		//Reading month name
    		Cell cell =  sheet.getRow(4).getCell(36);
    		int month = MonthNametoInt(cell.getStringCellValue());
    		fileCalendar.set(Calendar.MONTH,month);   
    		
    		//Reading "1" if is last day of month
    		cell =  sheet.getRow(4).getCell(39);
    		int lastDay = (int) cell.getNumericCellValue();
    		if(lastDay == 1){
    			fileCalendar.add(Calendar.MONTH, -1);
    			System.out.println("cierre de mes");
    		}
    		
    		//Reading last day with data
            cell =  sheet.getRow(9).getCell(41);
            int currentDay = (int) cell.getNumericCellValue();            
            /* Calendar manual set */
            fileCalendar.set(Calendar.DAY_OF_MONTH,currentDay);
            
           // System.out.println(  currentDay +" " + calendar.get(Calendar.DAY_OF_MONTH));

            System.out.println("clon modificado: " + fileCalendar.getTime());
            		       
            file.close();
            return fileCalendar;            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
    }
	
	
	static Calendar  ReadExcelDateVentas(File filepath)
    {
		try
        {
            FileInputStream file = new FileInputStream(filepath);
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell =  sheet.getRow(6).getCell(13);
                      
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(cell.getDateCellValue());
                  
            
            file.close();
            return myCal;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
    }
	
	static Calendar  ReadExcelDateRohoy(File filepath)
    {
		try
        {
            FileInputStream file = new FileInputStream(filepath);
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell =  sheet.getRow(4).getCell(5);
                      
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(cell.getDateCellValue());
           
            
            file.close();
            return myCal;
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
    }
	
	static Calendar  ReadExcelDateCrudoCampos(File filepath, Calendar calendar)
    {
		try
        {
			Calendar fileCalendar = (Calendar) calendar.clone();
			
			//System.out.println("Procesando: " + filepath);
			
            FileInputStream file = new FileInputStream(filepath);
 
            //Create Workbook instance holding reference to .xlsx file
           // XSSFWorkbook workbook = new XSSFWorkbook(file);            
            FileInputStream fileIn=new FileInputStream(filepath);
    		Workbook workbook = WorkbookFactory.create(fileIn);      //this reads the file
 
            //Get first/desired sheet from the workbook
    		final Sheet sheet= workbook .getSheet("CORP.PROD");
            //XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
    		
            //initial point to search
            Cell cellDay =  sheet.getRow(9).getCell(4);
            Cell cellData = sheet.getRow(10).getCell(4);
           
            
            //Searching real values
            int infoDay = 4;         
            while (cellData.getStringCellValue().equals("(R)")) {
            	infoDay++;	
            	cellDay =  sheet.getRow(9).getCell(infoDay);
            	cellData =  sheet.getRow(10).getCell(infoDay);
            	//System.out.println("-");
            				
			}
            
            infoDay--;
        	cellDay =  sheet.getRow(9).getCell(infoDay);
        	cellData =  sheet.getRow(10).getCell(infoDay);
        	//System.out.println(cellDay.getNumericCellValue());            
           
         
            int currentDay = (int) cellDay.getNumericCellValue();            
            /* Calendar manual set */
            //System.out.println("   antes de modificacion : "+ calendar.getTime());
            fileCalendar.set(Calendar.DAY_OF_MONTH,currentDay);
            //System.out.println("   despues de modificacion: "+ calendar.getTime());

            
            file.close();
            return fileCalendar;
            
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return null;
    }
	
	static String PrintCalendar(Calendar calendarToPrint){
		
        return calendarToPrint.get(Calendar.DAY_OF_MONTH) + "/"
        		+ calendarToPrint.get(Calendar.MONTH) + "/"
        		+ calendarToPrint.get(Calendar.YEAR);     
        	
	}
}
