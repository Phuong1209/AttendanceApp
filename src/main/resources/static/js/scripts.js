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

document.getElementById('downloadCsvButton').addEventListener('click', function() {
    // Gửi yêu cầu GET tới endpoint mà trả về file CSV
    fetch('/summary/summaryProjectByDepartment')
        .then(response => response.blob())  // Lấy file CSV dưới dạng blob
        .then(blob => {
            // Tạo URL cho file CSV
            const link = document.createElement('a');
            link.href = URL.createObjectURL(blob);
            link.download = 'summaryProjectByDepartment.csv'; // Tên file CSV khi tải xuống
            link.click(); // Mô phỏng hành động nhấn vào liên kết để tải file
        })
        .catch(error => console.error('Error downloading CSV:', error)); // Xử lý lỗi nếu có
});



