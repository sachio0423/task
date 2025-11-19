package jp.co.task.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jp.co.task.model.TaskForm;
import jp.co.task.service.TaskService;

@Controller
public class TaskController {
	@Autowired
	// セッションオブジェクト
	HttpSession session;
	
	@Autowired
	TaskService taskService;
	
	//試し--------------
	int tt=0;
	
	@GetMapping("/task/new")
	public String getNewForm(@RequestParam(name = "task_id", required = false) Integer task_id,
			Model model) {
		TaskForm taskForm;
		if (task_id != null && task_id > 0) {
	        taskForm = taskService.editTask(task_id);
	    } else {
	        taskForm = new TaskForm();
	        taskForm.setTask_id(null);  // ✅ 明示的に null をセット
	    }
		model.addAttribute("taskForm", taskForm);
		 //試し-----------------------------------
		System.out.println(taskForm.getTask_id());
		return "task/task_new";
	}
	
	@PostMapping("/task/new")
	public String postNewForm(
			@Validated @ModelAttribute TaskForm taskForm,
			BindingResult bindingResult, Model model, RedirectAttributes attr) {
		
		Integer taskId = taskForm.getTask_id();
	    System.out.println("Received task_id: " + taskId);
	    
		if (taskId != null) {
			taskForm.setTask_id(taskId);
		}
		if (taskForm.getTask_id() == null) {
	        System.out.println("task_id が null です！");
	    } else {
	        System.out.println("受け取った task_id: " + taskForm.getTask_id());
	    }
		
		if (bindingResult.hasErrors()) {
			//入力画面に遷移
			return "task/task_new";
		}
		
		

		//入力されたタスクを変数に格納
		String titel = taskForm.getTitle();
		Integer userId = (Integer) session.getAttribute("user_id");
		
		//ステータスをゲット
		String status = taskForm.getStatus();
		int statusNum = 0;
		if (status.equals("進行中")) {
			statusNum = 1;
		} else {
			statusNum = 2;
		}
		
		//入力された日付をローカルデータに変換
		int year = taskForm.getYear();
		int month = taskForm.getMonth();
		int day = taskForm.getDay();
		taskForm.setInputDay(year, month, day);
		LocalDate today = LocalDate.now();
		
		//inputDay変数に情報を格納
		LocalDate inputDay = taskForm.getInputDay();
		
		//過去の日付が入っていないかチェック
		boolean date = false;
		if (inputDay == null || inputDay.isBefore(today)) {
			date = true;
		} else {
			date = false;
		}
		if (date) {
			String message = "過去の日付が入力されています";
			System.out.println("過去の日付が入力されています");
			model.addAttribute("message", message);
			return "task/task_new";
		}
		
		Date inputDayday = Date.valueOf(inputDay);
		taskForm.setSday(inputDayday);
		
		if (taskForm.getTask_id() == null) {
			taskService.registTask(titel, inputDayday, statusNum , userId);
		} else {
			taskService.updeteTask(taskForm);
			String res = "redirect:/task/list";
			return res;
		}
		String res = "redirect:/task/list";
		return res;
	}
	
	
}
