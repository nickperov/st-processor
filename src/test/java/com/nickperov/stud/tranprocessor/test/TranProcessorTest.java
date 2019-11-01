package com.nickperov.stud.tranprocessor.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nickperov.stud.tranprocessor.data.Balance;
import com.nickperov.stud.tranprocessor.data.Index;
import com.nickperov.stud.tranprocessor.exec.MultiThreadTranExecutor;
import com.nickperov.stud.tranprocessor.exec.SingleThreadTranExecutor;
import com.nickperov.stud.tranprocessor.exec.TransactionExecutorService;
import com.nickperov.stud.tranprocessor.test.TestUtils.TranGenStrategy;
import com.nickperov.stud.tranprocessor.tran.Transaction;
import com.nickperov.stud.tranprocessor.tran.TransactionExecutor;

public class TranProcessorTest {
	
	private static List<List<Balance>> resultBalances = new ArrayList<>(); 
	
	private static List<TransactionExecutor> transactionDocsST;
	private static List<TransactionExecutor> transactionDocsMT;
	
	private TransactionExecutorService tranExecutorServiceST = SingleThreadTranExecutor.getExecutorService();
	private TransactionExecutorService tranExecutorServiceMT = MultiThreadTranExecutor.getExecutorService();
	
	private static int ACC_NUMBER = 100;
	//private static int TRAN_NUMBER = 1_000_000;
	private static int TRAN_NUMBER = 1000;
	
	private static final TranGenStrategy tranGenStrategy = TranGenStrategy.RND;
	
	@BeforeClass
	public static void prepareData() {
		
		TestUtils.initDictionaries(ACC_NUMBER, null);
		
		List<Transaction> transactions = TestUtils.generateTransactions(tranGenStrategy, TRAN_NUMBER);
		transactionDocsST = TestUtils.createTranDocsST(transactions);
		transactionDocsMT = TestUtils.createTranDocsMT(transactions);
		
		TestUtils.printAllInstruments();
		
		TestUtils.printAllAccounts();
	}
	
	@Before
	public void cleanBalances() {
		Index.getIndex().getAllBalances().forEach(b -> b.resetBalance());
	}
	
	@After
	public void saveBalanceResults() {
		resultBalances.add(Index.getIndex().getAllBalances().stream().map(b -> b.copyBalance()).collect(Collectors.toList()));
	}
	
	@AfterClass
	public static void checkResultBalances() {
		System.out.println("===== Compare results =====");
		if (resultBalances.size() > 1) {
			int i = 0;
			checkRes:
			do {
				List<Balance> balances1 = resultBalances.get(i);
				List<Balance> balances2 = resultBalances.get(i + 1);
				
				if (balances1.size() != balances2.size()) {
					System.out.println("Different results !!!");
					break;
				}
				
				for (int k = 0; k < balances1.size(); k++) {
					if (!balances1.get(k).getAmount().equals(balances2.get(k).getAmount())) {
						System.out.println("Different results !!!");
						break checkRes;
					}
				}
				
				i++;
			} while (i < (resultBalances.size() - 1));
		} else
			System.out.println("Not enough results to compare");
		
		if (!resultBalances.isEmpty()) {
			// Check that sum == 0
			int sum = resultBalances.get(0).stream().mapToInt(b -> b.getAmount()).sum();
			if (sum != 0)
				System.out.println("Wrong sum !!!");	
			else
				System.out.println("Correct sum");
		}
		
		TestUtils.printAllBalances();
	}
	
	@Test
	public void runSingleThreadTest() {
		tranExecutorServiceST.setTransDocumentList(transactionDocsST);
		tranExecutorServiceST.startTranService();
	}
	
	@Test
	public void runMultiThreadTest() {
		tranExecutorServiceMT.setTransDocumentList(transactionDocsMT);
		tranExecutorServiceMT.startTranService();
	}
}