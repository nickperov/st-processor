package com.nickperov.stud.tranprocessor.tran;

import java.util.concurrent.Callable;

public interface TransactionExecutor extends Callable<Void> {

	/*default void run() {
		execute();
	}*/

	@Override
	default Void call() {
		execute();
		return null;
	}

	void execute();
}