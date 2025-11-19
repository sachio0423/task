package jp.co.task.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.task.model.User;
import jp.co.task.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	User user = new User();
	
	//ユーザー登録画面を表示する
	@GetMapping("/signup")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "task/signup";
	}
	
	@PostMapping("/signup")
	public String signup(User user, Model model) {
		try {
			userService.registerUser(user);
			// 登録成功したらログインページにリダイレクト
			return "redirect:/login";
		} catch (IllegalArgumentException e) {
			// エラーがあったら、エラーメッセージを表示してもう一度登録画面へ
			model.addAttribute("error", e.getMessage());
			model.addAttribute("user", user);
			return "task/signup";
		}
	}
	
	//ログイン画面を表示する
	@GetMapping("/login")
	public String showLoginForm() {
		return "task/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("name") String name,
	                    @RequestParam("password") String password,
	                    Model model,
	                    HttpSession session) {

	    Optional<User> loginUser = userService.login(name, password);
	    if (loginUser.isPresent()) {
	        session.setAttribute("user_id", loginUser.get().getUserId()); // ← これでuser_id保存
	        return "redirect:/task/list";
	    } else {
	        model.addAttribute("error", "ユーザー名またはパスワードが誤っています");
	        return "task/login";
	    }
	}
}
