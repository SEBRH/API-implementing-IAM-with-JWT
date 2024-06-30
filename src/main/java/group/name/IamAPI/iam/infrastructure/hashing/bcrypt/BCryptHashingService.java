package group.name.IamAPI.iam.infrastructure.hashing.bcrypt;

import group.name.IamAPI.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
