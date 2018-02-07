package gui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;

import readFile.ReadFile;
import virus.Virus;
import virus.VirusLogica;
import org.eclipse.swt.widgets.Group;

/**
 * @author Sebastiaan te Molder
 *
 */
public class VirusGUI {
	/**
	 * filePath, path to the file
	 * IDList1, text area with virus ids relevent to the host chosen the combobox for hosts1
	 * IDList2, text area with virus ids relevent to the host chosen the combobox for hosts2 
	 * IDSim, text area with virus ids relevent to the hosts chosen the combobox for hosts1 and 2
	 * sortMethod, The method in which the above text areas are sorted
	 */
	private static Text filePath;
	private static Text IDList1;
	private static Text IDSim;
	private static Text IDList2;
	private static String sortMethod = "Id";

	/**
	 * Launch the application.
	 * @param args
	 */
	/**
	 * The main dude bowling on in this crazy universe of hours.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(700, 400);
		shell.setText("SWT Application");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(10, 20, 70, 17);
		lblNewLabel.setText("Pad:");
		
		filePath = new Text(shell, SWT.BORDER);
		filePath.setBounds(175, 10, 110, 27);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(10, 45, 149, 17);
		lblNewLabel_1.setText("Virus classification:");
		
		final Combo hostID1 = new Combo(shell, SWT.NONE);
		final Combo hostID2 = new Combo(shell, SWT.NONE);
		
		final Combo comboClass = new Combo(shell, SWT.NONE);
		comboClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(hostID1.getSelectionIndex() != -1)
				{
					VirusLogica.hostToList(Virus.getHostList().get(hostID1.getItem(hostID1.getSelectionIndex())),comboClass.getItem(comboClass.getSelectionIndex()),0);
				}
				if(hostID2.getSelectionIndex() != -1){
					VirusLogica.hostToList(Virus.getHostList().get(hostID2.getItem(hostID2.getSelectionIndex())),comboClass.getItem(comboClass.getSelectionIndex()),1);
				}
				VirusLogica.SortedBy(new Text[]{IDList1, IDList2, IDSim});
			}
		});
		comboClass.setBounds(173, 43, 189, 29);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(421, 10, 70, 17);
		lblNewLabel_2.setText("Host ids:");
		
		
		hostID1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String classification;
				if(comboClass.getSelectionIndex() > -1){
					classification = comboClass.getItem(comboClass.getSelectionIndex());
				}
				else{
					classification = "";
				}
				VirusLogica.hostToList(Virus.getHostList().get(hostID1.getItem(hostID1.getSelectionIndex())),classification,0);
				//IDSim.setText(simLists(IDList1.getText().split("\\n"),IDList2.getText().split("\\n")));
				VirusLogica.SortedBy(new Text[]{IDList1, IDList2, IDSim});
			}
		});
		hostID1.setBounds(501, 10, 189, 29);
		
		
		hostID2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String classification;
				if(comboClass.getSelectionIndex() > -1){
					classification = comboClass.getItem(comboClass.getSelectionIndex());
				}
				else{
					classification = "";
				}
				VirusLogica.hostToList(Virus.getHostList().get(hostID2.getItem(hostID2.getSelectionIndex())),classification,1);
				//IDSim.setText(simLists(IDList1.getText().split("\\n"),IDList2.getText().split("\\n")));
				VirusLogica.SortedBy(new Text[]{IDList1, IDList2, IDSim});
			}
		});
		hostID2.setBounds(501, 45, 189, 29);
		
		Group radioGroup = new Group(shell, SWT.SHADOW_IN);
		radioGroup.setText("Sort by:");
		radioGroup.setLayout(new RowLayout(SWT.VERTICAL));
		radioGroup.setBounds(10, 112, 150, 100);
		
		final Button btnID = new Button(radioGroup, SWT.RADIO);
		btnID.setSelection(true);
		btnID.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortVirusses(btnID.getText());
			}
		});
		btnID.setBounds(10, 24, 120, 24);
		btnID.setText("Id");
	
		final Button btnClassification = new Button(radioGroup, SWT.RADIO);
		btnClassification.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortVirusses(btnClassification.getText());
			}
		});
		btnClassification.setBounds(10, 48, 120, 24);
		btnClassification.setText("Classification");
		
		final Button btnHostAmount = new Button(radioGroup, SWT.RADIO);
		btnHostAmount.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortVirusses(btnHostAmount.getText());
			}
		});
		btnHostAmount.setBounds(10, 72, 120, 24);
		btnHostAmount.setText("Host amount");
		//Button[] Radios = {btnID,btnClassification,btnHostAmount};
		
		IDList1 = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		IDList1.setBounds(175, 109, 143, 253);
		
		IDSim = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		IDSim.setBounds(332, 109, 143, 253);
		
		IDList2 = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		IDList2.setBounds(501, 109, 143, 253);
		
		Label lblHostId = new Label(shell, SWT.NONE);
		lblHostId.setBounds(175, 86, 70, 17);
		lblHostId.setText("Host ID 1:");
		
		Label lblHostId_1 = new Label(shell, SWT.NONE);
		lblHostId_1.setBounds(501, 86, 70, 17);
		lblHostId_1.setText("Host ID 2:");
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(332, 86, 70, 17);
		lblNewLabel_3.setText("Similarity :");
		
		Button btnOpen = new Button(shell, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				VirusLogica.openViruses(filePath.getText());
				hostID1.setItems(Virus.getHostList().keySet().toArray(new String[Virus.getHostList().keySet().size()]));
				hostID2.setItems(Virus.getHostList().keySet().toArray(new String[Virus.getHostList().keySet().size()]));
				comboClass.setItems(Virus.getClassList().toArray(new String[Virus.getClassList().size()]));
			}
		});
		btnOpen.setBounds(295, 10, 91, 29);
		btnOpen.setText("Open");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * @return The method in which the virus list is supposed to be sorted.
	 */
	public static String getSortMethod() {
		return sortMethod;
	}

	/**
	 * @param sortMethod, The method in which the virus list is supposed to be sorted.
	 */
	public static void setSortMethod(String sortMethod) {
		VirusGUI.sortMethod = sortMethod;
	}
	
	/**
	 * Sorts the virus lists.
	 * 
	 * @param sortMethod The method in which the virus list is supposed to be sorted.
	 */
	public static void sortVirusses(String sortMethod){
		VirusGUI.sortMethod = sortMethod;
		VirusLogica.SortedBy(new Text[]{IDList1, IDList2, IDSim});
	}
}
