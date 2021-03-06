package pl.zut.edu.ztpj;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.zut.edu.ztpj.config.DBMS;
import pl.zut.edu.ztpj.db.dto.Director;
import pl.zut.edu.ztpj.db.dto.Employee;
import pl.zut.edu.ztpj.db.dto.Trader;

public class EmployeeManagement {
	private List<Employee> employeeList;
	private Scanner scanner = new Scanner(System.in);
	private Daemon demon;
	
	public EmployeeManagement(){
		employeeList = new ArrayList<>();
		
		demon = new Daemon(8185);
		demon.start();
	}
	
	private void printAllEmployees(List<Employee> employees) {
		String pressedKey = "n";
		
		//System.out.println("1.1 Lista pracownikow\n");
		for(Employee employee : employees) {
			if (pressedKey.equals("n") || pressedKey.equals("N"))
				printInformation(employee);
			else if (pressedKey.equals("q") || pressedKey.equals("Q"))
				break;
			
			System.out.println("\t-----------------------------------------------");
			System.out.println("[N] - nastepny");
			System.out.println("[Q] - powrot");
			pressedKey = scanner.nextLine();
		}
	}
	
	private void addAnEmployee() {
		Employee newEmployee = null;
		String readPosition = "";
		System.out.print("[D]yrektor/[H]andlowiec:\t");
		readPosition = scanner.nextLine();
		
		if (readPosition.equals("d") || readPosition.equals("D")) {
			newEmployee = new Director();
		} else if (readPosition.equals("h") || readPosition.equals("H")) {
			newEmployee = new Trader();
		}
		
		String tForname, tSurname, tSalary, tPhone;
		System.out.print("Imie:\t\t\t");
		tForname = scanner.nextLine();
		System.out.print("Nazwisko:\t\t");
		tSurname = scanner.nextLine();
		System.out.print("Wynagrodzenie:\t\t");
		tSalary = scanner.nextLine();
		System.out.print("Telefon:\t\t");
		tPhone = scanner.nextLine();
		
		if (newEmployee instanceof Director) {
			String tDutyAddition, tDutyCard, tCostsLimit;
			System.out.print("Dodatek sluzbowy:\t");
			tDutyAddition = scanner.nextLine();
			System.out.print("Karta sluzbowa:\t\t");
			tDutyCard = scanner.nextLine();
			System.out.print("Limit kosztow/miesiac:\t");
			tCostsLimit = scanner.nextLine();
			
			newEmployee = new Director(tForname, tSurname, Integer.parseInt(tSalary), tPhone, Integer.parseInt(tDutyAddition), tDutyCard, Integer.parseInt(tCostsLimit));
		} else {
			String tProvision, tProvisionLimit;
			System.out.print("Prowizja %:\t\t");
			tProvision = scanner.nextLine();
			System.out.print("Limit prowizji/miesiac:\t");
			tProvisionLimit = scanner.nextLine();
			
			newEmployee = new Trader(tForname, tSurname, Integer.parseInt(tSalary), tPhone, Integer.parseInt(tProvision), Integer.parseInt(tProvisionLimit));
		}
		System.out.println("\t-----------------------------------------------");
		System.out.println("[A] - dodaj");
		System.out.println("[Q] - porzuc");
		String pressedKey = "";
		pressedKey = scanner.nextLine();
		while (!pressedKey.equals("q") && !pressedKey.equals("Q")) {
			if (pressedKey.equals("a") || pressedKey.equals("A")) {
				if (newEmployee instanceof Director) {
					DBMS.getInstance().getDao().getDaoDirector().insertDirector(newEmployee);
				} else {
					DBMS.getInstance().getDao().getDaoTrader().insertTrader(newEmployee);
				}
				break;
			}
			System.out.println("Nie rozpoznano klawisza");
			
			pressedKey = scanner.nextLine();
		}
	}
	
