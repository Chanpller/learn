function deleteFruit(fid){
    if(confirm("确定删除fid="+fid+"的水果？")){
        window.location.href = "deleteFruitServlet?fid="+fid;
    }
}
function loadPage(page){
    window.location.href = "queryPageFruitServlet?page="+page;
}
