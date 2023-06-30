package com.example.ap2_ex3.repositories;

import android.app.Application;

import com.example.ap2_ex3.AppDB;
import com.example.ap2_ex3.daos.UserDao;
import com.example.ap2_ex3.entities.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UsersRepository {

    private AppDB appDB;

    private List<User> getAllUsers;
    private UserDao userDao;

    private Executor executor = Executors.newSingleThreadExecutor();

    public UsersRepository(Application application) {
        appDB = AppDB.getInstance(application);
        userDao = appDB.userDao();
         executor.execute(() -> getAllUsers = userDao.getAllUsers());
    }

    public List<User> getAllUsers() {
        return getAllUsers;
    }

    public User get(String username) {
        return userDao.get(username);
    }

    public void insert(final User user) {
        executor.execute(() -> userDao.insert(user));
    }

    public void insertList(final List<User> userList) {
        executor.execute(() -> userDao.insertList(userList));
    }


    public void delete(final User user) {
        executor.execute(() -> userDao.delete(user));
    }

    public void update(final User user) {
        executor.execute(() -> userDao.update(user));
    }

}
