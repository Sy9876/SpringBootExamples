package cn.sy.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

public class MyUserDetails extends User implements UserDetails {

	private static Logger logger = LoggerFactory.getLogger(MyUserDetails.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2978047440845128709L;

	private final String AccountEnabled = "1";
	private final String AccountDisabled = "0";
	private final String AccountExpired = "2";
	private final String AccountLocked = "3";
	
	List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

	public MyUserDetails() {
		super();
	}
	public MyUserDetails(String id, String name, String status, String password) {
		super(id, name, status, password);
	}

	public MyUserDetails(User user) {
		this(user.id, user.name, user.status, user.password);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
//		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return authorities;
	}

	public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public void setMenus(List<Map<String, String>> menus) {
		for(Map<String, String> menu : menus) {
			String authorityName = "ROLE_" + menu.get("menuName");
			logger.info("setMenus  menuName=" + authorityName);
			authorities.add(new SimpleGrantedAuthority(authorityName));
		}
	}
	
	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		if(this.status.equals(AccountExpired)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if(this.status.equals(AccountLocked)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if(!this.status.equals(AccountEnabled)) {
			return false;
		}
		return true;
	}


}
