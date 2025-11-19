package jp.co.task.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.task.model.TaskForm;
import jp.co.task.repository.TaskDao;

@Service
public class TaskService {
	@Autowired
	TaskDao taskDao;
	
	public int registTask(String title, Date inputDay, int status ,Integer userId) {
		int result = taskDao.registTask(title, inputDay, status , userId);
		return result;
	}
	
	public int deleteTask(String[] task_id) {
		int result = taskDao.deleteTask(task_id);
		return result;
	}
	
	public TaskForm editTask(int search) {
		Map<String, Object> map = taskDao.editTask(search);
		TaskForm task = new TaskForm();
		
		//SQLでセレクトした情報をformのフォームのフィールドに代入していく
		//　タスクidを代入
		task.setTask_id((Integer) map.get("task_id"));
		
		//タイトルを代入ーーーーーーーー
		task.setTitle(String.valueOf(map.get("title")));
		
		//
		Date sqlDate = (Date) map.get("deadline");
		///年
		SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
		String datey = ysdf.format(sqlDate);
		int y = Integer.parseInt(datey);
		task.setYear(y);
		
		//月
		SimpleDateFormat msdf = new SimpleDateFormat("MM");
		String datem = msdf.format(sqlDate);
		int m = Integer.parseInt(datem);
		task.setMonth(m);
		
		//日
		SimpleDateFormat dsdf = new SimpleDateFormat("dd");
		String dated = dsdf.format(sqlDate);
		int d = Integer.parseInt(dated);
		task.setDay(d);
		
		int sqlStatus = (int) map.get("status");
		if (sqlStatus == 1) {
			task.setStatus("進行中");
		} else {
			task.setStatus("保留");
		}
		return task;
	}
	
	public int updeteTask(TaskForm task) {
		int result = taskDao.updateTask(task);
		return result;
	}
	
	public List<Map<String, Object>> searchTask_now(Integer userId) {
		List<Map<String, Object>> list = taskDao.searchTask(userId,1);
		LocalDate today = LocalDate.now(); // ✅ 今日の日付を取得
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d"); // ✅ 「1/10」形式のフォーマット
		
		List<Map<String, Object>> updatedList = new ArrayList<>();
		
		for (Map<String, Object> task : list) {
			
			// 🔹 `status` が `1` のタスクのみ処理
			
			Object dateObj = task.get("deadline");
			
			if (dateObj instanceof java.sql.Date) {
				LocalDate deadline = ((java.sql.Date) dateObj).toLocalDate();
				String formattedDate = deadline.format(formatter);
				task.put("deadline", formattedDate);
				
				long daysLeft = ChronoUnit.DAYS.between(today, deadline);
				
				if (daysLeft < 0) {
					task.put("daysLeft", "期限切れ");
				} else if (daysLeft == 0) {
					task.put("daysLeft", "今日");
				} else {
					task.put("daysLeft", "あと " + daysLeft + " 日");
				}
			} else {
				task.put("deadline", "不明");
				task.put("daysLeft", "不明");
			}
			
			updatedList.add(task);
			
		}
		
		return updatedList;
	}
	
	public List<Map<String, Object>> searchTask_stay(Integer userId) {
		List<Map<String, Object>> list = taskDao.searchTask(userId,2);
		LocalDate today = LocalDate.now(); // ✅ 今日の日付を取得
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d"); // ✅ 「1/10」形式のフォーマット
		
		List<Map<String, Object>> updatedList = new ArrayList<>();
		
		for (Map<String, Object> task : list) {
			Object dateObj = task.get("deadline");
			
			if (dateObj instanceof java.sql.Date) {
				LocalDate deadline = ((java.sql.Date) dateObj).toLocalDate();
				String formattedDate = deadline.format(formatter);
				task.put("deadline", formattedDate);
				
				long daysLeft = ChronoUnit.DAYS.between(today, deadline);
				
				if (daysLeft < 0) {
					task.put("daysLeft", "期限切れ");
				} else if (daysLeft == 0) {
					task.put("daysLeft", "今日");
				} else {
					task.put("daysLeft", "あと " + daysLeft + " 日");
				}
			} else {
				task.put("deadline", "不明");
				task.put("daysLeft", "不明");
			}
			
			updatedList.add(task);
			
		}
		
		return updatedList;
	}
}