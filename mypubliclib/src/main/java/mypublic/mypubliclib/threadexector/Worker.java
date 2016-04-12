package mypublic.mypubliclib.threadexector;

import java.util.concurrent.Callable;

public class Worker<T> implements Callable<T> {

	ProxyAction proxyDoAction;
	private Boolean isStart = false; // 是否已经启动
	
	public Worker(ProxyAction proxyDoAction){
		this.proxyDoAction=proxyDoAction;
	}

	public T call() throws Exception {

		synchronized (isStart) {
			isStart = true;
		}
		if (proxyDoAction != null)
			proxyDoAction.doAction();

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
