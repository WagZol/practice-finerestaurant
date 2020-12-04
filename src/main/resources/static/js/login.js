(function () {
    const userForm = document.querySelector("form#login-form");
    handleLoginFormSubmit(userForm);

})();

function handleLoginFormSubmit(formElement){
    formElement.addEventListener("submit", (event) => {
        event.preventDefault();

        validateUserForm(formElement);
    })
}

async function validateUserForm(formElement) {
    const formData = new URLSearchParams(new FormData(formElement))

    const rawResponse = await fetch('login', {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });
    if (rawResponse.status == 200) {
        submitLogin(formData);
    }

    const updatedForm = await rawResponse.text();
    formElement.innerHTML = updatedForm;
}


async function submitLogin(formData) {
    const rawResponse=await fetch('login/process', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });
    console.log(rawResponse.status);
    if (rawResponse.status==200){
        window.location.href = rawResponse.url
    }

};

async function submitRegister(formData) {
    const rawResponse=await fetch('login/process', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });
    console.log(rawResponse.status);
    if (rawResponse.status==200){
        window.location.href = rawResponse.url
    }

};
