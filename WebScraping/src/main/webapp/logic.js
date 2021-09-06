var xMLHttpRequest = new XMLHttpRequest();

window.onload = () => {
    document.getElementById('loading').hidden = true;
};

function findElements() {
    document.getElementById('content').innerHTML = "";
    document.getElementById('loading').hidden = false;
    xMLHttpRequest.open("Get", "/WebScraping/AjaxServlet?link=" + document.getElementById("link").value, true);
    xMLHttpRequest.onreadystatechange = processElements;
    xMLHttpRequest.send(null);
}

function processElements() {
    document.getElementById('loading').hidden = true;
    var content = document.getElementById("content");
    if (xMLHttpRequest.readyState === 4 && xMLHttpRequest.status === 200) {
        var JSONTopicObject = eval('(' + xMLHttpRequest.responseText + ')').Pages;

        if (JSONTopicObject !== null) {

            JSONTopicObject.forEach(site => {
                var border = document.createElement("div");
                border.className = "border border-info rounded text-center m-2 p-2";
                
                var title = document.createElement("h4");
                title.innerHTML = site.title;
                
                var div = document.createElement("div");
                
                var size = document.createElement("label");
                size.innerHTML = "<b>Size: </b>" + site.size + " kb";
                
                var is_form = document.createElement("input");
                is_form.type = "checkbox";
                is_form.className = "m-1";
                is_form.disabled = true;
                is_form.checked = site.is_form;
                var form = document.createElement("label");
                form.innerHTML = "Form";
                
                var is_login = document.createElement("input");
                is_login.type = "checkbox";
                is_login.className = "m-1";
                is_login.disabled = true;
                is_login.checked = site.is_form;
                var login = document.createElement("label");
                login.innerHTML = "Login";

                var links = document.createElement("table");
                links.className = "table table-striped";
                links.innerHTML = "<tr class='text-center'><th scope='col'>#</th><th scope='cols'>Link</th><th>active</th></tr>";

                site.list_links.forEach((link, index) => {
                    var headrow = links.insertRow();
                    var headcell1 = headrow.insertCell();
                    headcell1.innerHTML = index;
                    var headcell2 = headrow.insertCell();
                    var l = document.createElement("a");
                    l.href = link;
                    l.innerHTML = link;
                    headcell2.className = "text-break";
                    headcell2.appendChild(l);
                    var headcell3 = headrow.insertCell();
                    headcell3.className = "text-center";
                    var check = document.createElement("i");
                    const len = site.links_types[index].length-1;
                    if (site.links_types[index][len] === "1"){
                        check.className = "bi bi-check h3 text-success";
                    } else {
                        check.className = "bi bi-x h3 text-danger";
                    }
                    headcell3.appendChild(check);
                });
                
                var nlinks = document.createElement("button");
                nlinks.type = "button";
                nlinks.className = "btn btn-outline-primary m-1 col-md-5";
                nlinks.setAttribute("data-toggle", "modal");
                nlinks.setAttribute("data-target", "#myModal");
                nlinks.setAttribute("data-title", site.title);
                nlinks.setAttribute("data-table", links.outerHTML);
                nlinks.innerHTML = "Links: " + site.nlinks;

                var lines = document.createElement("table");
                lines.className = "table table-striped";
                lines.innerHTML = "<tr class='text-center'><th scope='col'>#</th><th scope='cols'>Size of line</th></tr>";

                site.lines_size.forEach((line, index) => {
                    var headrow = lines.insertRow();
                    var headcell1 = headrow.insertCell();
                    headcell1.innerHTML = index;
                    var headcell2 = headrow.insertCell();
                    headcell2.innerHTML = line;
                });
                
                var nlines = document.createElement("button");
                nlines.type = "button";
                nlines.className = "btn btn-outline-primary m-1 col-md-5";
                nlines.setAttribute("data-toggle", "modal");
                nlines.setAttribute("data-target", "#myModal");
                nlines.setAttribute("data-title", site.title);
                nlines.setAttribute("data-table", lines.outerHTML);
                nlines.innerHTML = "Lines: " + site.nlines;

                var word_concurr = document.createElement("table");
                word_concurr.className = "table table-striped";
                word_concurr.innerHTML = "<tr class='text-center'><th scope='col'>Word</th><th scope='cols'>Frequency</th></tr>";

                site.words_concurrency.forEach(tuple => {
                    var headrow = word_concurr.insertRow();
                    var headcell1 = headrow.insertCell();
                    headcell1.innerHTML = tuple.split("-")[0];
                    var headcell2 = headrow.insertCell();
                    headcell2.innerHTML = tuple.split("-")[1];
                });
                
                var words = document.createElement("button");
                words.type = "button";
                words.className = "btn btn-outline-primary m-1 col-md-5";
                words.setAttribute("data-toggle", "modal");
                words.setAttribute("data-target", "#myModal");
                words.setAttribute("data-title", site.title);
                words.setAttribute("data-table", word_concurr.outerHTML);
                words.innerHTML = "Words";

                var images = document.createElement("table");
                images.className = "table table-striped";
                images.innerHTML = "<tr class='text-center'><th scope='col'>Link</th><th scope='cols'>Download</th></tr>";

                site.links_image.forEach(lnk => {
                    var headrow = images.insertRow();
                    var headcell1 = headrow.insertCell();
                    var l = document.createElement("a");
                    l.href = lnk;
                    l.innerHTML = lnk;
                    headcell1.className = "word-break";
                    headcell1.appendChild(l);
                    var headcell2 = headrow.insertCell();
                    var l1 = document.createElement("a");
                    l1.href = lnk;
                    l1.target = '_blank';
                    l1.innerHTML = "<i class='bi bi-download h3'></i>";
                    headcell2.className = "text-center";
                    headcell2.appendChild(l1);
                });
                
                var imgs = document.createElement("button");
                imgs.type = "button";
                imgs.className = "btn btn-outline-primary m-1 col-md-5";
                imgs.setAttribute("data-toggle", "modal");
                imgs.setAttribute("data-target", "#myModal");
                imgs.setAttribute("data-title", site.title);
                imgs.setAttribute("data-table", images.outerHTML);
                imgs.innerHTML = "Images";
                
                div.appendChild(size);
                div.appendChild(is_form);
                div.appendChild(form);
                div.appendChild(is_login);
                div.appendChild(login);
                border.appendChild(title);
                border.appendChild(div);
                border.appendChild(nlinks);
                border.appendChild(nlines);
                border.appendChild(document.createElement("br"));
                border.appendChild(words);
                border.appendChild(imgs);
                content.appendChild(border);
            });
        } else {
            alert("Please enter at least one valid link");
        }

    }
}

$('#myModal').on('show.bs.modal', (e) => {
    var button = $(e.relatedTarget);
    var title = button.data("title");
    var table = button.data("table");
    
    $('#myModalLabel').text(title);
    $('#modal-body').html(table);
});
