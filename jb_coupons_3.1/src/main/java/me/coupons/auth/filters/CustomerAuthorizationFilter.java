package me.coupons.auth.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.coupons.auth.utils.JwtUtil;
import me.coupons.entity.ClientType;
import me.coupons.rest.error_response_entity.ErrorResponse;

@Component
@Order(4)
public class CustomerAuthorizationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizatioHeader = request.getHeader("Authorization");
		if(authorizatioHeader == null || authorizatioHeader.equals("")) {
			
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
			errorResponse.setMessage("Unauthorized - not token found ");
			errorResponse.setTimeStamp(System.currentTimeMillis());
			
			byte[] responseToSend = restResponseBytes(errorResponse);
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(403);
            response.getOutputStream().write(responseToSend);
           
            return;
		}
		final String jwt = authorizatioHeader.substring(7);
		Map<String, Object>claims = jwtUtil.extractAllClaims(jwt);
		String role = (String)claims.get("role");
		
		if( ! role.equalsIgnoreCase(ClientType.CUSTOMER.getClientType()) ) {
			
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
			errorResponse.setMessage("Unauthorized ");
			errorResponse.setTimeStamp(System.currentTimeMillis());
			
			byte[] responseToSend = restResponseBytes(errorResponse);
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(403);
            response.getOutputStream().write(responseToSend);
           
            return;
		}
		
		filterChain.doFilter(request, response);
	}
	
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) {
//		String path = request.getServletPath();
//		if(path.equals("/authenticate") || path.startsWith("/admin") || path.startsWith("/company"))
//			return true;
//		else
//			return false;
//	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		if(path.startsWith("/api/customer")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }
}
