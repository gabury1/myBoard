
// 키워드로 검색.
function Search()
{
     var boardNo =  getNo();

     // 글 정보를 받아서 뿌려준다.
     $.ajax({
            url : "/Board/Info",
            data:{ "boardNo" :  boardNo},
            method : "GET",
            success : success,
            error :
            function(e)
            {
                alert(e.responseText);
            }
     });

}

function success(info)
{
    if(info.flag == "false") location.href = "/Board/Main";

    $("#title").html(info.title);
    $("#content").html(info.content);
    var etc = "추천 " + info.likes + "  |  " + "조회수 " + info.views + "  |  " + "작성일 " + info.dateTime;

    $("#etc").html(etc);
    $("#likes").html("추천" + info.likes);

}

function getNo()
{
    return  document.location.href.split("/")[4]; // 번호를 따로 따서 컨트롤러에 전달
}

