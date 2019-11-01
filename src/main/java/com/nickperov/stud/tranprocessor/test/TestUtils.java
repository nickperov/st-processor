package com.nickperov.stud.tranprocessor.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.nickperov.stud.tranprocessor.data.Account;
import com.nickperov.stud.tranprocessor.data.EntityUtils;
import com.nickperov.stud.tranprocessor.data.Index;
import com.nickperov.stud.tranprocessor.data.Instrument;
import com.nickperov.stud.tranprocessor.data.Instrument.InstrumentCode;
import com.nickperov.stud.tranprocessor.tran.Transaction;
import com.nickperov.stud.tranprocessor.tran.TransactionDocument;
import com.nickperov.stud.tranprocessor.tran.TransactionDocumentSync;
import com.nickperov.stud.tranprocessor.tran.TransactionExecutor;

public class TestUtils {

	
	public enum TranGenStrategy {
		RND, // Random
		REV, // Reverse
		O2M, // One to many
		M2O; // Many to one
	}
	
	public static void printAllInstruments() {
		System.out.println("=== Instruments ===");
		Index.getIndex().getAllInstruments().forEach(System.out::println);
		System.out.println("==========================");
	}
	
	public static void printAllAccounts() {
		System.out.println("=== Accounts ===");
		Index.getIndex().getAllAccounts().forEach(System.out::println);
		System.out.println("==========================");
	}
	
	//@SuppressWarnings("unused")
	public static void printAllBalances() {
		System.out.println("=== Balances ===");
		Index.getIndex().getAllBalances().forEach(System.out::println);
		System.out.println("==========================");
	}
	
	public static List<Transaction> generateTransactions(TranGenStrategy tranGenStrategy, Integer numberOfTrans) {
		final Random rnd = new Random();
		final List<Transaction> transactions = new ArrayList<>();
		
		switch (tranGenStrategy) {
		case RND:
			genRandomTransList(transactions, numberOfTrans, rnd);
			break;
		case REV:
			genRevTransList(transactions, numberOfTrans, rnd);
			break;
		case O2M:
			genO2MTransList(transactions, numberOfTrans, rnd);
			break;
		case M2O:
			genM2OTransList(transactions, numberOfTrans, rnd);
			break;
		default:
			break;
		}
		
		return transactions;
	}
	
	private static void genRandomTransList(List<Transaction> transactions, Integer numberOfTrans, Random rnd) {
		genTransList(transactions, numberOfTrans, rnd, null, null, false);
	}
	
	private static void genRevTransList(List<Transaction> transactions, Integer numberOfTrans, Random rnd) {
		genTransList(transactions, numberOfTrans, rnd, null, null, true);
	}
	
	private static void genO2MTransList(List<Transaction> transactions, Integer numberOfTrans, Random rnd) {
		Integer numberOfAcc = Index.getIndex().getAllAccounts().size();
		Integer debitAccRange = (int) Math.round(numberOfAcc * 0.05);
		genTransList(transactions, debitAccRange, rnd, debitAccRange, null, false);
	}
	
	private static void genM2OTransList(List<Transaction> transactions, Integer numberOfTrans, Random rnd) {
		Integer numberOfAcc = Index.getIndex().getAllAccounts().size();
		Integer creditAccRange = (int) Math.round(numberOfAcc * 0.05);
		genTransList(transactions, creditAccRange, rnd, null, creditAccRange, false);
	}
	
	private static void genTransList(List<Transaction> transactions, Integer numberOfTrans, Random rnd, Integer debitAccRange, Integer creditAccRange, boolean addRevTrans) {
		for (int i = 0; i < numberOfTrans; i++) {
			Account		debitAccount	 = getRandomAccount(debitAccRange);
			Account 	creditAccount	 = getRandomAccount(debitAccount, creditAccRange);
			Instrument	instrument		 = getRandomInstrument();
			Integer		amount			 = rnd.nextInt(10000);
			
			transactions.add(new Transaction(debitAccount, creditAccount, instrument, amount));
			if (addRevTrans) {
				// add reverse transaction
				amount = rnd.nextInt(10000);
				transactions.add(new Transaction(creditAccount, debitAccount, instrument, amount));
				i++;
			}
		}
	}
	
	public static void initDictionaries(Integer accNumber, Integer instrNumber) {
		initAccounts(accNumber);
		
		intitInstruments(instrNumber);
		
		initBalances();
	}
	
	private static void initAccounts(Integer number) {
		int i = 0;
		while(i < (number != null ? number : 100))
			EntityUtils.getOrCreateAccount(generateAccCode(i++));
	}
	
	private static void intitInstruments(Integer number) {
		int i = 0;
		InstrumentCode[] instCodes = InstrumentCode.values();
		while(i < (number != null ? Integer.min(number, instCodes.length) : instCodes.length))
			EntityUtils.getOrCreateInstrument(instCodes[i++]);
	}
	
	private static void initBalances() {
		final Index index = Index.getIndex();
		index.getAllAccounts().forEach(account -> {
			index.getAllInstruments().forEach(instrument -> {
				EntityUtils.getOrCreateBalance(account, instrument);
			});
		});
	}
	
	private static String generateAccCode(int number) {
		return "ACC" + String.format("%07d", number);
	}
	
	private static Instrument getRandomInstrument() {
		final Random rnd = new Random();
		final List<Instrument> instruments = Index.getIndex().getAllInstruments();
		return instruments.get(rnd.nextInt(instruments.size() == 1 ? 1 : (instruments.size() - 1)));
	}
	
	private static Account getRandomAccount(Integer range) {
		return getRandomAccount(null, range);
	}
	
	private static Account getRandomAccount(Account exAccount, Integer range) {
		final Random rnd = new Random();
		final List<Account> accounts = Index.getIndex().getAllAccounts();
		Account account;
		do {
			account = accounts.get(range != null ? rnd.nextInt(range) : rnd.nextInt(accounts.size() - 1));
		} while (exAccount != null && account == exAccount);
		
		return account;
	}
	
	public static List<TransactionExecutor> createTranDocsST(List<Transaction> transactions) {
		return transactions.stream().map(tran -> new TransactionDocument(tran)).collect(Collectors.toList());
	}
	
	public static List<TransactionExecutor> createTranDocsMT(List<Transaction> transactions) {
		return transactions.stream().map(tran -> new TransactionDocumentSync(tran)).collect(Collectors.toList());
	}
	
}
