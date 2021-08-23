var xMLHttpRequest = new XMLHttpRequest();
function findElements() {
    xMLHttpRequest.open("Get", "/WebScraping/AjaxServlet?link=" + document.getElementById("link").value, true);
    xMLHttpRequest.onreadystatechange = processElements();
    xMLHttpRequest.send(null);

}
function processElements() {
    if (xMLHttpRequest.readyState === 4 && xMLHttpRequest.status === 200) {
        var JSONTopicObject = eval('(' + xMLHttpRequest.responseText + ')');
        console.log(JSONTopicObject);
        var table = document.getElementById("elementsTable");
        table.innerHTML = "";
        var headrow = table.insertRow(0);
        var headcell = headrow.insertCell(0);
        headcell.style.backgroundColor = "lightblue";
        headcell.innerHTML = JSONTopicObject.title;
        //var tutorials = JSONTopicObject.topic.tutorial;
    }
}

