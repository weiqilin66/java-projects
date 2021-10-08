package org.wayne.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-01-28
 */
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Hr implements Serializable, UserDetails {
    private int id;

    private String name;

    private String phone;

    private String telephone;

    private String address;

    private Boolean enabled;

    private String username;

    private String password;

    private String userface;

    private String remark;
    @TableField(exist = false)
    private List<Role> roles;

    // 返回用户的所有roles
    @Override
    @JsonIgnore // json2obj封装时候忽略这个属性
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authrities = new ArrayList<>();
        for (Role role : roles) {
            authrities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authrities;
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
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

//    public Boolean getEnabled() {
//        return enabled;
//    }

    public String getUserface() {
        return userface;
    }

    public int getId() {
        return id;
    }

    public String getRemark() {
        return remark;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
