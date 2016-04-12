package mypublic.mypubliclib;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import mypublic.mypubliclib.threadexector.Exector;
import mypublic.mypubliclib.threadexector.GetProxyAction;
import mypublic.mypubliclib.threadexector.ProxyAction;

public class TestSimpleThread {

	@Test
	public void countSimpleNum() {
		Exector exector = new Exector();

		List<ProxyAction> actions = new ArrayList<ProxyAction>();
		ProxyAction action = new ProxyAction() {

			@Override
			public void doAction() {
				System.out.println(Thread.currentThread().getName());

			}

		};
		actions.add(action);
		actions.add(action);
		actions.add(action);

		exector.doExectorWithoutResult(actions);
	}

	
	@Test
	public void countResultTest() {
		Exector exector = new Exector();

		List<GetProxyAction<Long>> actions = new ArrayList<GetProxyAction<Long>>();
		GetProxyAction<Long> action = new GetProxyAction<Long>() {

			@Override
			public Long getAction() {
				return System.currentTimeMillis();
			}


		};
		actions.add(action);
		actions.add(action);
		actions.add(action);

		List<Long> result = exector.doExectorWithResult(actions);
		System.out.println(result);
	}
}
