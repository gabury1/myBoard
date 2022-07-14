
//글 정보를 받아오는 부분
function Load()
{
     var boardNo =  getNo();

     $.ajax({
            url : "/Board/Info",
            data:{ "boardNo" :  boardNo},
            method : "GET",
            success :
            function (info)
            {
                if(info.flag == "false") location.href = "/Board/Main";

                // 제목과 내용을 표시해준다.
                $("#title").val(info.title);
                $(".ql-editor").html(info.content);

            },
            error :
            function(e)
            {
                alert(e.responseText);
            }
     });

}

// 수정된 정보를 보냄.
function Update()
{
    var boardNo =  getNo();
    var title = $("#title").val();
    var content = $(".ql-editor").html();

    $.ajax({
           url : "/Board/Update",
           data:{ "boardNo" :  boardNo, "title" : title, "content" : content},
           method : "GET",
           success :
           function (message)
           {
                // 성공이라면 해당 상세보기 페이지로 돌아간다.
                if(message == "Success") location.href = "/Board/" + boardNo;
                else alert(message);
           },
           error :
           function(e)
           {
               alert(e.responseText);
           }
    });
}


function getNo()
{
    return  document.location.href.split("/")[4]; // 번호를 따로 따서 컨트롤러에 전달
}