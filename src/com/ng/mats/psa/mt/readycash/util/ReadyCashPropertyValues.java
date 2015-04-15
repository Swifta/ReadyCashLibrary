package com.ng.mats.psa.mt.readycash.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Logger;

import com.ng.mats.psa.mt.readycash.model.MoneyTransfer;

public class ReadyCashPropertyValues {
	private static final Logger logger = Logger
			.getLogger(ReadyCashPropertyValues.class.getName());

	public MoneyTransfer getPropertyValues() {
		// for cashout, payernumber = customer and reciever number = agent:::::
		// for cashing, payernumber = agent and reciever number = customer

		MoneyTransfer moneyTransfer = new MoneyTransfer();
		Properties prop = new Properties();
		String propFileName = "com/ng/mats/psa/mt/readycash/util/config.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);
		try {
			if (inputStream != null) {

				prop.load(inputStream);

			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// get the property value and print it out
		String parameterType = prop.getProperty("settings-type");
		String key = prop.getProperty("Key_" + parameterType);
		logger.info("THE CONFIGURATION BEING USED AT THIS POINT IS ==========================="
				+ parameterType);

		moneyTransfer.setAmount(BigDecimal.valueOf(Long.valueOf(prop
				.getProperty("Amount_" + parameterType))));
		moneyTransfer.setMmo(prop.getProperty("Mmo_" + parameterType));
		moneyTransfer.setAgentUsername(prop.getProperty("AgentUsername_"
				+ parameterType));
		moneyTransfer.setReadyCashPin(prop.getProperty("ReadyCashPin_"
				+ parameterType));
		moneyTransfer
				.setReceiver(prop.getProperty("Receiver_" + parameterType));
		moneyTransfer.setBankAccountNumber(prop
				.getProperty("BankAccountNumber_" + parameterType));
		moneyTransfer.setBank(prop.getProperty("Bank_" + parameterType));
		moneyTransfer.setBankAccountName(prop.getProperty("BankAccountName_"
				+ parameterType));
		moneyTransfer.setPassword(prop.getProperty("ReadyCashPin_"
				+ parameterType));
		moneyTransfer
				.setAgentPin(prop.getProperty("AgentPin_" + parameterType));
		moneyTransfer.setSender(prop.getProperty("Sender_" + parameterType));
		moneyTransfer.setReference(prop.getProperty("Reference_"
				+ parameterType));
		moneyTransfer.setBankPhoneNumber(prop
				.getProperty("BankAccountPhoneNumber_" + parameterType));
		moneyTransfer.setParameterType(parameterType);
		if (parameterType.equalsIgnoreCase("production")) {

			TripleDES tripleDes = new TripleDES();
			try {
				moneyTransfer.setReadyCashPin(tripleDes.encrypt(key,
						moneyTransfer.getReadyCashPin()));
				moneyTransfer.setAgentPin(tripleDes.encrypt(key,
						moneyTransfer.getAgentPin()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return moneyTransfer;
	}
}
