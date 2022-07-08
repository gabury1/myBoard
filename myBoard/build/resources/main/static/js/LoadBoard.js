
var mode = ""; // 전체글/개념글
var key = ""; // 어떤 주제로 검색?
var keyword = ""; // 어떤 문자열로 검색?
var page = 0; // 현재 몇 페이지?

modeChange(0); // 로드 시 전송

// 키워드로 검색.
function Search()
{
     $.ajax({
            url : "/Board/getBoardList",
            data:{"mode" : this.mode, "key" :  this.key  , "keyword" : this.keyword , "page" :  this.page },
            method : "GET",
            success : function( boardlist )
            {
                alert( boardlist );
            },
            error :
            function(e)
            {
                alert(e.responseText);
            }
     });

}


// 모드를 바꾼다. 모드를 바꿀때는 모든 키/키워드/페이지가 초기화 된다.
function modeChange(pMode)
{
    mode = pMode;
    page = 0;
    key = "title";
    keyword = "";

    Search();

}

function keyChange()
{
    page = 0;
    key = $("#key").val();
    keyword = $("#keyword").val();

    Search();

}

function pageChange(pPage)
{
    page = pPage;

    Search();
}


