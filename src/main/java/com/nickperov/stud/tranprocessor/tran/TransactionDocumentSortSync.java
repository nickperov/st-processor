package com.nickperov.stud.tranprocessor.tran;

import com.nickperov.stud.tranprocessor.data.Balance;

public class TransactionDocumentSortSync extends TransactionDocument {

	public TransactionDocumentSortSync(final Transaction transaction) {
		super(transaction);

		this.balances = new Balance[2];
		if (this.creditBalance.getId() > this.debitBalance.getId()) {
			this.balances[0] = this.debitBalance;
			this.balances[1] = this.creditBalance;
		} else {
			this.balances[1] = this.debitBalance;
			this.balances[0] = this.creditBalance;
		}
	}

	private final Balance[] balances;

	@Override
	public void execute() {
		synchronized (this.balances[0]) {
			synchronized (this.balances[1]) {
				this.debitBalance.decreaseBalance(this.transaction.getAmount() /*+ Transaction.getRandomInt()*/);
				this.creditBalance.increaseBalance(this.transaction.getAmount() /*+ Transaction.getRandomInt()*/);
			}
		}
	}

}
