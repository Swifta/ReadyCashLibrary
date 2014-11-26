package com.ng.mats.psa.mt.readycash.model;

import javax.xml.bind.annotation.XmlTransient;

public class MoneyTransferResponse {
	private String sender;
	private String receiver;
	private long amount;
	@XmlTransient
	private MoneyTransferStatus status;
	private String reference;
	private String transactionId;
	private String statusMessage;
	private String Commission;

	public MoneyTransferResponse(String sender, String receiver, long amount,
			String transactionId, String reference, String statusMessage,
			String Commission) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.transactionId = transactionId;
		// this.status = status;
		this.reference = reference;
		this.statusMessage = statusMessage;
		this.Commission = Commission;
	}

	public MoneyTransferResponse() {
		// TODO Auto-generated constructor stub
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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public MoneyTransferStatus getStatus() {
		return status;
	}

	public void setStatus(MoneyTransferStatus status) {
		this.status = status;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	

	public String getCommission() {
		return Commission;
	}

	public void setCommission(String commission) {
		Commission = commission;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sender: " + sender + "\n");
		sb.append("Receiver: " + receiver + "\n");
		sb.append("Amount: " + amount + "\n");
		sb.append("Reference: " + reference + "\n");
		sb.append("TransactionId: " + transactionId + "\n");
		// sb.append("Status: " + status + "\n");

		return sb.toString();
	}

}
