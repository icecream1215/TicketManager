document.addEventListener("DOMContentLoaded", () => {
    // 추가 버튼 클릭 이벤트
    const buttons = document.querySelectorAll(".add-btn");

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            const performance = {
                id: button.dataset.id,
                name: button.dataset.name,
                location: button.dataset.location,
                startDate: button.dataset.startdate,
                endDate: button.dataset.enddate,
            };

            fetch("/search/performances/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(performance),
            })
                .then((response) => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error("공연 추가 실패");
                    }
                })
                .then((data) => alert(data))
                .catch((error) => alert("오류 발생: " + error.message));
        });
    });

    // 날짜 기본값 설정
    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, "0");
    const dd = String(today.getDate()).padStart(2, "0");

    const nextMonth = new Date(today);
    nextMonth.setMonth(nextMonth.getMonth() + 1);
    const nextYyyy = nextMonth.getFullYear();
    const nextMm = String(nextMonth.getMonth() + 1).padStart(2, "0");
    const nextDd = String(nextMonth.getDate()).padStart(2, "0");

    document.getElementById("startDate").value = `${yyyy}-${mm}-${dd}`;
    document.getElementById("endDate").value = `${nextYyyy}-${nextMm}-${nextDd}`;
});