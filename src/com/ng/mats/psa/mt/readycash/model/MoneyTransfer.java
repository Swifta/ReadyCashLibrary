package com.ng.mats.psa.mt.readycash.model;

import java.math.BigDecimal;

public class MoneyTransfer {

	private String sender;
	private String receiver;
	private String transactionType;
	private BigDecimal amount;
	private String mmo;
	private String readyCashPin;
	private String agentUsername;
	private String requestreference;
	private String agentPin;
	private String bankAccountName;
	private String bankAccountNumber;
	private String bank;
	// @XmlTransient
	// private CashOutStatus status = CashOutStatus.PENDING;
	private String reference;

	public MoneyTransfer(String sender, String receiver,
			String transactionType, long amount, String mmo, String reference,
			String agentUsername, String teasypin) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.transactionType = transactionType;
		this.amount = BigDecimal.valueOf(amount);
		this.mmo = mmo;
		this.readyCashPin = readyCashPin;
		this.agentUsername = agentUsername;
		// this.teasyrequestreference = "201407010000001";
		// this.status = status;
		this.reference = reference;
	}

	public MoneyTransfer() {

	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMmo() {
		return mmo;
	}

	public void setMmo(String mmo) {
		this.mmo = mmo;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	// public CashOutStatus getStatus() {
	// return status;
	// }
	//
	// public void setStatus(CashOutStatus status) {
	// this.status = status;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getReadyCashPin() {
		return readyCashPin;
	}

	public void setReadyCashPin(String readyCashPin) {
		this.readyCashPin = readyCashPin;
	}

	public String getAgentUsername() {
		return agentUsername;
	}

	public void setAgentUsername(String agentUsername) {
		this.agentUsername = agentUsername;
	}

	public String getRequestreference() {
		return requestreference;
	}

	public void setRequestreference(String requestreference) {
		this.requestreference = requestreference;
	}

	public String getAgentPin() {
		return agentPin;
	}

	public void setAgentPin(String agentPin) {
		this.agentPin = agentPin;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sender: " + sender + "\n");
		sb.append("Receiver: " + receiver + "\n");
		sb.append("Amount: " + amount + "\n");
		sb.append("Reference: " + reference + "\n");
		sb.append("Mmo: " + mmo + "\n");
		sb.append("Agent Username: " + agentUsername + "\n");
		sb.append("Agent password: " + readyCashPin + "\n");
		sb.append("Agent pin: " + agentPin + "\n");

		// sb.append("Status: " + status + "\n");

		return sb.toString();
	}

}
