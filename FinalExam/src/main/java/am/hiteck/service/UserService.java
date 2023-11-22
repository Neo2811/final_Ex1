package am.hiteck.service;

import am.hiteck.model.User;
import am.hiteck.model.dto.request.UserRequestDto;
import am.hiteck.util.exceptions.DuplicateException;
import am.hiteck.util.exceptions.NotFoundException;

import java.util.List;

public interface UserService {
    User getById(int id) throws NotFoundException;

    List<User> getAll();

    List<User> getOnlyActiveUsers();

    User getByUsername(String email);

    void create(UserRequestDto requestDto) throws DuplicateException;


    void change(int id);

    double getSalaryByMonth(int id,int month);
    double getSalaryByQuarter(int id,int month, int month2);
}
