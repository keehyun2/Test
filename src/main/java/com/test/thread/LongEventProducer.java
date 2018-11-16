package com.test.thread;

import java.nio.ByteBuffer;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.lmax.disruptor.RingBuffer;

public class LongEventProducer {
	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(List<WebElement> list,String url) {
		long sequence = ringBuffer.next(); // Grab the next sequence
		try {
			LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor for the sequence
//			event.setValue(bb.getLong(0)); // Fill with data
			event.setList(list);
			event.setUrl(url);
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}
