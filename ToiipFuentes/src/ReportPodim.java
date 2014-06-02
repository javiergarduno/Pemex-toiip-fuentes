import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ReportPodim extends Report{
		
		
		ReportPodim() {
			super();
		}
		
		
		public void CopyReport(){
			try {
				File reportSrcFile = new File(SrcDir + SrcFile);
				File reportDestFile = new File(DestDir + DestFile);
				if( reportSrcFile.exists()  && !reportDestFile.exists()){
					
					System.out.println(reportSrcFile.lastModified());
					FileUtils.copyFile(reportSrcFile, reportDestFile );
					System.out.println("Copiado: "+ reportSrcFile.toString() + " -> " + reportDestFile);
				}
				else{
					if(!reportSrcFile.exists()){
						System.out.println("No existe el la fuente: \""+ reportSrcFile.toString());
					}
					if(reportDestFile.exists()){
						System.out.println("Ya esxiste el destino " + reportDestFile.toString() + "\"");
					}					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
			
		}
		
	
	}