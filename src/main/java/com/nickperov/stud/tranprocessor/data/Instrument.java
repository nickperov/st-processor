package com.nickperov.stud.tranprocessor.data;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Instrument extends AbstractDataEntity {

	private final ReentrantReadWriteLock rrwLock = new ReentrantReadWriteLock();

	Instrument(final InstrumentCode code) {
		this.code = code;
	}

	public enum InstrumentCode {
		USD, NZD, EUR
	}

	private InstrumentCode code;

	public InstrumentCode getCode() {
		return this.code;
	}

	public void lock() {
		this.rrwLock.writeLock().lock();
	}

	public void unLock() {
		this.rrwLock.writeLock().unlock();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Instrument [id:").append(this.id).append("; ");
		sb.append("code:").append(this.code).append("]");
		return sb.toString();
	}
}
