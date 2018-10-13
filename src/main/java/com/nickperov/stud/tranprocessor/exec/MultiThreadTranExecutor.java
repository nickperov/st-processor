package com.nickperov.stud.tranprocessor.exec;

import com.nickperov.stud.tranprocessor.tran.TransactionExecutor;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadTranExecutor implements TransactionExecutorService {

	// TODO take transactions from queue

	private static final MultiThreadTranExecutor singletonInstance = new MultiThreadTranExecutor();

	private ExecutorService executorService;

	private MultiThreadTranExecutor() {
		this.executorService = Executors.newWorkStealingPool(8);
	}

	public static MultiThreadTranExecutor getExecutorService() {
		return singletonInstance;
	}

	@Override
	public void reInit() {
		this.executorService = Executors.newWorkStealingPool(8);
	}

	private List<TransactionExecutor> tranDocs;

	@Override
	public void setTransDocumentList(final List<TransactionExecutor> tranDocs) {
		this.tranDocs = tranDocs;
	}

	@Override
	public void startTranService() {

		try {
			final List<Future<Void>> resultList = this.executorService.invokeAll(this.tranDocs);

			/*for (final Future<Void> result : resultList) {
				result.get();
			}*/

			boolean allDone = true;
			do {
				for (final Future<Void> result : resultList) {
					allDone &= result.isDone();
				}
			} while (!allDone);


		} catch (final InterruptedException e) {
			e.printStackTrace();
		}/* catch (final ExecutionException e) {
			e.printStackTrace();
		}*/
		/*this.tranDocs.forEach(tranDoc -> this.executorService.submit(tranDoc));
		this.executorService.shutdown();
		try {
			this.executorService.awaitTermination(1000l, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
