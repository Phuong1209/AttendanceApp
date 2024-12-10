//Sidebar display
document.addEventListener('DOMContentLoaded', () => {
    // Select all <a> tags in the sidebar
    const links = document.querySelectorAll('.sidebar ul li > a');
    const currentPath = window.location.pathname; // Get current URL path

    // Highlight the active link based on the current URL
    links.forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active'); // Add active class if path matches
        }

        // Add click event listeners for SPA-like behavior
        link.addEventListener('click', (event) => {
            // Remove 'active' class from all links
            links.forEach(item => item.classList.remove('active'));

            // Add 'active' class to the clicked link
            event.currentTarget.classList.add('active');
        });
    });
});

const selectBtn = document.querySelector(".select-btn"),
    items = document.querySelectorAll(".item");

window.onload = () => {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    // Update the UI for the initial state of checked items
    checkboxes.forEach(checkbox => {
        const item = checkbox.closest(".item");
        if (checkbox.checked && item) {
            item.classList.add("checked");
        }
    });

    // Update the button text to reflect the initially selected items
    updateSelectedText();
};

// Function to update the displayed selected text
function updateSelectedText() {
    const checkedItems = document.querySelectorAll(".checked");
    const btnText = document.querySelector(".btn-text");

    if (checkedItems.length > 0) {
        const selectedTexts = Array.from(checkedItems).map(item =>
            item.querySelector(".item-text").innerText.trim()
        );

        // Join the selected item texts with commas and display
        btnText.innerText = selectedTexts.join("、 ");
        btnText.classList.add("active");
    } else {
        btnText.innerText = "選択してください。";
        btnText.classList.remove("active");
    }
}

//multiple select
selectBtn.addEventListener("click", () => {
    selectBtn.classList.toggle("open");
})

items.forEach(item => {
    item.addEventListener("click", () => {
        const checkbox = item.querySelector('input[type="checkbox"]');
        checkbox.checked = !checkbox.checked;
        item.classList.toggle("checked", checkbox.checked); // Update class based on new state

        updateSelectedText();
    });
});

//ThuPhuong
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

