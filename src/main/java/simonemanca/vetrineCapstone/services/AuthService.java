package simonemanca.vetrineCapstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simonemanca.vetrineCapstone.entities.User;
import simonemanca.vetrineCapstone.exceptions.UnauthorizedException;
import simonemanca.vetrineCapstone.repositories.UserRepository;
import simonemanca.vetrineCapstone.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String email, String password) {
        System.out.println("Tentativo di login per l'email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Utente non trovato."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Tentativo di login fallito per l'email: " + email);
            throw new UnauthorizedException("Password errata.");
        }
        System.out.println("Login riuscito per l'email: " + email);
        return user;
    }

    public String generateToken(User user) {
        return jwtTools.createToken(user.getUsername(), user.getId());
    }
}

