package com.spring.backproject.Service;

import com.spring.backproject.Models.Utilisateur;
import com.spring.backproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurdetaiSer implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Utilisateur user=userRepository.findByEmail(email);
        if(user==null){
        throw new UsernameNotFoundException(email);
        }
        UserDetails userDetails= User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("user").build();
        return userDetails;

    }
}
