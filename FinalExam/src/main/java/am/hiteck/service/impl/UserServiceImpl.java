package am.hiteck.service.impl;

import am.hiteck.model.User;
import am.hiteck.model.dto.request.UserRequestDto;
import am.hiteck.repository.UserRepository;
import am.hiteck.service.UserService;
import am.hiteck.util.ErrorMessage;
import am.hiteck.util.exceptions.DuplicateException;
import am.hiteck.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getById(int id) throws NotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
             throw new NotFoundException("User not found for id " + id);
        }
        return user;
    }


    @Override
    public double getSalaryByMonth(int id,int month) {
       int hourInMonth = userRepository.getHours(id,month);
        System.out.println(hourInMonth);
       int salary = userRepository.getSalary(id);
        System.out.println(salary);
       double oneHourSalary = salary/21/8;
        System.out.println(oneHourSalary);
       double result = hourInMonth * oneHourSalary;
        System.out.println(result);
        return result;
    }

    @Override
    public double getSalaryByQuarter(int id,int month, int month2) {
        int hourInMonth = userRepository.getHoursBy3month(id,month,month2);
        int salary = userRepository.getSalary(id);
        double oneHourSalary = salary/21/8;
        double result = hourInMonth * oneHourSalary;

        return result;
    }



    @Override
    public List<User> getAll() {
        List<User> list = userRepository.getAllBy();
        return list;
    }

    @Override
    public List<User> getOnlyActiveUsers() {
        return userRepository.getOnlyActiveUsers();
    }

    @Override
    public User getByUsername(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public void create(UserRequestDto requestDto) throws DuplicateException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateException(ErrorMessage.DUPLICATE_EMAIL);
        }
        User user = convertToUser(requestDto,new User());
        userRepository.save(user);
    }


    @Override
    public void change(int id) {
        userRepository.change(id);
    }

    private User convertToUser(UserRequestDto requestDto,User user) {
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setStatus(requestDto.getStatus());
        user.setRole(requestDto.getRole());
        user.setSalary(requestDto.getSalary());

        return user;
    }
}
