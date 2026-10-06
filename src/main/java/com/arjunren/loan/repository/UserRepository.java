package com.arjunren.loan.repository;
import com.arjunren.loan.entity.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<AppUser,Long>{Optional<AppUser> findByEmailIgnoreCase(String email);}

