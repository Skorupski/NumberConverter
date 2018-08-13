package eu.scattering.client;

import static java.lang.System.out;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import eu.scattering.core.GlobalVariables;

/**
 * Klasa tworzaca GUI.
 * Nie jest skomplikowana. Powstala po to, zeby pokazac podstawowe mozliwosci programu.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class GUI {

	/* Elementy GUI do ktorych powinien buc bezposredni dostep. */
	private JFrame guiFrame = new JFrame("Number Converter");
	private Container guiPanel = guiFrame.getContentPane();
	
	private JTextField guiTextRemoteAddress = new JTextField(GlobalVariables.defaultConversionServerAddress);
	private JComboBox<String> guiComboBoxMethodSelection = new JComboBox<>(new String[] {"roman", "hexadecimal"});
	private JTextArea guiTextAreaInput = new JTextArea();
	private JTextArea guiTextAreaOutput = new JTextArea();
	private JButton guiButtonProceed = new JButton("Proceed");
	
	private JMenuItem guiMenuExit = new JMenuItem("Exit");
	private JMenuItem guiMenuReset = new JMenuItem("Reset");
	private JCheckBoxMenuItem guiMenuEdit = new JCheckBoxMenuItem("Edit");
	
	/* Blok kopiowany do kazdego konstruktora (w tym domyslnego) */
	{
		/* Wyglad panelu i podstawowe funkcje. */
		guiPanel.setLayout(new BorderLayout());
		JPanel guiPanelNorth = new JPanel(new GridLayout(0, 1));
		guiPanelNorth.add(new JLabel(" Remote address:"));
		guiTextRemoteAddress.setEnabled(false);
		guiTextRemoteAddress.setPreferredSize(new Dimension(500, 28));
		guiPanelNorth.add(guiTextRemoteAddress);
		guiPanelNorth.add(new JLabel(" Available conversion methods:"));
		guiComboBoxMethodSelection.setPreferredSize(new Dimension(500, 28));
		guiPanelNorth.add(guiComboBoxMethodSelection);
		
		guiPanel.add(guiPanelNorth, BorderLayout.NORTH);
		
		JPanel guiPanelCenter = new JPanel(new GridLayout(0, 1));
		
		JPanel guiPanelCenterInput = new JPanel(new BorderLayout());
		guiPanelCenterInput.add(new JLabel(" Input:"), BorderLayout.NORTH);
		guiTextAreaInput.setText(GlobalVariables.defaultConversionValueString);
		JScrollPane guiTextAreaInputPane = new JScrollPane(guiTextAreaInput);
		guiTextAreaInputPane.setPreferredSize(new Dimension(500, 84));
		guiPanelCenterInput.add(guiTextAreaInputPane, BorderLayout.CENTER);
		
		guiPanelCenter.add(guiPanelCenterInput);
		
		JPanel guiPanelCenterOutput = new JPanel(new BorderLayout());
		guiPanelCenterOutput.add(new JLabel(" Output:"), BorderLayout.NORTH);
		guiTextAreaOutput.setEditable(false);
		JScrollPane guiTextAreaOutputPane = new JScrollPane(guiTextAreaOutput);
		guiTextAreaOutputPane.setPreferredSize(new Dimension(500, 84));
		guiPanelCenterOutput.add(guiTextAreaOutputPane, BorderLayout.CENTER);
		
		guiPanelCenter.add(guiPanelCenterOutput);
		
		guiPanel.add(guiPanelCenter, BorderLayout.CENTER);
		
		guiButtonProceed.setMnemonic(KeyEvent.VK_P);
		guiPanel.add(guiButtonProceed, BorderLayout.SOUTH);
		
		JMenuBar guiMenuBar = new JMenuBar();
		JMenu guiMenuBarFile = new JMenu("File");
		
		guiMenuBar.add(guiMenuBarFile);
		guiMenuExit.setMnemonic(KeyEvent.VK_Q);
		guiMenuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		guiMenuBarFile.add(guiMenuExit);
		guiMenuReset.setMnemonic(KeyEvent.VK_R);
		guiMenuReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		guiMenuBarFile.add(guiMenuReset);
		guiMenuBarFile.addSeparator();
		guiMenuEdit.setSelected(false);
		guiMenuBarFile.add(guiMenuEdit);
		
		guiFrame.setJMenuBar(guiMenuBar);
		
		guiFrame.pack();
		guiFrame.setResizable(false);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		guiFrame.getRootPane().setDefaultButton(guiButtonProceed);
		
		/* Informacje dotyczace poszczegolnych elementow. */
		guiTextRemoteAddress.setToolTipText("The address of the remote server.");
		guiComboBoxMethodSelection.setToolTipText("The required conversion method.");
		guiTextAreaInput.setToolTipText("The input field. These lines shall be send to the server.");
		guiTextAreaOutput.setToolTipText("The response. Converted values or a specific server message.");
		guiButtonProceed.setToolTipText("Connect to the server.");
		guiMenuExit.setToolTipText("Quit");
		guiMenuReset.setToolTipText("Restore the default settings.");
		guiMenuEdit.setToolTipText("Edit the remote address field..");
		
		/* Wykonywane operacje. */
		guiMenuExit.addActionListener(e -> {
			System.exit(0);
		});
		
		guiMenuReset.addActionListener(e -> {
			guiTextRemoteAddress.setText(GlobalVariables.defaultConversionServerAddress);
			guiComboBoxMethodSelection.setSelectedIndex(0);
			guiTextAreaInput.setText(GlobalVariables.defaultConversionValueString);
			guiTextAreaOutput.setText("");
			guiMenuEdit.setSelected(false);
			guiTextRemoteAddress.setEnabled(false);
		});
		
		guiMenuEdit.addActionListener(e -> {
			guiTextRemoteAddress.setEnabled(guiMenuEdit.isSelected());
		});
		
		
		guiButtonProceed.addActionListener(e -> {
			guiTextAreaOutput.setText(Client.getConversionServerValue(guiTextRemoteAddress.getText(),
					(String) guiComboBoxMethodSelection.getSelectedItem(), guiTextAreaInput.getText()));
		});
		
		/* Dodanie listenerow. */
		guiButtonProceed.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {	}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				guiFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				guiFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {	}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		guiFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {	}

			@Override
			public void windowClosed(WindowEvent arg0) { }

			@Override
			public void windowClosing(WindowEvent arg0) {
				/* Jezeli wlaczylismy serwer z pliku EXECUTE.jar, zamkniecie GUI go wylaczy. */
				File shortcut = new File("ServerStop.bat");
				if (shortcut.exists()) {
					try {
						Desktop.getDesktop().open(shortcut);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {}

			@Override
			public void windowIconified(WindowEvent arg0) {	}

			@Override
			public void windowOpened(WindowEvent arg0) { }
			
		});
		
		/* Wyswietlanie ramki. */
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setVisible(true);
	}
	
	/**
	 * Klasa startowa.
	 * 
	 * @param args W zaleznosci od ilosci argumentow program pracuje w konsoli lub uruchomione jest GUI.
	 */
	public static void main(String[] args) {
		/* Jezeli nie ma argumentow tworzymy GUI. */
		if (args.length == 0) {
			/* Wszystkie elementy przenosimy do dispatcha. */
			SwingUtilities.invokeLater(() -> {
				try {
					/* Zmiana wygladu elementow. */
				    for (LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(laf.getName())) {
				            UIManager.setLookAndFeel(laf.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    /* Trudno, nie udalo sie. Program i tak bedzie dzialal baz zarzutu. */
				}
				new GUI();
			});
		}
		/* Jest tylko jeden argument. */
		else if (args.length == 1) {
			/* Wyswietlamy informacje odnosnie programu. */
			if (args[0].toLowerCase().equals("-h") || args[0].toLowerCase().equals("-help")) {
				out.println("If you want use the GUI version please do not include parameters.\n"
						+ "Otherwise, you must set at least one of the following:\n"
						+ "-value  The input value (default: 123).\n"
						+ "-method The conversion method. There are two possibilities: roman (default) and hexadecimal.\n"
						+ "-address The address of the server. If not specified, the default value is used.");
			}
		}
		/* Jest parzysta ilosc argumentow. */
		else if (args.length == 2 || args.length == 4 || args.length == 6) {
			String converterAddress = GlobalVariables.defaultConversionServerAddress;
			String converterMethod = GlobalVariables.defaultConversionMethodString;
			String converterValue = GlobalVariables.defaultConversionValueString;
			
			/* Tworzenie powiazan klucz - wartosc. */
			HashMap<String, String> argMap = new HashMap<>();
			for (int i = 0; i < args.length; i += 2) {
				argMap.put(args[i].toLowerCase(), args[i + 1].toLowerCase());
			}
			
			/* Sprawdzenie dostepnych opcji. */
			String test;
			boolean proceed = false;
			test = argMap.get("-address");
			if (test != null) {
				converterAddress = test;
				proceed = true;
			}
			test = argMap.get("-method");
			if (test != null) {
				converterMethod = test;
				proceed = true;
			}
			test = argMap.get("-value");
			if (test != null) {
				converterValue = test;
				proceed = true;
			}
			
			/* Polaczenie z serwerem. */
			if (proceed) {
				out.println(Client.getConversionServerValue(converterAddress, converterMethod, converterValue));
			} else {
				out.println("At least one parameter must be changed.");
			}
			/* Niewlasciwa ilosc argumentow. */
		} else {
			System.out.println("Invalid number of parameters");
		}
	}
}
