import {createElementFromHTML} from '/js/moduls/utils.js';

const URLs={
    update:"/reservation/admin",
    allInstances: "/reservation/reservationItems",
    delete: "/reservation/admin?id=",
    instance: "/reservation/reservationItems?id=",
    create: "/reservation/admin"
};

(function () {
    const header = document.querySelector("header");
    const footer = document.querySelector("footer");
    const reservationList = document.querySelector(".reservation-list");
    const pageMain = document.querySelector("main");
    const reservationItemContext = document.querySelector(".reservation-item-context");
    const reservationModificationModal = document.querySelector("#reservation-modification-modal");

    handleMenuItemContextResize(header, footer, reservationItemContext, pageMain);
    handleDeleteListItem(reservationList, reservationModificationModal);
    handleAddListItem(reservationList, reservationModificationModal);
    handleUpdateListItem(reservationList, reservationModificationModal);
    convertDate(reservationList)
})();

function convertDate(listElement){
    const dateElements=listElement.querySelectorAll(".reservation-item-date span")
    let index;
    for (index = 0; index < dateElements.length; index++) {
        const dateString=dateElements[index].innerHTML;
        dateElements[index].innerHTML=dateString.replace("T", " ");
    }
    return listElement;
}


function handleMenuItemContextResize(header, footer, ItemContext, pageMain) {
    setReservationItemContextHeight(header, footer, ItemContext, pageMain);
    window.addEventListener('resize', () => {
        setReservationItemContextHeight(header, footer, ItemContext, pageMain);
    });
}

function setReservationItemContextHeight(header, footer, ItemContext, pageMain) {
    const headerHeight = header.offsetHeight;
    const footerHeight = footer.offsetHeight;
    const pageMainPaddingTop = pageMain.style.paddingTop;
    const pageMainPaddingBottom = pageMain.style.paddingBottom;
    const IteContextHeight = window.innerHeight - headerHeight - footerHeight - 20 - 20;
    ItemContext.style.height = `${IteContextHeight}px`;
}

async function refreshList(listElement){
    const rawResponse = await fetch(URLs.allInstances)

    const responseText = await rawResponse.text();
    if (rawResponse.status == 200) {
        let updatedListElement = createElementFromHTML(responseText)
        updatedListElement=convertDate(updatedListElement)
        listElement.innerHTML = updatedListElement.innerHTML;
        return;
    }
}

function handleDeleteListItem(listElement, modificationModal) {
    listElement.addEventListener("deleteFromList", async (event) => {
        const formElement = modificationModal.querySelector("form")
        const rawResponse = await fetch(`${URLs.delete}${event.detail.id}`, {
            method: 'DELETE'
        });

        const responseText = await rawResponse.text();
        if (rawResponse.status == 200) {
            refreshList(listElement)
            return;
        }
        formElement.innerHTML = responseText;
    })
}

async function initializeEdit(event, formElement){
    const rawResponse = await fetch(`${URLs.instance}${event.detail.id}`);
    const responseText = await rawResponse.text();
    if(rawResponse.status==200)
        formElement.innerHTML = responseText;
}

function handleUpdateListItem(listElement, modificationModal){
    const formElement=modificationModal.querySelector('form');
    listElement.addEventListener("modifyListItem", async (event) => {
        initializeEdit(event, formElement);
        $(modificationModal).modal("show");

        formElement.onsubmit=(event) => {
            event.preventDefault();
            modifyItem(modificationModal, formElement, listElement);
        }
    })
}

async function modifyItem(modificationModal, formElement, listElement) {
    const formData = new URLSearchParams(new FormData(formElement))

    const rawResponse = await fetch(URLs.update, {
        method: 'PUT',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });

    const responseText = await rawResponse.text();
    if (rawResponse.status == 200) {
        $(modificationModal).modal("hide");
        refreshList(listElement);
        return;
    }
    formElement.innerHTML = responseText;
}

function handleAddListItem(listElement, modificationModal) {
    const formElement=modificationModal.querySelector("form");
    listElement.addEventListener("addListItem", async (event) => {
        formElement.reset();
        $(modificationModal).modal("show");
        formElement.onsubmit=(event) => {
            event.preventDefault();
            createNewListItem(modificationModal, formElement, listElement);
        }
    })
}

async function createNewListItem(modificationModal, formElement, listElement) {
    const formData = new URLSearchParams(new FormData(formElement))

    const rawResponse = await fetch(URLs.create, {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });

    const responseText = await rawResponse.text();
    if (rawResponse.status == 200) {
        $(modificationModal).modal("hide");
        refreshList(listElement)
        return;
    }
    formElement.innerHTML = responseText;
}