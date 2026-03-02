
const authBtn = document.getElementById("authBtn");
const regBtn = document.getElementById("regBtn");
authBtn.addEventListener("click", AutToAPI);
regBtn.addEventListener("click", RegTo);




const Customer = {
    login: "",
    password: "",
    email: "" 
};

    async function AutToAPI() {
    Customer.login = document.getElementById("loginInput").value;
    Customer.password = document.getElementById("passInput").value;

    console.log("Attempting login for:", Customer.login);
    
    await fetchGlovoToApiAuth();
}
    async function fetchGlovoToApiAuth() {
    const bodyreq = JSON.stringify({
    login: Customer.login,
    password: Customer.password
});

    const request = {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: bodyreq
};

    try {
    const response = await fetch(`http://localhost:8080/api/v1/glovo/login`, request);
    const datacheck = await response.text();

    if (response.status !== 200) {
    throw new Error(datacheck);
}

    alert("Success: " + datacheck); 
    console.log("Successfully authorized");
} catch (error) {
    console.error("Error during Authorization:", error.message);
    alert("Auth failed: " + error.message);
}
}

    function RegTo() {
    window.location.href = "Registration.html";
}