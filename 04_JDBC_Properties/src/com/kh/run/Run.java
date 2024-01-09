package com.kh.run;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import com.kh.view.MemberMenu;

public class Run {

	public static void main(String[] args) {
		// Properties 복습
		/*
		 * * Properties 특징
		 * - Map 계열 컬렉션 (key + value 세트로 담는 특징)
		 * - key, value 모두 String(문자열) 담기
		 *   setProperty(String key, String value)
		 *   getProperty(String key) : String(value)
		 *   
		 *   
		 * - 주로 외부파일(.properties / .xml)로 입출력 할 때 사용
		 */
		/*
		Properties prop = new Properties();
		
		prop.setProperty("C", "INSERT");
		prop.setProperty("R", "SELECT");
		prop.setProperty("U", "UPDATE");
		prop.setProperty("D", "DELETE");
		
		
		
		try {
			prop.store(new FileOutputStream("resources/test.properties"), "properties Test");
			prop.storeToXML(new FileOutputStream("resources/test.xml"), "properties Test");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("resources/driver.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		System.out.println(prop.getProperty("driver"));
		System.out.println(prop.getProperty("url"));
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));
		System.out.println(prop.getProperty("password11")); // 존재하지 않는 키값 제시시 null 반환
		*/
		
		new MemberMenu().mainMenu();
		
		 
	}

}
