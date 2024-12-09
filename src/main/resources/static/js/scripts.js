/*!
* Start Bootstrap - Modern Business v5.0.6 (https://startbootstrap.com/template-overviews/modern-business)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-modern-business/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project
fetch ("http://localhost:8080/loginui", {
    method: "POST",
    headers: {
        // Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        username:"admin",
        password: "admin"
    })  // Add a proper body if needed for authentication
})
    .then(response => response.json())
    .then(data => {
        console.log(data)
        const token = data.result.token;
        localStorage.setItem("authToken", token);
        console.log("Data fetched successfully:", data);
        // window.location.href = '/login-success';
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



if (token) {
    fetch("http://localhost:8080/departmentui", {
        method: "GET",  // hoặc POST
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error("Error:", error);
        });
}

//màn user
if (token) {
    fetch("http://localhost:8080/userui", {
        method: "GET",  // hoặc POST
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
        })

}

//create user
if (token) {
    fetch("http://localhost:8080/userui/createuser", {
        method: "GET",  // hoặc POST
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
        })
}

if (token) {
    fetch("http://localhost:8080/userui/editUser/{id}", {
        method: "GET",  // hoặc POST
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
        })
}

