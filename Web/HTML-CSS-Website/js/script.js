document.addEventListener("DOMContentLoaded", () => {
    // Rule 1: Check if passwords match
    const password = document.getElementById("password");
    const confirm_password = document.getElementById("confirm_password");
    confirm_password.addEventListener("input" , () => { 
        confirm_password.setCustomValidity(
            confirm_password.value === password.value ? "" : "Passwords don't match."
        );  
    })

    // Rule 2: Restrict minimum age to 18
    const birthDateElement = document.getElementById("birthdate");
    birthDateElement.addEventListener("input", () => {
        const today = new Date();
        const birthDate = new Date(birthDateElement.value);
        const age = today.getFullYear() - birthDate.getFullYear();
        const monthDifference = today.getMonth() - birthDate.getMonth(); 
        if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        birthDateElement.setCustomValidity(
            age >= 18 ? "" : "You must be at least 18 years old to register."
        )   
    });

    // Rule 3: Username must start with a letter,be at least 5 characters and contains only letters and numbers
    const username = document.getElementById("username");
    username.addEventListener("input", () =>{  
        const pattern = /^[a-zA-Z][a-zA-Z0-9]{4,}$/;
        username.setCustomValidity(
            pattern.test(username.value) ? "" : "Username must start with a letter,be at least 5 characters and contain only number and letters."
        );
    })
}
);