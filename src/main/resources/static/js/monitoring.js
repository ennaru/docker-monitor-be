function containerLoad(data) {

    let envArea = document.createElement("div");
    envArea.className = "content";
    if(Array.isArray(data.config.Env)) {
        data.config.Env.forEach(env => {
            let envElement = document.createElement("div");
            let envArray = env.split("=");
            envElement.innerHTML = `${envArray[0]} <span class='divider'>=</span> ${envArray[1]}`;
            envArea.appendChild(envElement);
        })
    }

    let mountArea = document.createElement("div");
    mountArea.className = "content";
    if(Array.isArray(data.mounts)) {
        data.mounts.forEach(mount => {
            let mountElement = document.createElement("div");
            mountElement.innerHTML = `${mount.source} <span class='divider'>:</span> ${mount.destination}`;
            mountArea.appendChild(mountElement);
        })
    }

    let temp = document.createElement("div");
    temp.innerHTML = `
        <h4>${data.name}</h4>
        <div>
            <div class="infos"><span class="label">Image</span><span class="content">${data.config.Image}</span></div>
            <div class="infos"><span class="label">Status</span><span class="content">${data.state.status}</span></div>
            <div class="infos"><span class="label">CreateDt</span><span class="content">${getDateTime(data.created)}</span></div>
            <div class="infos"><span class="label">StartedDt</span><span class="content">${getDateTime(data.state.startedAt)}</span></div>
        </div>
        <div>
            <div class="infos"><span class="label">Port</span>6379-6379</div>
        </div>
        <div>
            <div class="infos"><span class="label">Mount</span>${mountArea.outerHTML}</div>
        </div>
        <div>
            <div class="infos"><span class="label">Envinroment</span>${envArea.outerHTML}</div>
        </div>
    `;

    document.querySelector(".container-info .content").innerHTML = temp.outerHTML;
}
function containerListFail(data) {
    clearInterval(intervalCode);
    let position = document.querySelector(".container-list .content");
    let temp = document.createElement("div");
    temp.innerHTML = `
                    <div>Container를<br/>불러올 수 없습니다.</div>
                `;
    position.append(temp);
}
function containerListLoad(data) {
    //datatype check
    if(data != null) {
        if(Array.isArray(data)) {
            let position = document.querySelector(".container-list .content");
            position.innerHTML = "";
            data.forEach((el) => {
                let state = el.state !== '' ? el.state : '';
                let temp = document.createElement("div");
                temp.className = "containers";
                temp.innerHTML = `
                    <div class="title ${state}">${el.names}</div>
                    <div>${el.id.substring(0, 12)} 📄</div>
                    <div>${el.state}</div>
                `;

                temp.addEventListener("click", function () {
                    get(`/status/${el.id}`, containerLoad);
                })

                position.append(temp);
            });
        }
    } else {

    }

}