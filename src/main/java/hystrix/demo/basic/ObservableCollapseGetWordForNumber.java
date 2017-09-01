package hystrix.demo.basic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.netflix.hystrix.HystrixCollapser.CollapsedRequest;
import com.netflix.hystrix.HystrixObservableCollapser;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import hystrix.demo.basic.ObservableCommandNumbersToWords.NumberWord;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * 
 * Example that uses {@link HystrixObservableCollapser} to batch multiple {@link ObservableCommandNumbersToWords} requests.
 * 
 * @author yuezh2   2017年8月31日 下午3:30:29
 *
 */
public class ObservableCollapseGetWordForNumber extends HystrixObservableCollapser<Integer, NumberWord, String, Integer>{

	private final Integer number;
	
	private final static AtomicInteger counter = new AtomicInteger();
	
	
	
	
	public static void resetCmdCounter(){
		counter.set(0);
	}

	
	public static int getCmdCount(){
		return counter.get();
	}
	
	
	public ObservableCollapseGetWordForNumber(final Integer number) {
		this.number = number;
	}
		
	
	
	@Override
	protected HystrixObservableCommand<NumberWord> createCommand(Collection<CollapsedRequest<String, Integer>> requests) {
		final int count = counter.incrementAndGet();
		System.out.println("Creating batch for "+requests.size()+" requests . Total invocations so far : "+count);
		
		final List<Integer> numbers = new ArrayList<>();
		for(final CollapsedRequest<String, Integer> request : requests){
			numbers.add(request.getArgument());
		}
		
		return new ObservableCommandNumbersToWords(numbers);
	}

	@Override
	protected Func1<NumberWord, Integer> getBatchReturnTypeKeySelector() {
		// Java 8: (final NumberWord nw) -> nw.getNumber();
		
		return new Func1<ObservableCommandNumbersToWords.NumberWord, Integer>() {

			@Override
			public Integer call(final NumberWord nw) {
				return nw.getNumber();
			}
			
		};
	}

	@Override
	protected Func1<NumberWord, String> getBatchReturnTypeToResponseTypeMapper() {
		// Java 8: return (final NumberWord nw) -> nw.getWord();
		
		return new Func1<ObservableCommandNumbersToWords.NumberWord, String>() {

			@Override
			public String call(final NumberWord nw) {
				return nw.getWord();
			}
			
		};
	}

	@Override
	public Integer getRequestArgument() {
		return number;
	}

	@Override
	protected Func1<Integer, Integer> getRequestArgumentKeySelector() {
		// Java 8: return (final Integer no) -> no;
		
		return new Func1<Integer, Integer>() {

			@Override
			public Integer call(final Integer no) {
				return no;
			}
			
		};
	}

	@Override
	protected void onMissingResponse(CollapsedRequest<String, Integer> request) {
		request.setException(new Exception("no word"));
	}

	
	
	
	
	
	public static class ObservableCollapserGetWordForNumberTest{
		
		private HystrixRequestContext context;
		
		
		@Before
		public void before(){
			context = HystrixRequestContext.initializeContext();
			ObservableCollapseGetWordForNumber.resetCmdCounter();
		}
		
		
		@After
		public void after(){
			System.out.println(HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());
			context.shutdown();
		}
		
		
		/**
		 * Example where we subscribe without using a specific scheduler. That means we run the actions on the same thread.
		 */
		@Test
		public void shouldCollapseRequestSync(){
			final int noOfRequests = 10;
			final Map<Integer, TestSubscriber<String>> subscribersByNumber = new HashMap<>(noOfRequests);
			
			TestSubscriber<String> subscriber;
			
			for(int number =0;number<noOfRequests;number++){
				subscriber = new TestSubscriber<String>();
				new ObservableCollapseGetWordForNumber(number).toObservable().subscribe(subscriber);
				subscribersByNumber.put(number, subscriber);
				// wait a little bit after running half of the requests so that we don't collapse all of them into one batch
				// TODO this can probably be improved by using a test scheduler
				if(number == noOfRequests/2){
					sleep(1000);
				}
			}
			
			
			assertThat(subscribersByNumber.size() , is(noOfRequests));
			
			for(final Entry<Integer, TestSubscriber<String>> subscriberByNumber : subscribersByNumber.entrySet()){
				subscriber = subscriberByNumber.getValue();
				subscriber.awaitTerminalEvent(10, TimeUnit.SECONDS);
				
				assertThat(subscriber.getOnErrorEvents().toString(), subscriber.getOnErrorEvents().size() , is(0));
				assertThat(subscriber.getOnNextEvents().size(), is(1));
				
				final String word = subscriber.getOnNextEvents().get(0);
				System.out.println("Translated "+subscriberByNumber.getKey() +" to "+word);
				assertThat(word, equalTo(numberToWord(subscriberByNumber.getKey())));
			}
			
			assertTrue(ObservableCollapseGetWordForNumber.getCmdCount()>1);
			assertTrue(ObservableCollapseGetWordForNumber.getCmdCount()<noOfRequests);
			
		}
		
		
		private String numberToWord(final int number)
		{
			return ObservableCommandNumbersToWords.dict.get(number);
		}
		
		
		private void sleep(final long ms)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (final InterruptedException e)
			{
				throw new IllegalStateException(e);
			}
		}
		
		
		
	}
	
	
	
	
}
