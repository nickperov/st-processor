package com.nickperov.stud.tranprocessor.data;

import java.util.Arrays;

public class Key<T> {

	private T[] values;
	
	@SafeVarargs
	public Key(T... values) {
		this.values = values;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(values);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj != null && obj instanceof Key)
			return Arrays.equals(this.values, ((Key<?>)obj).values);
		
		return false;
	}
	
	
	
}
