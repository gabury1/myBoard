function Login(){

    // 폼 객체화
    let form = $("#loginform")[0];
    let fd = new FormData(form);

    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

    // 폼 전송
    $.ajax({
        url: "/User/Login",
        type: "POST",
        data: fd,
        contentType: false,
        processData: false,

        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function(data){

            if(data == "Success")
                location.href = "/";
            else
            {
                $("#warn").text(data);
                $("#warn").show();
            }

        },
        error: function(e){
            alert(e.responseText);
        }
    });

}