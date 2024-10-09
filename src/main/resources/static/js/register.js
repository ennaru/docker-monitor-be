const validator = {
    port: function(text) {
        let regex = new RegExp("\\d+:\\d+", "i");
        return regex.test(text);
    },
    env: function(text) {
        return true;
    }
};
function searchList(nodes, text) {
    let flag = true;
    if(text === "") {
        return false;
    }
    nodes.forEach((item) => {
        if(item.innerText === text) {
            alert("중복 건은 등록이 불가합니다.");
            flag = false;
        }
    });
    return flag;
}
function listAdd(area, text, validatorName) {
    if(validatorName != null) {
        if(!validator[validatorName](text)) {
            alert("형식을 확인해 주세요.");
            return;
        }
    }

    if(searchList(area.childNodes, text)) {
        let tempNode = document.createElement("span");
        let closeTempNode = document.createElement("span");
        closeTempNode.className = "close";
        closeTempNode.addEventListener("click", () => {
            document.removeChild(this);
        })
        tempNode.innerText = text;
        tempNode.appendChild(closeTempNode);
        area.appendChild(tempNode);
    }
}
function listRemove() {

}
function submit() {

    let getValue = function(name) {
        let object = document.querySelector(`.list-box input[name=${name}]`);
        if(object != null) {
            return object.value;
        } else {
            return "";
        }
    }

    let json = {
        "name" : getValue("name"),
        "image" : getValue("image"),
        "restart" : getValue("restart"),
    }
}
window.onload = function () {
    let lists = Array.from(document.getElementsByClassName("list-box"));
    if(lists.length === 0) {
        return;
    }

    lists.forEach(list => {
        let text = list.querySelector("input[type=text]");
        let area = list.querySelector(".list-area");
        if(text != null && area != null) {
            text.addEventListener("keydown", function (event) {
                if(event.key !== "Enter") {
                    return;
                }
                if(list.dataset.validator !== undefined) {
                    listAdd(area, this.value, list.dataset.validator);
                } else {
                    listAdd(area, this.value);
                }
            });
        }
    });
}