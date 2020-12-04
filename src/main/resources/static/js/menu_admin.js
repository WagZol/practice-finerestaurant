import {createElementFromHTML} from '/js/moduls/utils.js';


const URLs={
    update:"/menu",
    refresh: "/menu/courses",
    delete: "/menu?id=",
    instance: "/menu/courses?id=",
    add: "/menu"
};

(function () {
    const header = document.querySelector("header");
    const footer = document.querySelector("footer");
    const menuList = document.querySelector(".menu-list");
    const pageMain = document.querySelector("main");
    const menuItemContext = document.querySelector(".menu-item-context");
    const courseModificationModal = document.querySelector("#course-modification-modal");

    handleMenuItemContextResize(header, footer, menuItemContext, pageMain);
    handleDeleteCourseItemFromList(menuList, courseModificationModal);
    handleAddListItem(menuList, courseModificationModal);
    handleUpdateListItem(menuList, courseModificationModal);

})();


function handleMenuItemContextResize(header, footer, menuItemContext, pageMain) {
    setMenuItemContextHeight(header, footer, menuItemContext, pageMain);
    window.addEventListener('resize', () => {
        setMenuItemContextHeight(header, footer, menuItemContext, pageMain);
    });
}

function setMenuItemContextHeight(header, footer, menuItemContext, pageMain) {
    const headerHeight = header.offsetHeight;
    const footerHeight = footer.offsetHeight;
    const pageMainPaddingTop = pageMain.style.paddingTop;
    const pageMainPaddingBottom = pageMain.style.paddingBottom;
    const menuIteContextHeight = window.innerHeight - headerHeight - footerHeight - 20 - 20;
    menuItemContext.style.height = `${menuIteContextHeight}px`;
}

async function refreshMenuList(menuListElement){
    const rawResponse = await fetch(`/menu/courses`)

    const responseText = await rawResponse.text();
    if (rawResponse.status == 200) {
        const updatedMenuListElement = createElementFromHTML(responseText)
        menuListElement.innerHTML = updatedMenuListElement.innerHTML;
        return;
    }
}

function handleDeleteCourseItemFromList(menuListElement, courseModificationModal) {
    menuListElement.addEventListener("deleteFromMenuList", async (event) => {
        const formElement = courseModificationModal.querySelector("form")
        const rawResponse = await fetch(`/menu?id=${event.detail.id}`, {
            method: 'DELETE'
        });

        const responseText = await rawResponse.text();
        if (rawResponse.status == 200) {
            refreshMenuList(menuListElement)
            return;
        }
        formElement.innerHTML = responseText;
    })
}

async function initializeCourseModify(event, formElement){
    const rawResponse = await fetch(`/menu/courses?id=${event.detail.id}`);
    const responseText = await rawResponse.text();
    if(rawResponse.status==200)
        formElement.innerHTML = responseText;
}

function handleUpdateListItem(menuListElement, courseModificationModal){
    const formElement=courseModificationModal.querySelector('form');
    menuListElement.addEventListener("modifyListItem", async (event) => {
        initializeCourseModify(event, formElement);
        $(courseModificationModal).modal("show");

        formElement.onsubmit=(event) => {
            event.preventDefault();
            modifyCourse(courseModificationModal, formElement, menuListElement);
        }
    })
}

async function modifyCourse(courseModificationModal, formElement, menuListElement) {
    const formData = new URLSearchParams(new FormData(formElement))

    const rawResponse = await fetch(`/menu`, {
        method: 'PUT',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });

    const responseText = await rawResponse.text();
    if (rawResponse.status == 200) {
        $(courseModificationModal).modal("hide");
        refreshMenuList(menuListElement);
        return;
    }
    formElement.innerHTML = responseText;
}

function handleAddListItem(menuListElement, courseModificationModal) {
    const formElement=courseModificationModal.querySelector("form");
    menuListElement.addEventListener("addListItem", async (event) => {
        formElement.reset();
        $(courseModificationModal).modal("show");
        formElement.onsubmit=(event) => {
            event.preventDefault();
            createNewCourse(courseModificationModal, formElement, menuListElement);
        }
    })
}

async function createNewCourse(courseModificationModal, formElement, menuListElement) {
    const formData = new URLSearchParams(new FormData(formElement))

    const rawResponse = await fetch(`/menu`, {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });

    const responseText = await rawResponse.text();
    if (rawResponse.status == 200) {
        $(courseModificationModal).modal("hide");
        refreshMenuList(menuListElement)
        return;
    }
    formElement.innerHTML = responseText;
}