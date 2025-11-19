// 年、月、日を動的に生成
document.addEventListener("DOMContentLoaded", function() {
	let yearSelect = document.getElementById("year");
	let monthSelect = document.getElementById("month");
	let daySelect = document.getElementById("day");

	// 現在の年を取得
	let currentYear = new Date().getFullYear();

	// 年（現在の年から+10年分を追加）
	for (let i = currentYear; i <= currentYear + 10; i++) {
		let option = document.createElement("option");
		option.value = i;
		option.textContent = i;
		yearSelect.appendChild(option);
	}

	// 月（1〜12月）
	for (let i = 1; i <= 12; i++) {
		let option = document.createElement("option");
		option.value = i.toString().padStart(2, "0");
		option.textContent = i;
		monthSelect.appendChild(option);
	}

	// 日（初期値は1〜31）
	function updateDays() {
		let year = parseInt(yearSelect.value);
		let month = parseInt(monthSelect.value);
		let daysInMonth = new Date(year, month, 0).getDate(); // 月末の日数を取得

		// 現在の日付をリセット
		daySelect.innerHTML = "";

		for (let i = 1; i <= daysInMonth; i++) {
			let option = document.createElement("option");
			option.value = i.toString().padStart(2, "0");
			option.textContent = i;
			daySelect.appendChild(option);
		}
	}

	// 月・年が変更されたら日数を更新
	yearSelect.addEventListener("change", updateDays);
	monthSelect.addEventListener("change", updateDays);

	// 初回ロード時に日付を設定
	yearSelect.value = currentYear;
	monthSelect.value = "01";
	updateDays();
});

$(document).ready(function() {
    console.log("task.js が読み込まれました！"); // ✅ デバッグ用

    $(".completion").click(function() {
        console.log("完了ボタンがクリックされました！"); // ✅ デバッグ用
        // 🔹 チェックされているタスクを取得
        let checkedTasks = $("input#taskCheckBox:checked");

        // 🔹 もしチェックが一つも入っていなかったら、アラートを表示して処理を中断
        if (checkedTasks.length === 0) {
            alert("チェックが入っていません。処理を実行できません。");
            return; // ❌ `submit()` を実行しない
        }

        // 🔹 確認ダイアログ
        if (!confirm("削除しますがよろしいですか？")) {
            return; // ❌ `submit()` を実行しない
        }

        // 🔹 チェックが入っていればフォームを送信
        $("#taskForm").submit();
    });
});



