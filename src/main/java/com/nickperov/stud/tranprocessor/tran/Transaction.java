package com.nickperov.stud.tranprocessor.tran;

import com.nickperov.stud.tranprocessor.data.Account;
import com.nickperov.stud.tranprocessor.data.Instrument;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {

	private static final AtomicInteger sequenceGenerator = new AtomicInteger(0);

	public Transaction(final Account debitAcc, final Account creditAcc, final Instrument instrument, final Integer amount) {
		this.id = sequenceGenerator.getAndIncrement();
		this.debitAcc = debitAcc;
		this.creditAcc = creditAcc;
		this.instrument = instrument;
		this.amount = amount;
		this.status = TransactionStatus.P;
	}

	public enum TransactionStatus {
		P, // prepared
		F  // executed - final
	}

	private Integer id;

	private Account debitAcc;

	private Account creditAcc;

	private Instrument instrument;

	private Integer amount;

	private TransactionStatus status;

	public Integer getId() {
		return this.id;
	}

	public Account getDebitAcc() {
		return this.debitAcc;
	}

	public Account getCreditAcc() {
		return this.creditAcc;
	}

	public Instrument getInstrument() {
		return this.instrument;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public TransactionStatus getStatus() {
		return this.status;
	}

	public void markExecuted() {
		this.status = TransactionStatus.F;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (obj instanceof Transaction) {
			return this.id.equals(((Transaction) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.id;
	}


	// For tests
	static int getRandomInt() {
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		int randomVal = 0;
		for (int i = 0; i < 100; i++) {
			randomVal += random.nextInt();
		}
		return randomVal;
	}

}