package com.ng.mats.psa.mt.readycash.util;

import java.io.IOException;

public class HexConverter {
	String stringToHex(String str) {
		char[] chars = str.toCharArray();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			strBuffer.append(Integer.toHexString((int) chars[i]));
		}
		return strBuffer.toString();
	}

	public static String hexToString(String txtInHex) {
		byte[] txtInByte = new byte[txtInHex.length() / 2];
		int j = 0;
		for (int i = 0; i < txtInHex.length(); i += 2) {
			txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
		}
		return new String(txtInByte);
	}

	public static String byteToHex(byte b) {
		int i = b & 0xFF;
		return Integer.toHexString(i);
	}

	public static void main(String[] args) throws IOException {
		// System.out.print("Enter the String : ");
		byte[] encryptedData = null;
		String message = "5432";
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		// String inputString = br.readLine();
		System.out.println("PIN before encryption : " + message);
		TripleDES tripleDes = new TripleDES();
		try {
			encryptedData = tripleDes.encrypt(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Secret key : " + tripleDes.secretKey);
		System.out.println("String after encryption in TDES : "
				+ encryptedData.toString());
		HexConverter obj = new HexConverter();
		String hexString = obj.stringToHex(encryptedData.toString());
		System.out.println("String in hexa form : " + hexString);
		String newHexValue = hexToString(hexString);
		System.out.println("Hex converted back to string is : " + newHexValue);
		String decryptedValue = "";
		try {
			decryptedValue = tripleDes.decrypt(newHexValue.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Decrypted value is : " + decryptedValue);

	}
}
