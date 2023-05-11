package shop.controller.signup;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component
public class HashingPasswd {
	// 회원가입 성공 - 입력받은 패스워드를 해싱(DB에 저장하기 전에 수행)

	private static final int STRETCH_SIZE = 3; // 스트레칭 횟수
	// private static final int SALT_SIZE = 5;

	// digest(Byte배열)를 16진수 문자열로 변환
	private String byteToString(byte[] digest) {
		StringBuilder sb = new StringBuilder();

		for (byte b : digest) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	/*
	 * // 특정 길이만큼의 임의의 문자열 생성
	 * public String makeRandomString(int length) {
	 * SecureRandom secureRandom = new SecureRandom();
	 * byte[] temp = new byte[length];
	 * 
	 * secureRandom.nextBytes(temp); // 문자열 생성
	 * 
	 * return byteToString(temp); // 16진수 문자열로 변환 후 반환
	 * }
	 * 
	 * 
	 * 
	 * //Salt값(랜덤 문자열) 생성
	 * public String createSalt() {
	 * return makeRandomString(SALT_SIZE);
	 * }
	 */

	// 비밀번호에 대한 해시 값을 반환
	public String getHashValue(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		for (int i = 0; i < STRETCH_SIZE; i++) {
			md.update(password.getBytes()); // 해시화
			password = byteToString(md.digest());// 16진수 문자열로 변환
			System.out.println("password lenth : " + password.length());
		}
		System.out.println("!" + password);
		return password;
	}

	/*
	 * // 비밀번호에 대한 해시 값을 반환(참고 : + Salt값)
	 * public String getHashValue(String password,String salt) throws Exception {
	 * MessageDigest md = MessageDigest.getInstance("SHA-256");
	 * 
	 * for (int i = 0; i < STRETCH_SIZE; i++) {
	 * String temp = password + salt;
	 * md.update(temp.getBytes()); // 해시화
	 * password = byteToString(md.digest());// 16진수 문자열로 변환
	 * System.out.println("password lenth : "+password.length());
	 * }
	 * 
	 * return password;
	 * }
	 */

}
