function disp(form) {
    if (form.id === "login") {
        document.getElementById('reg').style.display = "none";
    } else document.getElementById('login').style.display = "none";
    if (form.style.display === "none") {
        form.style.display = "block";
    } else {
        form.style.display = "none";
    }
}
let request;

function login() {
    let name = document.getElementById("name").value;
    let password = document.getElementById("password").value;
    let service = {
        "name": name,
        "password": password
    };
    let json = JSON.stringify(service);
    request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8080/task_8_war/api/registration/auth");
    request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200){
            let value = request.responseText;
            printService(request.responseText);
            let json = JSON.parse(value);
            if (json.status === "standard") {
                let a = document.createElement("a");
                a.setAttribute("id", "upgrade");
                a.setAttribute("href", "javascript:update()");
                a.innerText = "upgrade";
                document.getElementById("output").appendChild(a);
            }
        }
    };
    request.send(json);
    document.getElementById('login').style.display = "none";
}

function update() {
    let value = document.getElementById("json").innerText;
    let serviceJson = JSON.parse(value);
    let service = {
        "name": serviceJson.login,
        "password": serviceJson.password
    };
    let json = JSON.stringify(service);
    request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8080/task_8_war/api/registration/auth");
    request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200){
            printService(request.responseText);
            let a = document.getElementById("upgrade");
            document.getElementById("json").removeChild(a);
        }
    };
    request.send(json);
}

function printService(json) {
    let service = JSON.parse(json);
    document.getElementById("json").innerHTML = service;
}