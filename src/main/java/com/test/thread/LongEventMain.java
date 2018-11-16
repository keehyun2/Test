package com.test.thread;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebElement;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class LongEventMain {
	public static void main(String[] args) throws Exception {
		
		Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		// Executor that will be used to construct new threads for consumers
		Executor executor = Executors.newCachedThreadPool();

		// The factory for the event
		LongEventFactory factory = new LongEventFactory();

		// Specify the size of the ring buffer, must be power of 2.
		int bufferSize = 1024;

		// Construct the Disruptor
		Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);

		// Connect the handler
		disruptor.handleEventsWith(new LongEventHandler());

		// Start the Disruptor, starts all threads running
		disruptor.start();

		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

		LongEventProducer producer = new LongEventProducer(ringBuffer);

		ByteBuffer bb = ByteBuffer.allocate(8);
		List<WebElement> list = new ArrayList<WebElement>();
		String searchKeyword = "진공포장기";
		StringBuilder sb = new StringBuilder();
		sb.append("https://search.shopping.naver.com/search/all.nhn");
		sb.append("?origQuery=%s&pagingIndex=1&pagingSize=80&viewType=list&sort=price_asc&frm=NVSHATC&sps=Y&query=%s");
		producer.onData(list, String.format(sb.toString(), searchKeyword, searchKeyword));
		System.out.println("실행완료");
//		for (long l = 0; true; l++) {
//			//bb.putLong(0, l);
//			producer.onData(bb);
//			producer.set
//			Thread.sleep(1000);
//		}
	}
}
