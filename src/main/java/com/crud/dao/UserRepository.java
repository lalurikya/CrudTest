package com.crud.dao;

import com.crud.dto.user.UserDTO;
import com.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        SELECT COUNT(acc.username)
        FROM User AS acc
        WHERE acc.username = :username
    """)
    Long countExistingUser(@Param("username") String username);


    @Query("""
        SELECT new com.crud.dto.user.UserDTO(
        use.id,
        use.username,
        use.createdBy,
        use.createdAt,
        use.updatedBy,
        use.updatedAt,
        use.deletedBy,
        use.deletedAt)
        FROM User AS use
        WHERE use.username = :username
            """)
    public UserDTO findByUsername(@Param("username") String username);
}
