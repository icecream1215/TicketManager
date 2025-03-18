document.addEventListener("DOMContentLoaded", () => {
    let currentPage = 1;

    // 날짜 기본값 설정 (오늘 날짜 ~ 다음 달 같은 날짜)
    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, "0");
    const dd = String(today.getDate()).padStart(2, "0");

    const nextMonth = new Date(today);
    nextMonth.setMonth(nextMonth.getMonth() + 1);
    const nextYyyy = nextMonth.getFullYear();
    const nextMm = String(nextMonth.getMonth() + 1).padStart(2, "0");
    const nextDd = String(nextMonth.getDate()).padStart(2, "0");

    // startDate, endDate 요소가 존재하는지 확인 후 값 설정
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");

    if (startDateInput) startDateInput.value = `${yyyy}-${mm}-${dd}`;
    if (endDateInput) endDateInput.value = `${nextYyyy}-${nextMm}-${nextDd}`;

    // 검색 폼 이벤트 추가
    const searchForm = document.getElementById("searchForm");
    if (searchForm) {
        searchForm.addEventListener("submit", function(event) {
            event.preventDefault();
            currentPage = 1;
            fetchShows();
        });
    } else{
        console.error("searchForm을 찾을 수 없습니다");
    }

    function fetchShows() {
        const searchForm = document.getElementById("searchForm");
        if(!searchForm){
            return;
        }

        const formData = new FormData(searchForm);
        formData.append("page", currentPage);

        fetch("/search", {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            updateResults(data.performances);
            updatePagination(data.hasNextPage);
        })
        .catch(error => console.error("Error fetching data:", error));
    }

    function updateResults(performances) {
        const resultsContainer = document.getElementById("search-results");
        if(!resultsContainer){
            return;
        }
            resultsContainer.innerHTML = performances.map(performance => `
                <tr>
                    <td>${performance.name}</td>
                    <td>${performance.location}</td>
                    <td>${performance.startDate}~${performance.endDate}</td>
                    <td><input type="date" class="selected-date"
                                                   min="${performance.startDate}"
                                                   max="${performance.endDate}"
                                                   required>
                    </td>
                    <td>
                        <button class="btn btn-success add-btn"
                                data-id="${performance.id}"
                                data-name="${performance.name}"
                                data-location="${performance.location}"
                                data-startdate="${performance.startDate}"
                                data-enddate="${performance.endDate}">
                            <i class="fas fa-plus"></i> 추가
                        </button>
                    </td>
                </tr>
            `).join("");
        document.getElementById("pageIndicator").textContent = `페이지 ${currentPage}`;
    }

    function updatePagination(hasNextPage) {
        const prevPageBtn = document.getElementById("prevPage");
        const nextPageBtn = document.getElementById("nextPage");

        if (prevPageBtn) prevPageBtn.disabled = (currentPage === 1);
        if (nextPageBtn) nextPageBtn.disabled = !hasNextPage;
    }

    document.addEventListener("click", (event) => {
        if (event.target.classList.contains("add-btn")) {
            const button = event.target;
            const row = button.closest("tr");
            const selectedDateInput = row.querySelector(".selected-date");

            if (!selectedDateInput.value) {
                alert("날짜를 선택하세요.");
                return;
            }

            const performance = {
                id: button.dataset.id,
                name: button.dataset.name,
                location: button.dataset.location,
                startDate: button.dataset.startdate,
                endDate: button.dataset.enddate,
                selectedDate: selectedDateInput.value
           };

            fetch("/search/performances/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(performance),
            })
            .then(response => response.text())
            .then(data => alert(data))
            .catch(error => alert("오류 발생: " + error.message));
        }
    });

    const prevPageBtn = document.getElementById("prevPage");
    const nextPageBtn = document.getElementById("nextPage");

    if (prevPageBtn) {
        prevPageBtn.addEventListener("click", () => {
            if (currentPage > 1) {
                currentPage--;
                fetchShows();
            }
        });
    }

    if (nextPageBtn) {
        nextPageBtn.addEventListener("click", () => {
            currentPage++;
            fetchShows();
        });
    }
});
