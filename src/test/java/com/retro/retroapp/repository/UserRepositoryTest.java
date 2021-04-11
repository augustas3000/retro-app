package com.retro.retroapp.repository;

import com.retro.retroapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepositoryUnderTest;

    @Test
    public void findByUsername_HappyPath_ShouldReturn1User() {
        //given
        User user = new User();
        user.setUsername("user1");
        user.setPassword("baba1");
        user.setRole("USER");

        userRepositoryUnderTest.save(user);

        //when
        Optional<User> userFound = userRepositoryUnderTest.findByUsername(user.getUsername());

        //then
        assertThat(userFound).isPresent().hasValueSatisfying(u -> assertThat(u).isSameAs(user));
    }

    @Test
    public void save_HappyPath_ShouldSave1User() throws Exception {
        //given
        User user = new User();
        user.setUsername("user1");
        user.setPassword("baba1");
        user.setRole("USER");


        //when
        User actual = userRepositoryUnderTest.save(user);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

}
