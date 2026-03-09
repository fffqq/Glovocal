const Customer = {
    login: "",
    password: "",
    email: ""
};

async function fetchGlovoToApiReg() {
    try {
        const response = await fetch("http://localhost:8080/api/v1/glovo", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                login: Customer.login,
                password: Customer.password,
                email: Customer.email
            })
        });

        if (response.ok) {
            console.log("Успешно сохранено в БД");
            // Вот здесь ручной переход, если он тебе нужен:
            window.location.href = "Reg-Autorize.html";
        } else {
            alert("Ошибка при сохранении");
        }
    } catch (error) {
        console.error("Fetch error:", error);
    }
}