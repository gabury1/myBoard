// 키워드로 검색.
function writeBoard(userNo)
{
    let title = $("#title").val();
    let content = $(".ql-editor").html();

     // 글 정보를 받아서 뿌려준다.
     $.ajax({
            url : "/Board/Post",
            data:{ "writerNo" :  userNo, "title" : title, "content" : content},
            method : "GET",
            success : success,
            error :
            function(e)
            {
                alert("아마도 사진 문제인듯 합니다... 사진 내려주세요.(난 프론트가 아니니까 봐주세요.)");
                alert(e.responseText);
            }
     });

}

function success(message)
{
    location.href = "/Board/Main";
}