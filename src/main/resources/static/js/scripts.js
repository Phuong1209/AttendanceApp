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

// Function to export project CSV
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
    // Chuyển hướng trình duyệt đến endpoint tải file CSV
    window.location.href = '/summary/summaryProjectByDepartment';
});





