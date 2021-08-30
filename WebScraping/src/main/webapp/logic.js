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
        
        var title = document.getElementById("title");
        title.innerHTML = JSONTopicObject.title;
        
        var size = document.getElementById("size");
        size.innerHTML = JSONTopicObject.size.toString() + " kb";
        
        var isForm = document.getElementById("isForm");
        isForm.checked = JSONTopicObject.isForm;
        
        var isLogin = document.getElementById("isLogin");
        isLogin.checked = JSONTopicObject.isLogin;
        
        var nlinks = document.getElementById("nlinks");
        nlinks.innerHTML = JSONTopicObject.nlinks.toString() + ".";
        
        var table = document.getElementById("links");
        table.innerHTML = "<tr><th scope='col'>#</th><th scope='cols'>Links</th></tr>";
        
        JSONTopicObject.list_links.forEach((link, index) => {
            var headrow = table.insertRow();
            var headcell1 = headrow.insertCell();
            headcell1.innerHTML = index;
            var headcell2 = headrow.insertCell();
            headcell2.innerHTML = link;
        });
        
        var nlines = document.getElementById("nlines");
        nlines.innerHTML = JSONTopicObject.nlines.toString() + ".";
        
        var lines = document.getElementById("lines");
        lines.innerHTML = "<tr><th scope='col'>#</th><th scope='cols'>Size of line</th></tr>";
        
        JSONTopicObject.lines_size.forEach((line, index) => {
            var headrow = lines.insertRow();
            var headcell1 = headrow.insertCell();
            headcell1.innerHTML = index;
            var headcell2 = headrow.insertCell();
            headcell2.innerHTML = line;
        });
        
    }
}

