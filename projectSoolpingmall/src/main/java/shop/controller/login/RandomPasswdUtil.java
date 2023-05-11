package shop.controller.login;


import java.security.SecureRandom;


public class RandomPasswdUtil {
	// 비밀번호 찾기 성공 : 임시 비밀번호 생성(이메일로 전송하기 위한 난수 비밀번호)

	   
	
	  public String generateRandomPassword(int len) {	    
	        // ASCII 범위 – 영숫자(0-9, a-z, A-Z)
	        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	 
	        SecureRandom random = new SecureRandom();
	        StringBuilder sb = new StringBuilder();
	 
	        // 루프의 각 반복은 주어진 문자에서 무작위로 문자를 선택합니다.
	        // ASCII 범위를 `StringBuilder` 인스턴스에 추가합니다.
	 
	        for (int i = 0; i < len; i++){
	            int randomIndex = random.nextInt(chars.length());
	            sb.append(chars.charAt(randomIndex));
	        }
	        return sb.toString();
	    }
	
}
