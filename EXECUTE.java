package eu.scattering;

import static java.lang.System.out;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * Automatyczne pobieranie wszystkich zaleznosci i uruchamianie projektu.
 * Klasa korzysta z bibliotek Commons IO i zip4j, uruchamiana byla pod Win10.
 * Tym razem opis nie jest az tak szczegolowy jak w innych plikach
 * (nie sadzilem, ze w ogole bede pisal ten modul).
 * Wszystko moge dokladnie opisac podczas rozmowy.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class EXECUTE {

	private static String fileTomEESource = "http://ftp.ps.pl/pub/apache/tomee/tomee-7.0.5/apache-tomee-7.0.5-plume.zip";
	private static String fileTomEEDestination = "tomee";
	
	private static String fileWarSource = "https://github.com/Skorupski/NumberConverter/raw/master/build/libs/NumberConverter-1.0.war";
	private static String fileWarDestination = "tomee/apache-tomee-plume-7.0.5/webapps/NumberConverter-1.0.war";
	
	private static String fileJarSource = "https://github.com/Skorupski/NumberConverter/raw/master/build/libs/NumberConverter-1.0.jar";
	private static String fileJarDestination = "NumberConverter-1.0.jar";
	
	private static String fileServerStart = "tomee/apache-tomee-plume-7.0.5/bin";
	
	/**
	 * Pobranie TomEE, wypakowanie plikow i usuniecie folderu *.zip.
	 */
	public static void downloadTomEEFile() {
		if (new File(fileTomEEDestination).exists()) {
			out.println(" - The directory '" + fileTomEEDestination + "' already exists.");
			return;
		}
		
		if (!new File(fileTomEEDestination + ".zip").exists()) {
			try {
				FileUtils.copyURLToFile(new URL(fileTomEESource), new File(fileTomEEDestination + ".zip"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			ZipFile zipFile = new ZipFile(fileTomEEDestination + ".zip");
			zipFile.extractAll(fileTomEEDestination);
		} catch (ZipException e) {
			e.printStackTrace();
		}
		
		FileUtils.deleteQuietly(new File(fileTomEEDestination + ".zip"));
		
		out.println(" + The directory '" + fileTomEEDestination + "' has been created.");
	}
	
	/**
	 * Pobranie pliku *.war z GitHuba.
	 */
	public static void downloadWarFile() {
		if (new File(fileWarDestination).exists()) {
			out.println(" - The file '" + new File(fileWarDestination).getName() + "' already exists.");
			return;
		}
		
		try {
			FileUtils.copyURLToFile(new URL(fileWarSource), new File(fileWarDestination));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.println(" + The file '" + new File(fileWarDestination).getName() + "' has been downloaded.");
	}
	
	/**
	 * Pobranie pliku *.jar z GitHuba.
	 */
	public static void downloadJarFile() {
		if (new File(fileJarDestination).exists()) {
			out.println(" - The file '" + new File(fileJarDestination).getName() + "' already exists.");
			return;
		}
		
		try {
			FileUtils.copyURLToFile(new URL(fileJarSource), new File(fileJarDestination));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		out.println(" + The file '" + new File(fileJarDestination).getName() + "' has been downloaded.");
	}
	
	/**
	 * Utworzenie referencji do kontroli serwera.
	 */
	public static void createShortcut() {
		List<String> fileData = new ArrayList<>();
		fileData.add("cd " + fileServerStart);
		fileData.add("");
		fileData.add("exit");
		
		File shortcut;

		shortcut = new File("ServerStart.bat");

		if (shortcut.exists()) {
			out.println(" - The shortcut '" + shortcut.getName() + "' already exists.");
		} else {
			 try {
				 fileData.set(1, "startup.bat");
				 FileUtils.touch(shortcut);
				 FileUtils.writeLines(shortcut, fileData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 out.println(" + The shortcut '" + shortcut.getName() + "' has been created."); 
		}
		
		shortcut = new File("ServerStop.bat");

		if (shortcut.exists()) {
			out.println(" - The shortcut '" + shortcut.getName() + "' already exists.");
		} else {
			 try {
				 fileData.set(1, "shutdown.bat");
				 FileUtils.touch(shortcut);
				 FileUtils.writeLines(shortcut, fileData);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 out.println(" + The shortcut '" + shortcut.getName() + "' has been created."); 
		}
	}
	
	/**
	 * Uruchomienie serwera i klienta.
	 */
	public static void execute() {
		try {
			Desktop.getDesktop().open(new File("ServerStart.bat"));
			Desktop.getDesktop().open(new File("NumberConverter-1.0.jar"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		downloadTomEEFile();
		downloadWarFile();
		downloadJarFile();
		createShortcut();
		execute();
	}

}
