package com.nickperov.stud.tranprocessor.data;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractDataEntity {
	
	private static final AtomicInteger sequenceGenerator = new AtomicInteger(0);
	
	AbstractDataEntity() {
		this.id = sequenceGenerator.getAndIncrement();
	}
	
	protected Integer id;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj != null) {
			if (obj instanceof AbstractDataEntity)
				return this.id.equals(((AbstractDataEntity)obj).id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}