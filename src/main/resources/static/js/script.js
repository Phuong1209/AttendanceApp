fetch("http://localhost:8080/auth/token", {
    method: "POST",
    headers: {
        // "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        username: 'admin',
        password: 'admin'
    })  // Add a proper body if needed for authentication
})
    .then(response => response.json())
    .then(data => {
        console.log(data)
        const token = data.token;
        localStorage.setItem("authToken", token);
        console.log("Data fetched successfully:", data);
    })
    .catch(error => {
        console.error("Error fetching data:", error);
    });
const token = localStorage.getItem("authToken");
if (token) {
    console.log("Token retrieved:", token);
} else {
    console.log("No token found.");
}
const token = localStorage.getItem("authToken");
if (token) {
    // Display token in an element (e.g., a div with the ID 'tokenDisplay')
    document.getElementById('tokenDisplay').innerText = `Your token: ${token}`;
} else {
    document.getElementById('tokenDisplay').innerText = "No token found.";
}

