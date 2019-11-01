package com.nickperov.stud.tranprocessor.data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.nickperov.stud.tranprocessor.data.Instrument.InstrumentCode;

public class Index {
	
	private static final Index singletonInstance = new Index();
	
	private Index() {
		// TODO Check with non synchronised Map
		accounts	= new ConcurrentHashMap<>();
		instruments	= new ConcurrentHashMap<>();
		balances	= new ConcurrentHashMap<>();
	}
	
	public static Index getIndex() {
		return singletonInstance;
	}
	
	public void addAccount(Account account) {
		final String code = account.getCode();
		if (accounts.get(code) == null)
			accounts.put(code, account);
	}
	
	public void addInstrument(Instrument instrument) {
		final InstrumentCode code = instrument.getCode();
		if (instruments.get(code) == null)
			instruments.put(code, instrument);
	}
	
	public void addBalance(Balance balance) {
		final Key<AbstractDataEntity> key = balance.getKey();
		if (balances.get(key) == null)
			balances.put(key, balance);
	}
	
	public Account getAccount(String code) {
		return accounts.get(code);
	}
	
	public List<Account> getAllAccounts() {
		return accounts.values().stream().collect(Collectors.toList());
	}
	
	public Instrument getInstrument(InstrumentCode code) {
		return instruments.get(code);
	}
	
	public List<Instrument> getAllInstruments() {
		return instruments.values().stream().collect(Collectors.toList());
	}
	
	public Balance getBalance(Account account, Instrument instrument) {		
		final Key<AbstractDataEntity> key = Balance.getKey(account, instrument);
		return balances.get(key);
	}
	
	public List<Balance> getAllBalances() {
		return balances.values().stream().collect(Collectors.toList());
	}
	
	private final Map<String, Account> accounts;
	
	private final Map<InstrumentCode, Instrument> instruments;
	
	private final Map<Key<AbstractDataEntity>, Balance> balances;
}