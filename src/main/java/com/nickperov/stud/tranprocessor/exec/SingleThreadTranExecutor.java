package com.nickperov.stud.tranprocessor.exec;

import java.util.List;

import com.nickperov.stud.tranprocessor.tran.TransactionExecutor;

public class SingleThreadTranExecutor implements TransactionExecutorService {

	// TODO take transactions from queue
	
	
	private static final SingleThreadTranExecutor singletonInstance = new SingleThreadTranExecutor();
	
	private SingleThreadTranExecutor() {}
	
	public static SingleThreadTranExecutor getExecutorService() {
		return singletonInstance;
	}
	
	private List<TransactionExecutor> tranDocs;
	
	@Override
	public void setTransDocumentList(List<TransactionExecutor> tranDocs) {
		this.tranDocs = tranDocs;
	}
	
	@Override
	public void startTranService() {
		tranDocs.forEach(tranDoc -> tranDoc.execute());
	}

	@Override
	public void reInit() {}
}