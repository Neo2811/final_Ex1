package am.hiteck.controller;

import am.hiteck.model.User;
import am.hiteck.model.dto.request.UserRequestDto;
import am.hiteck.service.UserService;
import am.hiteck.util.exceptions.DuplicateException;
import am.hiteck.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<User> getById(@RequestParam int id) throws NotFoundException {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

//    @GetMapping("/salary")
//    public ResponseEntity<Double> getSalaryByMonth(Authentication authentication, @RequestParam int month) {
//        User user = userService.getByUsername(authentication.getName());
//        double result = userService.getSalaryByMonth(user.getId(),month);
//
//        return ResponseEntity.ok(result);
//    }
//
//    @GetMapping("/salary2")
//    public ResponseEntity<Double> getSalaryByQuarter(Authentication authentication, @RequestParam int month) {
//        User user = userService.getByUsername(authentication.getName());
//        int x = 0;
//        if (month == 1) x = 1;
//        if (month == 2) x = 4;
//        if (month == 3) x = 7;
//        if (month == 4) x = 10;
//        double result = userService.getSalaryByQuarter(user.getId(), x, x+2);
//
//        return ResponseEntity.ok(result);
//    }


    @GetMapping("/salary")
    public ResponseEntity<Double> getSalaryByMonth(
            Authentication authentication,
            @RequestParam @Min(1) @Max(12) int month) {
        try {
            User user = userService.getByUsername(authentication.getName());
            double result = userService.getSalaryByMonth(user.getId(), month);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/salary2")
    public ResponseEntity<Double> getSalaryByQuarter(Authentication authentication, @RequestParam @Min(1) @Max(4) int month) {
        try {
            User user = userService.getByUsername(authentication.getName());

            int x = 0;
            if (month == 1) x = 1;
            else if(month == 2) x = 4;
            else if(month == 3) x = 7;
            else x = 10;

            double result = userService.getSalaryByQuarter(user.getId(), x, x+2);

            return ResponseEntity.ok(result);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getOnlyActiveUsers() {
        List<User> list = userService.getOnlyActiveUsers();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAuthority('HR') OR hasAuthority('PM')")
    @GetMapping("/users/hr-pm")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getAll();
        return ResponseEntity.ok(list);
    }


    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid UserRequestDto requestDto) throws DuplicateException {

        userService.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROOT_ADMIN')")
    @PatchMapping("/change")
    public ResponseEntity<Void> change(@RequestParam int id) {
        userService.change(id);
        return ResponseEntity.ok().build();
    }

}
