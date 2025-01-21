$(document).ready(function() {
    const performances = window.performances; // Thymeleaf에서 전달된 데이터 사용
    console.log(performances);

    // FullCalendar 초기화
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month'
        },
        events: performances.map(performance => ({
            title: performance.name,          // 공연 제목
            start: performance.startDate,     // 공연 시작일
            end: performance.endDate,         // 공연 종료일
            description: performance.location // 공연 장소
        })),
        eventClick: function(event) {
            alert('공연: ' + event.title + '\n장소: ' + event.description);
        }
    });
});