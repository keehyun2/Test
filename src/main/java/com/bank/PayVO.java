package com.bank;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class PayVO {
	
	@Expose
	private String no; // 번호
	@Expose
	private String payDt; // 거래일시
	@Expose
	private String briefs; // 적요
	@Expose
	private String memo; // 송금메모
	@Expose
	private String outAmt; // 출금액 
	@Expose
	private String inAmt; // 입금액
	@Expose
	private String balance; // 잔액
	@Expose
	private String point; // 거래점
	
}
