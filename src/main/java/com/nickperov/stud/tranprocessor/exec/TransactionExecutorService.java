package com.nickperov.stud.tranprocessor.exec;

import com.nickperov.stud.tranprocessor.tran.TransactionExecutor;
import java.util.List;

public interface TransactionExecutorService {

	void setTransDocumentList(List<TransactionExecutor> tranDocs);

	void startTranService();

	void reInit();
}
