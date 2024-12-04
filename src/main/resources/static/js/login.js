
//Handling JWT Token in Thymeleaf
async function login(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        const response = await fetch(form.action, {
            method: 'POST',
            body: new URLSearchParams(formData)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('jwtToken', data.token); // Store token
            window.location.href = '/dashboard'; // Redirect to dashboard
        } else {
            alert('Login failed!');
        }
    }

    document.querySelector('form').addEventListener('submit', login);


//Protect Thymeleaf Pages with JWT
    const token = localStorage.getItem('jwtToken');
    fetch('/secured-endpoint', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Unauthorized');
        }
    }).then(data => {
        console.log(data);
    }).catch(error => {
        console.error(error);
    });