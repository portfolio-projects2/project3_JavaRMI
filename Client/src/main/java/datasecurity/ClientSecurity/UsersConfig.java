package datasecurity.ClientSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import datasecurity.Enum.Role;

import java.util.Collection;

@Component
public class UsersConfig implements UserDetails {

    String _username;
    String cookie;

    public Role userRole;
    public boolean _isActiveSession;


    public String get_sessionAuthCookie(){
        return cookie;
    }

    public void set_sessionAuthCookie(String co){
        cookie=co;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }




    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return _username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void seUsername(String s) {
        _username=s;
    }

    public boolean activeSession() {

        return this._isActiveSession;
    }

    public boolean sessionStutus(){
        return this._isActiveSession;
    }

}
