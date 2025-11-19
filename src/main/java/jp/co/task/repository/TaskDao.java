package jp.co.task.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.task.model.TaskForm;

@Repository
public class TaskDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int registTask(String title, Date inputDay, int status,Integer userId) {
		
		String sql = "INSERT INTO task(title,deadline,status,user_id) VALUES(?,?,?,?)";
		Object[] param = { title, inputDay, status ,userId };
		int result = jdbcTemplate.update(sql, param);
		System.out.println("1件登録が完了しました");
		return result;
	}
	
	public List<Map<String, Object>> searchTask(Integer userId ,int status) {
		String sql = "SELECT *  FROM task WHERE user_id = ? AND status= ? AND deleteflg =1 ORDER BY deadline ASC";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId,status);
		return list;
	}
	
	public Map<String, Object> editTask(int search) {
		String sql = "SELECT * FROM task WHERE task_id = ?";
		Object[] param = { search };
		Map<String, Object> task = jdbcTemplate.queryForMap(sql, param);
		return task;
	}
	
	public int updateTask(TaskForm task) {
		int statusNum = 1; // デフォルト値
		if ("進行中".equals(task.getStatus())) {
			statusNum = 1;
		} else if ("保留".equals(task.getStatus())) {
			statusNum = 2;
		}
		System.out.println(task.getTask_id());
		String sql = "UPDATE task SET title=?,deadline=?,status=? WHERE task_id=?";
		Object[] param = { task.getTitle(), task.getSday(), statusNum, task.getTask_id() };
		int result = jdbcTemplate.update(sql, param);
		return result;
	}
	
	public int deleteTask(String[] task_id) {
		int result = 0;
		for (String id : task_id) {
			String sql = "UPDATE task SET deleteflg =0 WHERE task_id=?";
			Object[] param = { id };
			result += jdbcTemplate.update(sql, param);
		}
		return result;
	}
	
	
}
