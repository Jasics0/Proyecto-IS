var xMLHttpRequest = new XMLHttpRequest();
function findElements() {
    xMLHttpRequest.open("Get", "/WebScraping/AjaxServlet?link=" + document.getElementById("link").value, true);
    xMLHttpRequest.onreadystatechange = processElements;
    xMLHttpRequest.send(null);
}
function processElements() {
    if (xMLHttpRequest.readyState === 4 && xMLHttpRequest.status === 200) {
        var JSONTopicObject = eval('(' + xMLHttpRequest.responseText + ')');

        console.log(JSONTopicObject);

    }
}