	private void removeAnEmployee() {
		int readIndex = 0;
		System.out.println("3.1 Usun pracownika\n");
		System.out.print("\tPodaj identyfikator    :        ");
		readIndex = scanner.nextInt();
		System.out.println("\t-----------------------------------------------");
		
		if (readIndex != 0) {
			Employee employee = DBMS.getInstance().getDao().getDaoDirector().getDirector(readIndex);
			if (employee == null)
				employee = DBMS.getInstance().getDao().getDaoTrader().getTrader(readIndex);
			
			if (employee != null) {
				printInformation(employee);
				
				System.out.println("\t-----------------------------------------------");
				System.out.println("[R] - usun");
				System.out.println("[Q] - porzuc");
				String pressedKey;
				pressedKey = scanner.nextLine();
				while (!pressedKey.equals("q") && !pressedKey.equals("Q")) {
					if (pressedKey.equals("r") || pressedKey.equals("R")) {
						try {
							if (employee instanceof Director) {
								DBMS.getInstance().getDao().getDaoDirector().removeDirector(readIndex);
							} else {
								DBMS.getInstance().getDao().getDaoTrader().removeTrader(readIndex);
							}
						} catch(Exception e) {
							e.printStackTrace();
						}
						break;
					} else {
						pressedKey = scanner.nextLine();
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void retrieveDataFromNetwork() {
		String host = "127.0.0.1";
		int port;
		
		System.out.println("4.1 Pobierz dane z sieci\n");
		System.out.print("\tAdres :        ");
		host = scanner.nextLine();
		System.out.print("\tPort :        ");
		port = scanner.nextInt();
		
		if (!host.isEmpty() && port != 0) {
			try (Socket echoSocket = new Socket(host, port);
					ObjectInputStream in = new ObjectInputStream(echoSocket.getInputStream())) {
				System.out.println("\t-----------------------------------------------");
				System.out.println("\tŁączenie :        Sukces");
				System.out.println("\t-----------------------------------------------");
				
				System.out.println("Czy jesteś pewien? [T]/[N]");
				String pressedKey;
				pressedKey = scanner.nextLine();
				while (!pressedKey.equals("n") && !pressedKey.equals("N")) {
					if (pressedKey.equals("t") || pressedKey.equals("T")) {
						printAllEmployees((List<Employee>)in.readObject());
						break;
					} else {
						pressedKey = scanner.nextLine();
					}
				}
			} catch (Exception e) {
				System.out.println("\t-----------------------------------------------");
				System.out.println("\tŁączenie :        Niepowodzenie");
				System.out.println("\t-----------------------------------------------");
			}
		}
	}
	
	public void menu() {
		String pressedKey = "";
		do {
			System.out.println("MENU");
			System.out.println("\t1. Lista pracownikow");
			System.out.println("\t2. Dodaj pracownika");
			System.out.println("\t3. Usun pracownika");
			System.out.println("\t4. Pobierz dane z sieci");
			System.out.println("\t\n\n9. Wyjdz z programu");
			
			pressedKey = scanner.nextLine();
			
			switch (pressedKey) {
				case "1":
					retrieveData();
					printAllEmployees(employeeList);
					break;
				case "2":
					addAnEmployee();
					break;
				case "3":
					removeAnEmployee();
					break;
				case "4":
					retrieveDataFromNetwork();
					break;
				case "9":
					demon.stopServer();
					break;
				default:
					break;
			}
		} while (!pressedKey.equals("9"));
	}
	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	
	private void retrieveData() {
		employeeList.clear();
		employeeList.addAll(DBMS.getInstance().getDao().getDaoDirector().getDirectors());
		employeeList.addAll(DBMS.getInstance().getDao().getDaoTrader().getTraders());
	}
	
	private void printInformation(Employee employee) {
		System.out.println("Identyfikator       :   " + employee.getId());
		System.out.println("Imię                :   " + employee.getFirstName());
		System.out.println("Nazwisko            :   " + employee.getLastName());
		System.out.println("Wynagrodzenie       :   " + employee.getSalary());
		System.out.println("Stanowisko          :   " + employee.getClass().getSimpleName());
		System.out.println("Telefon             :   " + employee.getPhoneNumber());
		
		if (employee instanceof Director) {
			System.out.println("Dodatek sluzbowy        :   " + ((Director) employee).getSalaryAddition());
			System.out.println("Karta sluzbowa nr       :   " + ((Director) employee).getCardNumber());
			System.out.println("Limit kosztow/miesiac   :   " + ((Director) employee).getCostsLimitPerMonth());
		} else if (employee instanceof Trader) {
			System.out.println("Prowizja %              :   " + ((Trader) employee).getProvision());
			System.out.println("Limit prowizji/miesiac  :   " + ((Trader) employee).getProvisionLimit());
		}
	}
}
