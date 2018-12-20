package com.biz.bank.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.biz.bank.vo.BankVO;

public class BankService {
	
	List<BankVO> bankList;
	String balFile;
	Scanner scan;
	
	public BankService(String balfile) {
		bankList = new ArrayList();
		this.balFile = balfile;
		scan = new Scanner(System.in);
	
	}
	public BankVO bankFindId(String strId) {
		//TODO 계좌번호 조회
		/*
		 * 계좌번호를 매개변수로 받아서 bankList에서 계좌를 조회하고
		 * bankList에 계좌가 있으면 찾은 BankVO(vo)를 return하고
		 * 					 없으면 null값을 return하도록 한다.
		 */
		for(BankVO vo : bankList) {
			if(vo.getStrID().equals(strId))
				return vo;
		} 
				return null;
	}
	
	public void readBalance() {
		//TODO 파일읽어오기
		
		FileReader fr;
		BufferedReader buffer;
		
		try {
			fr = new FileReader(balFile);
			buffer = new BufferedReader(fr);
			
			while(true) {
				String reader=buffer.readLine();
				if(reader == null) break;
				
				String[] strReader = reader.split(":");
				
				BankVO vo = new BankVO();
				vo.setStrID(strReader[0]);
				vo.setIntBalance(Integer.valueOf(strReader[1]));
				vo.setStrLastDate(strReader[2]);
				bankList.add(vo);
			}
			
			buffer.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void bankMenu() {
		//TODO 메뉴선택하기
		//Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.println("=======================================");
			System.out.println("1.입금\t2.출금\t3.계좌조회\t종료");
			System.out.println("---------------------------------------");
			
			System.out.print("업무선택 >> ");
			
			String strM =scan.nextLine();
			int intM = Integer.valueOf(strM);
			if(intM == 0) System.out.println("GOOD BYE!"); 
				//return;// 종료하는 부분은 맨 먼저 넣어주는게 좋다.
			if(intM == 1) this.bankInput();		//System.out.println("입금");
			if(intM == 2) this.bankOutput();	//System.out.println("출금");
			if(intM == 3) 
				System.out.println("계좌조회");
				
		}
	}
	
	public void bankInput() {
		//TODO 입금 계좌번호 입력
		
		System.out.print("입금계좌>> ");
		String strId= scan.nextLine();
		
		BankVO vo = bankFindId(strId);
		if(vo == null ) return;
		
		// 계좌번호가 정상이고 vo에는 해당계좌번호의 정보가 담겨있다.
		System.out.print("입금액>> ");
		String strIO = scan.nextLine();
		int intIO = Integer.valueOf(strIO);
		
		// 입금일 경우 vo.strIO에 "입금" 문자열 저장
		// vo.int IOCash에 입금금액을 저장한다.
		// vo.balance에 + 입금액을 저장한다.
		vo.setStrIO("입금"); //입출금 구분
		vo.setIntIoCash(intIO); //입출금 금액
		vo.setIntBalance(vo.getIntBalance()+intIO); //최종잔액=잔액+입금액
		
		
		// old java 코드로 현재 날짜 가져오기
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-mm-dd");
		Date curDate = new Date();
		String strDate = sm.format(curDate);
		
		// new Java(1.8) 코드로 날짜 가져오기
		LocalDate ld = LocalDate.now();
		strDate = ld.toString();
		vo.setStrLastDate(strDate);
		
		System.out.println("입금처리 완료!");
		
	}
	
	public void bankOutput() {
		// TODO 출금 계좌번화 입력
		System.out.print("출금계좌>> ");
		String strId = scan.nextLine();
		
		BankVO vo = bankFindId(strId);
		if( vo == null) {
			System.out.println(("계좌번호 오류입니다."));
			return; // 계좌번호가 없으면 종료
		}
		
		// 계좌번호가 있으면 출금할 수 있도록 만들어준다.
		System.out.print("출금액>> ");
		String strIO = scan.nextLine();
		int intIO = Integer.valueOf(strIO);
		
		if(vo.getIntBalance() < intIO) {
			System.out.println("잔액부족");
			return;
		}
		vo.setStrIO("출금");
		vo.setIntIoCash(intIO);
		vo.setIntBalance(vo.getIntBalance()-intIO);
		
		this.bankIOWrite(vo);
		
		System.out.println("출금처리 완료!");
		
	}
	
	public void bankIOWrite(BankVO vo) {
		// TODO 파일생성하여 정보를 추가저장한다.
		
		String filePath = "src/com/biz/bank/iolist/"; // 저장할 경로
		
		String strId = vo.getStrID();
		int intBal = vo. getIntBalance();
		String strLastDate = vo.getStrLastDate();
		
		
		String strIO = vo.getStrID();
		int intIO = vo.getIntIoCash();
		
		FileWriter fw;		
		PrintWriter pw;
		
		try {
			fw = new FileWriter(filePath + strId, true);
			pw = new PrintWriter(fw);
			
			pw.print(strIO);
			pw.print(strLastDate);
			pw.print(strIO);
			
			if(strIO.equals("입금")) {
				pw.print(strIO);
				pw.print(0);
			}else {
				pw.print(0);
				pw.print(strIO);
			}
				pw.println(intBal);
		
				pw.close();
				fw.close();
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}



}
