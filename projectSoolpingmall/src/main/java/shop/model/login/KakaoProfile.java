package shop.model.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class KakaoProfile {

	private Long id;
	private String connected_at;
	private KakaoAccount kakao_account;
	
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	@Data
	public class KakaoAccount {

		//public Boolean hasEmail;
		//public Boolean emailNeedsAgreement;
		//public Boolean isEmailValid;
		//public Boolean isEmailVerified;
		private String email;
		//public Boolean hasBirthday;
		//public Boolean birthdayNeedsAgreement;
		private String birthday;
		//public String birthdayType;
		//public Boolean hasGender;
		//public Boolean genderNeedsAgreement;
		private String gender;

		}

}








