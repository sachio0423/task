package jp.co.task.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskForm {
	
	private Integer task_id;
	
	@NotBlank(message = "タスクが未入力です。")
	private String title;

	@NotNull(message = "年を選択してください")
	private Integer year;

	@NotNull(message = "月を選択してください")
	private Integer month;

	@NotNull(message = "日を選択してください")
	private Integer day;
	
	private LocalDate inputDay;

	private String status;
	
	private Date sday;
	
	//タスクID
		public Date getSday() {
			return sday;
		}

		public void setSday(Date sday) {
			this.sday = sday;
		}
	
	
	//タスクID
	public Integer getTask_id() {
		return task_id;
	}

	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}

	
	//タイトル---------------------------------------------------------------------------------------------------
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	
	//期限--------------------------------------------------------------------------------------------------------
	  public void setInputDay(int year, int momth, int day) {
	        this.inputDay = LocalDate.of(year, month, day);
	    }
	  public LocalDate getInputDay() {
		  return inputDay;
	  }
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	//-----------------------------------------------------------------------------------------------------------

	//ステータス---------------------------------------------------------------------------------------------------
		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatus() {
			return status;
		}


}
