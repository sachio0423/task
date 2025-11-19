package jp.co.task.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jp.co.task.service.TaskService;

@Controller
public class TaskController_list {
	
	@Autowired
	TaskService taskService;
	
	@RequestMapping("/task/list")
	public String searchTask(Model model,HttpSession session) {
		Integer userId = (Integer) session.getAttribute("user_id");
		if (userId == null) {
	        return "redirect:/login";
	    }
		
		List <Map<String,Object>> list = taskService.searchTask_now(userId);
		model.addAttribute("task_list",list);
		
		List <Map<String,Object>> stayList = taskService.searchTask_stay(userId);
		model.addAttribute("stay_list",stayList);
		String res = "task/task_list";
		return res;
	}
	
	@PostMapping("/task/delete")
	public String deleteTask(@RequestParam("check[]") String[] task_id,
			RedirectAttributes attr ) {
		int result = taskService.deleteTask(task_id);
		attr.addFlashAttribute("deleteNum",result + "件削除しました");
		return "redirect:/task/list";
	}
	
}
