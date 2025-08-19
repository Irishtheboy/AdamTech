package za.co.admatech.repository;

import za.co.admatech.domain.RefreshToken;
import za.co.admatech.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    Optional<RefreshToken> findByToken(String token);
    
    void deleteByUser(User user);
    
    void deleteByExpiryDateBefore(LocalDateTime date);
    
    boolean existsByToken(String token);
}
