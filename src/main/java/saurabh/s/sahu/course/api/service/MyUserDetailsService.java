package saurabh.s.sahu.course.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import saurabh.s.sahu.course.api.model.MyUserDetails;
import saurabh.s.sahu.course.api.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new MyUserDetails(username); // username can be passed anything
        return userRepository.findByUserName(username)
                .map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}