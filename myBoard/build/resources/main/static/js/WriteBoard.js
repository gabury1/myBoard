// 키워드로 검색.
function writeBoard(userNo)
{
    let title = $("#title").val();
    let content = $(".ql-editor").html();

    data = { "writerNo" :  userNo, "title" : title, "content" : content};

     // 글 정보를 받아서 뿌려준다.
     $.ajax({
            url : "/Board/Post",
            data: data,
            method : "GET",
            success : success,
            error :
            function(e)
            {
                alert(e.responseText);
            }
     });

}

function success(message)
{
    location.href = "/Board/Main";
}