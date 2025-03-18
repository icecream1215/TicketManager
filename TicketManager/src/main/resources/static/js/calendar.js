document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');

    // 데이터 한 번만 가져오기
    fetch('/performances/user')
        .then(response => response.json())
        .then(events => {
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay'
                },
                events: events,
                eventClick: function (info) {
                    showEventDetails(info.event);
                },
                eventLimit: true, // 일정이 많을 경우 'more' 버튼 표시
                editable: false // 사용자가 직접 일정 수정하지 못하도록 설정
            });

            calendar.render();
        })
        .catch(error => console.error("Error loading events:", error));
});

// 상세 정보 표시 함수
function showEventDetails(event) {
    var modal = document.getElementById('eventModal');
    document.getElementById('modalTitle').innerText = event.title;
     var selectedDate = event.start.toLocaleDateString('ko-KR', {
         year: 'numeric',
         month: '2-digit',
         day: '2-digit'
     }).replace(/\. /g, '-').replace('.', '');
    document.getElementById('modalDate').innerText = selectedDate;
    // 장소 정보가 있을 경우 표시
    document.getElementById('modalLocation').innerText = event.extendedProps.location || "장소 정보 없음";
    document.getElementById('modalPeriod').innerText = event.extendedProps.startDate + " ~ " + event.extendedProps.endDate;

    document.getElementById('deleteSelectedDateBtn').setAttribute("data-id", event.id);
    document.getElementById('deleteSelectedDateBtn').setAttribute("data-date", selectedDate);
    document.getElementById('deleteSelectedDateBtn').style.display = "block";

    modal.style.display = "block";
}

// 선택한 날짜 삭제
function deleteSelectedDate() {
    const performanceId = document.getElementById('deleteSelectedDateBtn').getAttribute("data-id");
    const selectedDate = document.getElementById('deleteSelectedDateBtn').getAttribute("data-date");

    fetch(`/performances/user/${performanceId}/${selectedDate}`, {
        method: 'DELETE'
    }).then(response => response.text())
      .then(data => {
          alert(data);
          closeModal();
          location.reload();
      })
      .catch(error => console.error("Error deleting selected date:", error));
}

// 모달 닫기
function closeModal() {
    document.getElementById('eventModal').style.display = "none";
}