package com.nickperov.stud.tranprocessor.data;

import com.nickperov.stud.tranprocessor.data.Instrument.InstrumentCode;

public class EntityUtils {
	
	private EntityUtils() {}
	
	private static final Index index = Index.getIndex();
	
	public static Balance getOrCreateBalance(Account account, Instrument instrument) {
		Balance balance = index.getBalance(account, instrument);
		if (balance == null) {
			balance = new Balance(account, instrument);
			index.addBalance(balance);
		}
		return balance;
	}
	
	public static Account getOrCreateAccount(String code) {
		Account account = index.getAccount(code);
		if (account == null) {
			account = new Account(code);
			index.addAccount(account);
		}
		return account;
	}
	
	public static Instrument getOrCreateInstrument(InstrumentCode code) {
		Instrument instrument = index.getInstrument(code);
		if (instrument == null) {
			instrument = new Instrument(code);
			index.addInstrument(instrument);
		}
		return instrument;
	}
}