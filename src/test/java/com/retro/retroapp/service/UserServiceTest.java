package com.retro.retroapp.service;

import com.retro.retroapp.model.User;
import com.retro.retroapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepositoryMock;

    private UserService userServiceUnderTest;

    @Before
    public void setUp() {
        userServiceUnderTest = new UserService(userRepositoryMock);
    }

    @Test
    public void loadUserByUsername_HappyPath_ShouldReturn1User() {
        //given
        User user = new User();
        user.setUsername("shazin");
        user.setPassword("sha908");
        user.setRole("USER");
        //and
        when(userRepositoryMock.findByUsername("shazin")).thenReturn(java.util.Optional.of(user));

        //when
        UserDetails shazin = userServiceUnderTest.loadUserByUsername("shazin");

        //then
        assertThat(shazin.getUsername()).isEqualTo("shazin");
        verify(userRepositoryMock, times(1)).findByUsername("shazin");
    }

    @Test
    public void loadUserByUsername_UserNotFound_ShouldThrowUsernameNotFoundException() {
        //given
        when(userRepositoryMock.findByUsername("shazin")).thenReturn(Optional.empty());
        //when called then throws
        assertThatThrownBy(() -> userServiceUnderTest.loadUserByUsername("shazin"))
                .isInstanceOf(UsernameNotFoundException.class);

        verify(userRepositoryMock, times(1)).findByUsername("shazin");
    }

}
