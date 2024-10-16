package uis.edu.entorno.tournament.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uis.edu.entorno.tournament.model.Roles;
import uis.edu.entorno.tournament.model.Usuario;
import uis.edu.entorno.tournament.repository.IUsuarioRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    private IUsuarioRepository usuarioRepo;

    @Autowired
    public  CustomUsersDetailsService(IUsuarioRepository usuarioRepo){
        this.usuarioRepo = usuarioRepo;
    }

    public Collection<GrantedAuthority> mapToAuthorities(List<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuario.getUsername(), usuario.getPassword(), mapToAuthorities(usuario.getRoles()));
    }
}
