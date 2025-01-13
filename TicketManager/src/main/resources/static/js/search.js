document.addEventListener("DOMContentLoaded", () => {
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
                .then((response) => response.text())
                .then((data) => alert(data))
                .catch((error) => alert("오류 발생: " + error));
        });
    });
});
