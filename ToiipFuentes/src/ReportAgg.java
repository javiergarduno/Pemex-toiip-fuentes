import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;

public class ReportAgg extends Report{
		
		
	ReportAgg() {
			super();
	}
	
		
		public void CopyReportBySDate(Calendar calendar, HideToSystemTray frame){
			try {
				File reportSrcFile = new File(SrcDir + SrcFile);
				File reportDestFile = new File(DestDir + DestFile);
				
				Calendar fileCalendar = Calendar.getInstance();
				fileCalendar.setTime(new Date(reportSrcFile.lastModified()));
				
				
				if( reportSrcFile.exists()  && !reportDestFile.exists() &&
						fileCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
						fileCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
						fileCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
				{
					
					
					FileUtils.copyFile(reportSrcFile, reportDestFile );
					System.out.println("Copiado por fecha: "+ reportSrcFile.toString() + " -> " + reportDestFile);
					frame.writeLog("Copiado por fecha: "+ reportSrcFile.toString() + " -> " + reportDestFile);
				}
				else{
					if(!reportSrcFile.exists()){
						System.out.println("No existe el la fuente: \""+ reportSrcFile.toString());
					}
					if(reportDestFile.exists()){
						System.out.println("Ya esiste el destino " + reportDestFile.toString() + "\"");
					}					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
			
		}
		
	
	}