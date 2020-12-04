import {createElementFromHTML} from '/js/moduls/utils.js';


(function () {
    const reservationFormElement=document.querySelector("#reservation-form");
    const reservationWrapper=document.querySelector(".reservation-wrapper")
    handleReservationFormSubmit(reservationWrapper);


})()

function handleReservationFormSubmit(formWrapper){
    const formElement=formWrapper.querySelector("form");
    // const formSubmitButton=formElement.querySelector('button');
    formWrapper.addEventListener("submit", (event) => {
        console.log("click")
        event.preventDefault();

        validateReservationForm(formElement);
    })
}

async function validateReservationForm(formElement) {
    const formData = new URLSearchParams(new FormData(formElement))

    const rawResponse = await fetch('/reservation', {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });

    const responseText = await rawResponse.text();
    console.log(responseText);
    const updatedFormElement = createElementFromHTML(responseText)
    formElement.innerHTML = updatedFormElement.innerHTML;

}
