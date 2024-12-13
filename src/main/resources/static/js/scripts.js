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


