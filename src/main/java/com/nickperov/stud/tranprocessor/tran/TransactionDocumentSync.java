package com.nickperov.stud.tranprocessor.tran;

public class TransactionDocumentSync extends TransactionDocument {

	public TransactionDocumentSync(final Transaction transaction) {
		super(transaction);
	}

	@Override
	public void execute() {

		// make lock
		this.transaction.getInstrument().lock();

		synchronized (this.debitBalance) {
			synchronized (this.creditBalance) {
				// release lock
				this.transaction.getInstrument().unLock();
				this.debitBalance.decreaseBalance(this.transaction.getAmount()/* + Transaction.getRandomInt()*/);
				this.creditBalance.increaseBalance(this.transaction.getAmount() /*+ Transaction.getRandomInt()*/);
			}
		}
	}
}
