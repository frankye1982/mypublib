package mypublic.mypubliclib.threadexector;

import java.util.concurrent.Callable;

public class ResultWorker <T> implements Callable<T>{

	GetProxyAction<T> getProxyDoAction;
	private Boolean isStart = false; // 是否已经启动
	
	public ResultWorker(GetProxyAction<T> getProxyAction){
		this.getProxyDoAction=getProxyAction;
	}

	public T call() throws Exception {

		synchronized (isStart) {
			isStart = true;
		}

		if (getProxyDoAction != null)
			return	getProxyDoAction.getAction();

		return null;
	}

	public void waitingToStart() {
		while (true) {
			synchronized (isStart) {
				if (isStart == true)
					break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
