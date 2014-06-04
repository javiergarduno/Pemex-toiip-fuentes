import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;


public class Report {
	public String SrcFile;
	public String SrcDir;
	public String DestFile;
	public String DestDir;

	
	Report(){
		SrcFile = null;
		SrcDir = null;
		DestFile = null;
		DestDir = null;
	}
	
	
	public void setSrcFile(String srcFile) {
		SrcFile = srcFile;
	}

	public void setSrctDir(String srctDir) {
		SrcDir = srctDir;
	}

	public void setDestFile(String destFile) {
		DestFile = destFile;
	}

	public void setDestDir(String destDir) {
		DestDir = destDir;
	}
	
	public void CopyReport(HideToSystemTray frame){
		
		try {
			File reportSrcFile = new File(SrcDir + SrcFile);
			File reportDestFile = new File(DestDir + DestFile);
			if( reportSrcFile.exists()  && !reportDestFile.exists()){
				FileUtils.copyFile(reportSrcFile, reportDestFile );

				System.out.println("Copiado: "+ reportSrcFile.toString() + " -> " + reportDestFile);
				frame.writeLog("Copiado: "+ reportSrcFile.toString() + " -> " + reportDestFile);
			}
			else{
				if(!reportSrcFile.exists()){
					System.out.println("No existe el la fuente: \""+ reportSrcFile.toString());
				}
				if(reportDestFile.exists()){
					System.out.println("Ya existe el destino " + reportDestFile.toString() + "\"");
				}					
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	}
	

}


