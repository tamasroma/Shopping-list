package hu.romat.shopinglist.service;

import hu.romat.shopinglist.config.UserDetailServiceImpl;
import hu.romat.shopinglist.entity.User;
import hu.romat.shopinglist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User fingUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    };

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = fingUserByEmail(userName);
        if (user == null){
            throw new UsernameNotFoundException(userName);
        }
        return new UserDetailServiceImpl(user);
    }

}
