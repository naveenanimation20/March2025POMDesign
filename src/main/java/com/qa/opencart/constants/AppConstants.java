package com.qa.opencart.constants;

import java.util.List;

public class AppConstants {
	
	public static final int DEFAULT_SHORT_WAIT = 5;
	public static final int DEFAULT_MEDIUM_WAIT = 10;
	public static final int DEFAULT_LARGE_WAIT = 20;	
	
	public static final int DEFAULT_FOOTER_LINKS_COUNT = 15;	
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	
	public static final String ACC_PAGE_TITLE = "My Account";
	public static final String ACC_PAGE_FRACTION_URL = "route=account/account";
	
	public static final String LOGIN_INVALID_CREDS_MESSG = "Warning: No match for E-Mail Address and/or Password.";
	public static final String LOGIN_BLANK_CREDS_MESSG = "Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.";


	
	public static final int ACC_PAGE_HEADERS_COUNT = 4;
	
	public static final String USER_REGISTER_SUCCESS_MESSG = "Your Account Has Been Created!";
	
	public static List<String> expectedAccPageHeadersList = List.of("My Account", 
																	"My Orders",
																	"My Affiliate Account",
																	"Newsletter");
}
