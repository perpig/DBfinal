document.addEventListener('DOMContentLoaded', function () {
    loadExamSchedules();
});

function loadExamSchedules() {
    fetch('/teacherCourses')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('examTableBody');
            tbody.innerHTML = '';

            data.forEach(course => {
                const row = tbody.insertRow();

                // 添加课程基本信息
                row.insertCell().textContent = course.courseId;
                row.insertCell().textContent = course.courseName;
                row.insertCell().textContent = course.credits;
                row.insertCell().textContent = course.classTime;
                row.insertCell().textContent = course.classroom;
                row.insertCell().textContent = course.capacity;

                let timeInput = null;
                let locationInput = null;

                // 考试时间单元格
                const examTimeCell = row.insertCell();
                if (course.examTime) {
                    examTimeCell.textContent = course.examTime;
                } else {
                    timeInput = document.createElement('input');
                    timeInput.type = 'text';
                    timeInput.className = 'exam-input';
                    timeInput.placeholder = '选择考试时间';
                    examTimeCell.appendChild(timeInput);

                    // 初始化日期时间选择器
                    flatpickr(timeInput, {
                        enableTime: true,
                        dateFormat: "Y-m-d H:i",
                        locale: "zh",
                        minDate: "today",
                        defaultHour: 9,
                        onChange: function (selectedDates, dateStr) {
                            // 自动添加考试时长（2小时）
                            const startTime = selectedDates[0];
                            const endTime = new Date(startTime.getTime() + 2 * 60 * 60 * 1000);
                            timeInput.value = `${dateStr}-${endTime.getHours().toString().padStart(2, '0')}:${endTime.getMinutes().toString().padStart(2, '0')}`;
                        }
                    });
                }

                // 考试地点单元格
                const examPlaceCell = row.insertCell();
                if (course.examPlace) {
                    examPlaceCell.textContent = course.examPlace;
                } else {
                    locationInput = document.createElement('input');
                    locationInput.type = 'text';
                    locationInput.className = 'exam-input';
                    locationInput.placeholder = '输入考试地点';
                    examPlaceCell.appendChild(locationInput);
                }

                // 操作按钮单元格
                const actionCell = row.insertCell();
                if (!course.examTime || !course.examPlace) {
                    const submitBtn = document.createElement('button');
                    submitBtn.textContent = '提交';
                    submitBtn.className = 'submit-btn';

                    // 使用闭包来保持对timeInput和locationInput的引用
                    const currentTimeInput = timeInput;
                    const currentLocationInput = locationInput;

                    submitBtn.onclick = () => {
                        if (currentTimeInput && currentLocationInput) {
                            submitExamInfo(course.scheduleId, currentTimeInput, currentLocationInput, submitBtn);
                        } else {
                            alert('请先填写考试时间和地点');
                        }
                    };

                    actionCell.appendChild(submitBtn);
                }
            });
        })
        .catch(error => {
            console.error('加载考试安排失败:', error);
            alert('加载考试安排失败，请重试');
        });
}

function submitExamInfo(scheduleId, timeInput, locationInput, submitBtn) {
    const examTime = timeInput.value;
    const examPlace = locationInput.value;

    if (!examTime || !examPlace) {
        alert('请填写完整的考试时间和地点');
        return;
    }

    // 验证时间格式
    const timePattern = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}-\d{2}:\d{2}$/;
    if (!timePattern.test(examTime)) {
        alert('考试时间格式不正确，请使用系统提供的时间选择器');
        return;
    }

    submitBtn.disabled = true;

    fetch('/examArrange', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            scheduleId: scheduleId,
            examTime: examTime,
            examPlace: examPlace
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('考试信息提交成功！');
                loadExamSchedules(); // 重新加载列表
            } else {
                alert(data.message || '提交失败，请重试');
                submitBtn.disabled = false;
            }
        })
        .catch(error => {
            console.error('提交考试信息失败:', error);
            alert('提交失败，请重试');
            submitBtn.disabled = false;
        });
} 