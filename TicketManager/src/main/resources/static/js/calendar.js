document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');

    // 데이터 한 번만 가져오기
    fetch('/calendar/user/performances')
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
                eventLimit: true, // 일정이 많을 경우 'more' 버튼 표시
                editable: false // 사용자가 직접 일정 수정하지 못하도록 설정
            });

            calendar.render();
        })
        .catch(error => console.error("Error loading events:", error));
});
