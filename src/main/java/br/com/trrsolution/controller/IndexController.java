package br.com.trrsolution.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.trrsolution.model.PathConfig;
import br.com.trrsolution.model.War;


@RestController
public class IndexController {
	
	private static PathConfig pathConfigSaved;
	
	@Autowired 
	private ServletContext servletContext;
	
	@RequestMapping(value = "/get-wars", method = RequestMethod.GET)
	public List<War> getWars(){
			
		List<War> wars_ = new ArrayList<>();
		
		File[] wars = new File(pathConfigSaved.getWeblogicPath()+"\\servers\\AdminServer\\upload\\").listFiles();
		
		for (File file : wars) {		
			
			File warFile = new File(file.getAbsolutePath() + "\\app\\" + file.getName() + ".war");
			
			LocalDateTime dateTime = Instant.ofEpochMilli(warFile.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();	
			
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");						
			
			wars_.add(new War(file.getName(), false, dateTime.format(formatador)));			
		}
		return wars_;
	}
	
	@RequestMapping(value="/post-paths", method=RequestMethod.POST)
	public String postPaths(@RequestBody PathConfig pathConfig){
		pathConfigSaved = pathConfig;		
		return "Sucessful!";
	}
	
	@RequestMapping(value="/post-wars-deploy", method=RequestMethod.POST)
	public String postWarsToDeploy(@RequestBody List<War> warsToDeploy){
		
		try {
			
			for (War war : warsToDeploy) {				
				if(war.getChecked()) {
					
					//%1 = pathConfigSaved + war.getServiceName();
					
					String exec = servletContext.getRealPath("clean_install.bat") +" "+ 
									pathConfigSaved.getGitPath() +" "+ 
										war.getServiceName() +" "+
											pathConfigSaved.getWeblogicPath();
					
					Process process = Runtime.getRuntime().exec(exec);
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;
					while((line = reader.readLine()) != null)
					    System.out.println(line);
				}				
			}		
		
		} catch (IOException  e) {			
			e.printStackTrace();
		}
		
		try {
			
			String exec = "cmd /C start "+ pathConfigSaved.getWeblogicPath() + "\\bin\\startWebLogic.cmd";
			
			Process process = Runtime.getRuntime().exec(exec,null);
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return "Sucessful!";
	}
}