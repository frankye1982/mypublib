package mypublic.mypubliclib.threadexector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Exector {
	
	ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(50);
	
	public void setExecutor(ThreadPoolExecutor threadPoolExecutor){
		this.executor = threadPoolExecutor;
	}

	/**
	 *  执行器
	 * @param action
	 */
	public void  doExectorWithoutResult(List<ProxyAction> actions){
		
		try {
			for(ProxyAction action:actions){
				Worker<Object> worker = new Worker<Object>(action);
				executor.submit(worker);
			}		
	        
			
			try {
				Thread.sleep(100);   //等待worker开始工作
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}  
			
			while(executor.getActiveCount()>0){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		} finally {
			executor.shutdown();
		}
	
	}
	
	
	/**
	 * 附带结果的执行器
	 * @param actions
	 * @return
	 */
	public <T> List<T> doExectorWithResult(List<GetProxyAction<T>> actions){
		try {
			
			
			List<T> result = new ArrayList<T>();
			List<Future<T>> fuResult = new ArrayList<Future<T>>();
			
			for(GetProxyAction<T> action:actions){
				ResultWorker<T> worker = new ResultWorker<T>(action);
				Future<T> execRet = executor.submit(worker);
				fuResult.add(execRet);
			}		
	        
			
			try {
				Thread.sleep(100);   //等待worker开始工作
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}  
			
			while(executor.getActiveCount()>0){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(Future<T> future:fuResult){
				try {
					T o =future.get();
					result.add(o);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			return result;
			
		} finally {
			executor.shutdown();
		}	
	}
	
	
	
	
}
