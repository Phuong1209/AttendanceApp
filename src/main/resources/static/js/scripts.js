const selectBtn = document.querySelector(".select-btn"),
    items = document.querySelectorAll(".item");

selectBtn.addEventListener("click", () => {
    selectBtn.classList.toggle("open");
})

items.forEach(item => {
    item.addEventListener("click", () => {
        item.classList.toggle("checked");

        const checkbox = item.querySelector('input[type="checkbox"]');
        checkbox.checked = item.classList.contains("checked");

        let checked = document.querySelectorAll(".checked"),
            btnText = document.querySelector(".btn-text");

        if (checked && checked.length > 0) {
            // Collect the text content of all selected items
            let selectedTexts = Array.from(checked).map(selectedItem =>
                selectedItem.querySelector(".item-text").innerText.trim()
            );

            // Join the selected item texts with commas and display
            btnText.innerText = selectedTexts.join("、 ");
            btnText.classList.add("active");
        } else {
            btnText.innerText = "作業内容を選択";
            btnText.classList.remove("active");
        }
    });
});