package com.biz.bank.exec;

import com.biz.bank.service.BankService;

public class BankExec01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String balfile = "src/com/biz/bank/bankBalance.txt";

		BankService bs = new BankService(balfile);
		bs.readBalance();
		bs.bankMenu();
		bs.bankInput();
		bs.bankOutput();

	}

}
