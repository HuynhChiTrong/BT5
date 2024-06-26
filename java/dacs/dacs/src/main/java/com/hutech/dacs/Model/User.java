package com.hutech.dacs.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", length = 50, unique = true)
    @NotBlank(message = "Ten nguoi dung khong duoc de trong")
    @Size(min = 1, max = 50, message = "Ten co it nhat 1 ki tu den 50 ki tu")
    private String username;

    @Column(name = "password", length = 250)
    @NotBlank(message = "Mat khau khong duoc de trong")
    /*@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")*/
    /*@Size(min = 8, max = 1000, message = "Mat khau co it nhat 8 ki tu")*/
    private String password;

    @Column(name = "confirm_password", length = 250)
    @NotBlank(message = "Confirm password is required")
    private String confirm_password;


    @Column(name = "email", length = 50, unique = true)
    @NotBlank(message = "Mail khong duoc de trong")
    @Size(min = 1, max = 50, message = "Mail khong qua 50 ki tu")
    @Email
    private String email;

    @Column(name = "phone", length = 10, unique = true)
    @Length(min = 10, max = 10, message = "So dien thoai chi co 10 so")
    @Pattern(regexp = "^[0-9]*$", message = "Phone must be number")
    private String phone;

    /*@Column(name = "provider", length = 50)
    private String provider;*/

    /*@Column(name = "dob")
    @Past(message = "Ngay sinh phai o qua khu")
    private LocalDate dob;

    @Column(name = "address", length = 200)
    @Size(min = 1, max = 200, message = "Dia chi khong qua 200 ki tu")
    private String address;*/

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> userRoles = this.getRoles();
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return
                false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
