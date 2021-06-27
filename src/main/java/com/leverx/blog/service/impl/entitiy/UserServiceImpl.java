package com.leverx.blog.service.impl.entitiy;

import com.leverx.blog.dto.UserDto;
import com.leverx.blog.entity.User;
import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.mapper.UserMapper;
import com.leverx.blog.repository.UserRepository;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto findByEmail(String email) throws NotFoundEntityException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new NotFoundEntityException(String.format("No user with email %s", email));
        }
        return userMapper.mapToDto(fetchLazyRoles(user.get()));
    }

    @Override
    public int findIdByEmail(String email) {
        Optional<Integer> id = userRepository.findIdByEmail(email);
        if (id.isEmpty()) {
            throw new NotFoundEntityException(String.format("No user with email %s", email));
        }
        return id.get();
    }

    @Override
    public void save(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void setActivatedById(int id, boolean activated) {
        userRepository.setActivatedById(id, activated);
    }

    private User fetchLazyRoles(User user) {
        user.getRoles().size();
        return user;
    }
}
