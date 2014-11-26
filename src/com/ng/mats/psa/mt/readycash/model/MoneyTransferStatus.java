package com.ng.mats.psa.mt.readycash.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum MoneyTransferStatus {
	@XmlEnumValue(value = "created")
	CREATED, 
	@XmlEnumValue(value = "pending")
	PENDING, 
	@XmlEnumValue(value = "complete")
	COMPLETED

}
