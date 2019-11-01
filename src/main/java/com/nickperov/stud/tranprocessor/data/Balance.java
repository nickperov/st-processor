package com.nickperov.stud.tranprocessor.data;

public class Balance extends AbstractDataEntity {
	
	Balance(Account acc, Instrument instr) {
		this(acc, instr, 0);
	}
	
	Balance(Account acc, Instrument instr, Integer amt) {
		this.acc	 = acc;
		this.instr	 = instr;
		this.amt	 = amt;
	}
	
	private Balance(Balance b) {
		this.id		 = b.id;
		this.acc	 = b.acc;
		this.instr	 = b.instr;
		this.amt	 = b.amt;
	}
	
	private Account acc;
	
	private Instrument instr;
	
	private Integer amt;
	
	public Integer getId() {
		return id;
	}
	
	public Account getAccount() {
		return acc;
	}
	
	public Instrument getInstrument() {
		return instr;
	}
	
	public Integer getAmount() {
		return amt;
	}
	
	public void increaseBalance(Integer amount) {
		this.amt += amount;
	}
	
	public void decreaseBalance(Integer amount) {
		this.amt -= amount;
	}
	
	public void resetBalance() {
		this.amt = 0;
	}
	
	public Key<AbstractDataEntity> getKey() {
		return getKey(acc, instr);
	}
	
	public static Key<AbstractDataEntity> getKey(Account acc, Instrument instr) {
		return new Key<AbstractDataEntity>(acc, instr);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Balance [id:").append(id).append("; ");
		sb.append("account:").append(acc.getCode()).append("; ");
		sb.append("instrument:").append(instr.getCode()).append("; ");
		sb.append("amount:").append(amt).append("]");
		return sb.toString();
	}
	
	public Balance copyBalance() {
		return new Balance(this);
	}
}