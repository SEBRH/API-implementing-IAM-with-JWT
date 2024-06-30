package group.name.IamAPI.iam.infrastructure.hashing.bcrypt.services;

import group.name.IamAPI.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/***
 * HashingServiceImpl class implements BCryptHashingService interface
 * basically it is a service that hashes passwords using BCryptPasswordEncoder
 * @author Sebastian Ramirez Hoffmann
 */

@Service
public class HashingServiceImpl implements BCryptHashingService {
    private final BCryptPasswordEncoder passwordEncoder;

    public HashingServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /***
     * Encodes the raw password using BCryptPasswordEncoder
     * @param rawPassword
     * @return encoded password
     */

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /***
     * Matches the raw password with the encoded password
     * @param rawPassword
     * @param encodedPassword
     * @return boolean
     */

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
