import {createElementFromHTML} from '/js/moduls/utils.js';

(function () {
    const header = document.querySelector("header");
    const footer = document.querySelector("footer");
    const menuList = document.querySelector(".menu-list");
    const pageMain = document.querySelector("main");
    const shoppingItemContext = document.querySelector(".shopping-item-context");
    const menuItemContext = document.querySelector(".menu-item-context");
    const courseToSearch = document.querySelector("#courseInput");
    const couponFormElement= document.querySelector("#coupon-form");
    const couponSubmitButton= document.querySelector("#coupon-code-btn")

    /*scrollozashoz a scrollozni kivant elem wrapperenek meghatarozott meretunek kell lennie,*/
    handleMenuItemContextResize(header, footer, menuItemContext, pageMain);

    handleCourseToSearchChange(courseToSearch, menuList);
    handleAddToShoppingcart(menuList, shoppingItemContext);
    handleRemoveFromShoppingcart(menuList, shoppingItemContext);
    handleCouponFormSubmit(couponFormElement, couponSubmitButton, shoppingItemContext);
})();

function handleCourseToSearchChange(courseToSearch, menuList) {
    courseToSearch.addEventListener("input", async () => {
        const rawResponse = await fetch(`/menu/searchCourse?coursename=${courseToSearch.value}`);

        const updatedMenuList = await rawResponse.text();
        if (rawResponse.status == 200) {
            const updatedMenuListElement = createElementFromHTML(updatedMenuList);
            menuList.innerHTML = updatedMenuListElement.innerHTML;
        }
    })
}

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

function handleAddToShoppingcart(menuList, shoppingItemContext) {
    menuList.addEventListener("addToShoppingcart", async (event) => {
        const rawResponse = await fetch(`/menu/addToShoppingCart?itemname=${event.detail.itemName}&`+
            `itemprice=${event.detail.itemPrice}`);

        const updatedShoppingList = await rawResponse.text();
        if (rawResponse.status == 200) {
            const updatedShoppingListElement=createElementFromHTML(updatedShoppingList)
            handleNegativeCurrency(updatedShoppingListElement.innerHTML.toString(), "$");
            shoppingItemContext.innerHTML = updatedShoppingListElement.innerHTML;
        }
    })
}

function handleNegativeCurrency(text, currency){
    const corrigatedHTMlText=text.replace(`${currency}-`, `-${currency}`);
    console.log(corrigatedHTMlText);
    return corrigatedHTMlText;
}

function handleRemoveFromShoppingcart(menuList, shoppingItemContext) {
    menuList.addEventListener("removeFromShoppingcart", async (event) => {
        console.log("remove");
        const rawResponse = await fetch(`/menu/removeFromShoppingCart?index=${event.detail.index}&`+
            `itemprice=${event.detail.itemPrice}`);

        const updatedShoppingList = await rawResponse.text();
        if (rawResponse.status == 200) {
            const updatedShoppingListElement=createElementFromHTML(updatedShoppingList)
            shoppingItemContext.innerHTML = updatedShoppingListElement.innerHTML;
        }
    })
}

function handleCouponFormSubmit(formElement, formSubmitButton, shoppingItemContext){
    formSubmitButton.addEventListener("click", (event) => {
        event.preventDefault();
        formElement.querySelector("input").classList.remove("is-invalid")

        validateCouponCodeForm(formElement, shoppingItemContext);
    })
}

async function validateCouponCodeForm(formElement ,shoppingItemContext) {
    const formData = new URLSearchParams(new FormData(formElement))

    const rawResponse = await fetch('/menu/couponCode', {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });

    const responseText = await rawResponse.text();
    console.log(responseText);
    if (rawResponse.status == 200) {
        const updatedShoppingListElement=createElementFromHTML(responseText)
        shoppingItemContext.innerHTML = updatedShoppingListElement.innerHTML;
        return;
    }
    formElement.innerHTML = responseText;
}

