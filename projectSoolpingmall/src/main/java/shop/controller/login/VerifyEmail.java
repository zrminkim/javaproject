package shop.controller.login;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class VerifyEmail {
	// 생성된 임시비밀번호를 담은 이메일을 자바에서 전송하는 코드 
	public String sendEmail(String user_email) {
		
		RandomPasswdUtil passwdUtil = new RandomPasswdUtil();	// autowired, private 왜 안되지?
		String newPasswd =  passwdUtil.generateRandomPassword(10); // 난수 알고리즘으로 10자의 임시 비밀번호 생성
		
		
	    String recipient = user_email; // 수신자 메일 주소
	    String title = "술핑몰에서 임시 비밀번호를 발급합니다"; // 메일 제목
	    String text = "회원님의 이메일로 가입된 계정이 있습니다.\r\n"
	    		+ "아래에 발급된 임시 비밀번호로 로그인한 후  반드시 비밀번호를 변경해주세요.\n 임시 비밀번호   :  "+newPasswd+""; // 메일 내용
	
	    // 1. 발신자의 메일 계정과 비밀번호 설정
	    final String user = "wlsrud0303@gmail.com";
	    final String password = "rfnpxbflvqlywykv";
	
	    // 2. Property에 SMTP 서버 정보 설정
	    Properties prop = new Properties();
	    prop.put("mail.smtp.host", "smtp.gmail.com");
	    prop.put("mail.smtp.port", 465);
	    prop.put("mail.smtp.auth", "true");
	    prop.put("mail.smtp.ssl.enable", "true");
	    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	    // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
	    Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(user, password);
	        }
	    });
	
	    // 4. Message 클래스의 객체를 사용하여 수신자와 내용, 제목의 메시지를 작성한다.
	    // 5. Transport 클래스를 사용하여 작성한 메세지를 전달한다.
	
	    MimeMessage message = new MimeMessage(session);
	    try {
	        message.setFrom(new InternetAddress(user));
	
	        // 수신자 메일 주소
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	
	        // Subject
	        message.setSubject(title);
	
	        // Text
	        message.setText(text);
	
	        Transport.send(message);    // send message
	        System.out.println("메일 전송 성공!");
	
	    } catch (AddressException e) {
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	    return newPasswd;
	}
	
	
}
