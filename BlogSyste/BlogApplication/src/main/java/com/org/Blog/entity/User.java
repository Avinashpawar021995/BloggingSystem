package com.org.Blog.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(unique = true, nullable = false)
	private String email;

	private String password;
	private String about;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Post> posts = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinTable(name="role",
//	  joinColumns=@JoinColumn(name="User", referencedColumnName ="id"),
//	  inverseJoinColumns = @JoinColumn(name="Role", referencedColumnName = "roleid"))

	@JoinTable(joinColumns = @JoinColumn(name = "user_Id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleid"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authories = this.roles.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

		return authories;
	}

	@Override
	public String getUsername() {
		return this.email;

	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
