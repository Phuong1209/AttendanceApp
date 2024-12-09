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