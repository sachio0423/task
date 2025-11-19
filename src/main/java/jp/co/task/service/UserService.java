package jp.co.task.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.task.model.User;
import jp.co.task.repository.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	//private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public void registerUser(User user) {
		
		// ① 同じ名前のユーザーが存在するか確認
		Optional<User> existingUser = userDao.findByName(user.getName());
		if (existingUser.isPresent()) {
			// ② すでに存在していたらエラーを投げる
			throw new IllegalArgumentException("このユーザー名はすでに使われています");
		}
		
		//String hashedPassword = passwordEncoder.encode(user.getPassword());
		//user.setPassword(hashedPassword);
		userDao.save(user);
	}
	
	public Optional<User> login(String name, String rawPassword) {
		Optional<User> userOpt = userDao.findByName(name);
		//if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
		if (userOpt.isPresent() && userOpt.get().getPassword().equals(rawPassword)) {
			return userOpt;
		}
		return Optional.empty();
	}
}