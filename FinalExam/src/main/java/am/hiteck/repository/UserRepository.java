package am.hiteck.repository;

import am.hiteck.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findById(int id);
    List<User> getAllBy();
    User findByEmail(String email);
    boolean existsByEmail(String email);


    @Query(nativeQuery = true,value = "select  * from `user` where status = 'active'")
    List<User> getOnlyActiveUsers();


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update `user` set status = if(status = 'unverified','verified','unverified') where id = ?1")
    void change(int id);

    @Query(nativeQuery = true,value = "SELECT  SUM(`check`) FROM `check_limit` WHERE user_id = ?1 AND SUBSTRING(DATE, 6, 2) = ?2")
    int getHours(int id,int month);

    @Query(nativeQuery = true,value = "SELECT  SUM(`check`) FROM `check_limit` WHERE user_id = ?1 AND SUBSTRING(DATE, 6, 2) BETWEEN ?2 AND ?3")
    int getHoursBy3month(int id,int month,int month2);

    @Query(nativeQuery = true,value = "select salary from `user` where id = ?1")
    int getSalary(int id);

}
