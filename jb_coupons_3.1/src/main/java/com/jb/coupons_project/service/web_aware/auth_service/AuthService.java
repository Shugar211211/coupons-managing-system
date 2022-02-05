package com.jb.coupons_project.service.web_aware.auth_service;

import com.jb.coupons_project.auth.models.Principal;
import com.jb.coupons_project.auth.models.UserDetails;

public interface AuthService {

	/**
	 * Authentication service
	 * @param userDetails
	 * @return principal
	 * @throws UserNotFoundException if can not authenticate.
	 */
	public Principal authenticate(UserDetails userDetails);
}
