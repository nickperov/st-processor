package com.nickperov.stud.tranprocessor.tran;

import com.nickperov.stud.tranprocessor.data.Balance;
import com.nickperov.stud.tranprocessor.data.EntityUtils;

public class TransactionDocument implements TransactionExecutor {

	public TransactionDocument(final Transaction transaction) {
		this.transaction = transaction;
		this.creditBalance = EntityUtils.getOrCreateBalance(transaction.getCreditAcc(), transaction.getInstrument());
		this.debitBalance = EntityUtils.getOrCreateBalance(transaction.getDebitAcc(), transaction.getInstrument());
	}

	protected Transaction transaction;

	final Balance creditBalance;

	final Balance debitBalance;

	@Override
	public void execute() {
		this.debitBalance.decreaseBalance(this.transaction.getAmount() + Transaction.getRandomInt());
		this.creditBalance.increaseBalance(this.transaction.getAmount() + Transaction.getRandomInt());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("TransDoc [ Id: ").append(this.transaction.getId());
		sb.append("; debitBalance: ").append(this.debitBalance);
		sb.append("; creditBalance: ").append(this.creditBalance);
		return sb.toString();
	}
}