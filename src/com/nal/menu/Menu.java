package com.nal.menu;

import java.util.Scanner;
import com.nal.entity.Employee;
public class Menu {
	
	Employee employee = null;
	public Menu() {
		this.employee = new Employee();
	}
    public void start() {    
    	Scanner keyboard = new Scanner(System.in);
    	int choice = 0;
        while (choice!=5) {        	            
            System.out.println("=======================================");
            System.out.println("|        MENU NHÂN VIÊN               |");
            System.out.println("| Lựa chọn:                           |");
            System.out.println("|        1. Danh sách nhân viên       |");
            System.out.println("|        2. Thêm mới nhân viên        |");
            System.out.println("|        3. Sửa thông tin nhân viên   |");
            System.out.println("|        4. Xóa nhân viên             |");
            System.out.println("|        5. Thoát chương trình        |");
            System.out.println("=======================================");
            System.out.println("Nhập lựa chọn của bạn: ");
            choice = keyboard.nextInt();
            switch (choice) {
                case 1:
                	choice1();
                    break;
                case 2:
                    choice2();
                    break;
                case 3:
                    choice3();
                    break;
                case 4:
                    choice4();
                    break;
                case 5:
                	choice5();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        keyboard.close();
    }

    private void choice1() {
        System.out.println("Danh sách nhân viên");
        this.employee.listEmployees();
    }

    private void choice2() {
        System.out.println("Thêm mới nhân viên");
        Employee employee = new Employee();
        employee.addEmployee();
    }

    private void choice3() {
        System.out.println("Sửa thông tin nhân viên");
        this.employee.editEmployee();
    }
    private void choice4() {
        System.out.println("Xóa nhân viên");
        this.employee.deleteEmployee();
    }
    private void choice5() {
        System.out.println("Thoát chương trình.");
        System.exit(0);
    }
    public static void main (String[] args){
        new Menu().start();
    }
}
