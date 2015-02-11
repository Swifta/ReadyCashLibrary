package com.ng.mats.psa.mt.readycash.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.jpos.iso.ISOUtil;

public class TripleDES {
	public final static String secretKey = "636948778095358323114731";
	public static String CHARSET = "UTF-8";

	public static void main(String[] args) throws Exception {

		String text = "kachi";

		byte[] codedtext = new TripleDES().encrypt(text);
		String decodedtext = new TripleDES().decrypt(codedtext);

		System.out.println(codedtext); // this is a byte array, you'll just see
										// a reference to an array
		System.out.println(decodedtext); // This correctly shows "kyle boon"
		System.out.println(encrypt(secretKey, text));
	}

	public static String encrypt(String key, String data) throws Exception {
		byte[] plainBytes = data.getBytes();
		int origSize = plainBytes.length;
		if (origSize % 8 != 0) {
			int padding = 8 - origSize % 8;
			int newSize = origSize + padding;

			byte[] newInputBytes = new byte[newSize];
			System.arraycopy(plainBytes, 0, newInputBytes, 0, origSize);
			plainBytes = newInputBytes;

		}

		System.out.println("Key " + key + " Data " + data);
		Cipher cipher = Cipher.getInstance("DESEde/ECB/NoPadding");
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(Charset
				.forName(CHARSET)));
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DESEde");
		SecretKey skey = factory.generateSecret(spec);

		cipher.init(Cipher.ENCRYPT_MODE, skey);
		byte[] ciphertext = cipher.doFinal(plainBytes);
		System.out.println("Encrypted AS :" + new String(ciphertext));
		return ISOUtil.hexString(ciphertext);
	}

	public byte[] encrypt(String message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
		/*
		 * System.out.println("Digest lenght : " + md.getDigestLength() +
		 * " | Digest value : " + md.toString());
		 * System.out.println("Password Digest : " +
		 * digestOfPassword.toString());
		 */final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}
		// System.out.println("Password Digest final : " + keyBytes.toString());
		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		// System.out.println(key.getEncoded());
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		final byte[] plainTextBytes = message.getBytes("utf-8");
		final byte[] cipherText = cipher.doFinal(plainTextBytes);
		// final String encodedCipherText = new sun.misc.BASE64Encoder()
		// .encode(cipherText);

		return cipherText;
	}

	public String decrypt(byte[] message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, key, iv);

		// final byte[] encData = new
		// sun.misc.BASE64Decoder().decodeBuffer(message);
		final byte[] plainText = decipher.doFinal(message);

		return new String(plainText, "UTF-8");
	}
}