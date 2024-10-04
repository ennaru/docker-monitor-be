function get(uri, callback, fallback) {
    $.ajax({
        url: uri,
        type: 'GET',
        success: function (response) {
            if(response.result === '0000') {
                callback(response.data);
            } else {
                if(typeof fallback === 'function') {
                    fallback(response);
                } else {
                    alert(response.message);
                }
            }
        },
        failure: function (xhr, status, error) {
            fallback(error);
        }

    });
}
function post(uri, data, callback, fallback) {
    $.ajax({
        url: uri,
        type: 'POST',
        data: data,
        success: function (response) {
            if(response.result === '0000') {
                callback(response.data);
            } else {
                if(typeof fallback === 'function') {
                    fallback(response);
                } else {
                    alert(response.message);
                }
            }
        },
        failure: function (xhr, status, error) {
            fallback(error);
        }
    });
}
function getDate(dateString) {
    let date = new Date(dateString);
    return date.getFullYear() + '-' +
        String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0') + ' ';
}
function getDateTime(dateString) {
    const date = new Date(dateString);
    return date.getFullYear() + '-' +
        String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0') + ' ' +
        String(date.getHours()).padStart(2, '0') + ':' +
        String(date.getMinutes()).padStart(2, '0') + ':' +
        String(date.getSeconds()).padStart(2, '0');
}