package com.spring.backproject.Controller;

import com.spring.backproject.Models.*;
import com.spring.backproject.Repository.UserRepository;
import com.spring.backproject.Service.EmailService;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Strings;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/auth")

@CrossOrigin(origins = "http://localhost:4200")
public class WebController {
    private static final int PASSWORD_LENGTH = 10;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserData userData) throws AuthenticationException {
    Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userData.getEmail(),
                        userData.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
           System.out.println("bienvenue");
            return ResponseEntity.ok().body(new AuthResponse(true, "Bienvenue, " + userData.getEmail() + "!"));
        } else {
           System.out.println("email ou de passe sont incorrects");
            return ResponseEntity.ok().body(new AuthResponse(false, "Email ou mot de passe incorrect"));
        }
        }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
if(userRepository.existsByEmail(registerDto.getEmail())){
    return ResponseEntity.ok().body(new AuthResponse(false, "Un compte qui,existe deja avec cet email"));
}
        Utilisateur user=new Utilisateur();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(),"Mail de confirmation","Bienvenue "+user.getEmail()+" votre compte est crée");
        return ResponseEntity.ok().body(new AuthResponse(true, "Compte cree, " + registerDto.getEmail() + "!"));
    }
    @PostMapping(value = "/mot-de-passe-oublie")
   // @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  motdepasseoublie(@RequestBody RestPasswor restPasswor){
        String newPassword = generateRandomPassword();
        String url="http://localhost:4200/changer-mdp";
        Utilisateur user=userRepository.findByEmail(restPasswor.getEmail());
        if(user==null){
            System.out.println("erreur");
            return ResponseEntity.ok().body(new AuthResponse(false, "erreur "));}
            // return new ResponseEntity<>("Aucun utilisateur avec cet email existe",HttpStatus.BAD_REQUEST);}
       else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            emailService.sendEmail(user.getEmail(),"Mot de passe changé ","Bienvenue "+user.getEmail()+" Votre mot de passe a été changé "+newPassword+" N'oubliez pas de changer votre mot de passe en cliquant sur ce lien "+url );
            System.out.println("email");
            return ResponseEntity.ok().body(new AuthResponse(true, "mail de changement de passe enoyé " ));
        }
    }

    public static String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
    }


    @PostMapping("/changer-mdp")
    public ResponseEntity<?> changemdp( @RequestBody Changemdp changemdp) {
      Utilisateur user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (user != null && passwordEncoder.matches(changemdp.getMdpgenere(), user.getPassword())) {
            if (changemdp.getNouveaumdp().equals(changemdp.getResetnouveaumdp())) {
                user.setPassword(passwordEncoder.encode(changemdp.getNouveaumdp()));
                userRepository.save(user);
                System.out.println("mdp change");
                return ResponseEntity.ok().body(new AuthResponse(true, "mot de passe change " ));
            } else {

                return ResponseEntity.ok().body(new AuthResponse(false, "Les deux champs de nouveau mot de passe doivent être identiques "));
            }
        } else {
            return ResponseEntity.ok().body(new AuthResponse(false, "Mot de passe actuel incorrect"));

        }}

}
