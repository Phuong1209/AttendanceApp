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
        btnText.innerText = "作業内容を選択";
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

// Function to export project CSV
function exportProjectCSV() {
    fetch('/project/exportCSV', {
        method: 'GET',
        headers: {
            // 'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Creating a Blob from the response data
            return response.blob();
        })
        .then(blob => {
            // Creating a link element to download the file
            const link = document.createElement('a');
            const url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'summaryByProject.csv';
            document.body.appendChild(link);
            link.click();
            // Clean up the link
            document.body.removeChild(link);
        })
        .catch(error => {
            console.error('There was an error with the fetch operation:', error);
        });
}

// Function to export department CSV
function exportDepartmentCSV() {
    fetch('/department/exportCSV', {
        method: 'GET',
        headers: {
            // 'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Creating a Blob from the response data
            return response.blob();
        })
        .then(blob => {
            // Creating a link element to download the file
            const link = document.createElement('a');
            const url = window.URL.createObjectURL(blob);
            link.href = url;
            link.download = 'summaryByDepartment.csv';
            document.body.appendChild(link);
            link.click();
            // Clean up the link
            document.body.removeChild(link);
        })
        .catch(error => {
            console.error('There was an error with the fetch operation:', error);
        });
}
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('downloadCsvButton').addEventListener('click', function() {
    // Gửi yêu cầu GET tới endpoint mà trả về file CSV
        fetch('/summary/summaryProjectByDepartment')
            .then(response => response.blob())  // Lấy file CSV dưới dạng blob
            .then(blob => {
             // Tạo URL cho file CSV
                const link = document.createElement('a');
                link.href = window.URL.createObjectURL(blob);
                link.download = 'summaryProjectByDepartment.csv'; // Tên file CSV khi tải xuống
                document.body.appendChild(link);
                link.click(); // Mô phỏng hành động nhấn vào liên kết để tải file
                document.body.removeChild(link);
                })
                .catch(error => console.error('Error downloading CSV:', error));
                });
                });
// Xử lý lỗi nếu có


//calculate workTime
/*
function calculateWorkTime() {
    const checkinTime = document.querySelector('input[th\\:field="*{checkinTime}"]').value;
    const checkoutTime = document.querySelector('input[th\\:field="*{checkoutTime}"]').value;
    const breakTime = parseFloat(document.querySelector('input[th\\:field="*{breakTime}"]').value || 0);

    if (!checkinTime || !checkoutTime || isNaN(breakTime)) {
        alert("すべてのフィールドを入力してください。");
        return;
    }

    const checkin = new Date(`1970-01-01T${checkinTime}`);
    const checkout = new Date(`1970-01-01T${checkoutTime}`);
    const totalMinutes = (checkout - checkin) / (1000 * 60) - (breakTime * 60);
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
function togglePasswordVisibility() {
        const passwordField = document.getElementById("password");
        const toggleIcon = document.getElementById("toggleIcon");
        if (passwordField.type === "password") {
            passwordField.type = "text";
            toggleIcon.classList.remove("fa-eye");
            toggleIcon.classList.add("fa-eye-slash");
        } else {
            passwordField.type = "password";
            toggleIcon.classList.remove("fa-eye-slash");
            toggleIcon.classList.add("fa-eye");
        }
    }
    if (totalMinutes < 0) {
        alert("Please confirm your worktime");
        return;
    }

    const totalWorkTime = totalMinutes / 60;
    const workTime = Math.min(totalWorkTime, 8);
    const overTime = Math.max(totalWorkTime - 8, 0);

    document.getElementById("calculatedWorkTime").value = workTime.toFixed(2);
    document.getElementById("calculatedOverTime").value = overTime.toFixed(2);
    }
*/
function togglePasswordVisibility() {
  var x = document.getElementById("password");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}
