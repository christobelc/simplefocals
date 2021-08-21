package com.github.simplefocals;

import com.github.simplefocals.entity.User;
import com.github.simplefocals.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RegisterTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserByUsername(){
        User testUser = userRepository.getUserByUsername("test@test.com");
        assertThat(testUser.getEmail()).isEqualTo("test@test.com");
    }
}
