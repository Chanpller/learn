window.onload=function (){
    var rows = $("tbl_fruit").rows;
    for (let i = 1; i < rows.length-1; i++) {

        bingEvent( rows[i]);
    }
    var addBtn = $("addBtn");
    addBtn.onclick = addBtnOnclick;
    var retsetBtn = $("retsetBtn");

}
function addBtnOnclick(id){
    var fname = $("fname");
    var price = $("price");
    var fcount = $("fcount");
    var rows = $("tbl_fruit").rows;
    var myNewRow  = $("tbl_fruit").insertRow(rows.length-1);
    var myNewCell1 = myNewRow.insertCell(0);
    myNewCell1.innerText = fname.value;
    var myNewCell2 = myNewRow.insertCell(1);
    myNewCell2.innerText = price.value;
    var myNewCell3 = myNewRow.insertCell(2);
    myNewCell3.innerText = fcount.value;

    var myNewCell4 = myNewRow.insertCell(3);
    myNewCell4.innerText = parseInt(price.value)* parseInt(fcount.value);

    var myNewCell5 = myNewRow.insertCell(4);
    myNewCell5.innerHTML = '<img src="del.jpg" class="delImg"/>';
    bingEvent( myNewRow);
    updateZJ();
}

function bingEvent(row){
    row.onmouseover =showBGColor;
    row.onmouseout =clearBGColor;
    var cells = row.cells;
    cells[1].onmouseover =showHand;
    cells[1].onclick = editPrice;
}
function $(id){
    return document.getElementById(id);
}
function showHand(){
    event.srcElement.style.cursor="hand";
}
function editPrice(){
    if(event && event.srcElement && event.srcElement.tagName=="TD"){
        var priceTD = event.srcElement ;
        //目的是判断当前priceTD有子节点，而且第一个子节点是文本节点 ， TextNode对应的是3  ElementNode对应的是1
        if(priceTD.firstChild && priceTD.firstChild.nodeType==3){
            //innerText 表示设置或者获取当前节点的内部文本
            var oldPrice = priceTD.innerText ;
            //innerHTML 表示设置当前节点的内部HTML
            priceTD.innerHTML="<input type='text' size='4'/>";
            // <td><input type='text' size='4'/></td>
            var input = priceTD.firstChild;
            if(input.tagName=="INPUT"){
                input.value = oldPrice ;
                //选中输入框内部的文本
                input.select();
                //4.绑定输入框失去焦点事件 , 失去焦点，更新单价
                input.onblur=updatePrice ;
                //8.在输入框上绑定键盘摁下的事件，此处我需要保证用户输入的是数字
                input.onkeydown=ckInput;
            }
        }

    }
}
//当鼠标悬浮时，显示背景颜色
function showBGColor(){
    //event : 当前发生的事件
    //event.srcElement : 事件源
    //alert(event.srcElement);
    //alert(event.srcElement.tagName);	--> TD
    if(event && event.srcElement && event.srcElement.tagName=="TD"){
        var td = event.srcElement ;
        //td.parentElement 表示获取td的父元素 -> TR
        var tr = td.parentElement ;
        //如果想要通过js代码设置某节点的样式，则需要加上 .style
        tr.style.backgroundColor = "navy" ;

        //tr.cells表示获取这个tr中的所有的单元格
        var tds = tr.cells;
        for(var i = 0 ; i<tds.length ; i++){
            tds[i].style.color="white";
        }
    }
}

//当鼠标离开时，恢复原始样式
function clearBGColor(){
    if(event && event.srcElement && event.srcElement.tagName=="TD"){
        var td = event.srcElement ;
        var tr = td.parentElement ;
        tr.style.backgroundColor="transparent";
        var tds = tr.cells;
        for(var i = 0 ; i<tds.length ; i++){
            tds[i].style.color="threeddarkshadow";
        }
    }
}
//检验键盘摁下的值的方法
function ckInput(){
    var kc = event.keyCode ;
    // 0 ~ 9 : 48~57
    //backspace : 8
    //enter : 13
    //console.log(kc);

    if(!( ( kc>=48 && kc<=57 ) || kc==8 || kc==13 )){
        event.returnValue=false;
    }

    if(kc==13){
        event.srcElement.blur();
    }

}

//更新单价的方法
function updatePrice(){
    if(event && event.srcElement && event.srcElement.tagName=="INPUT"){
        var input = event.srcElement ;
        var newPrice = input.value ;
        //input节点的父节点是td
        var priceTD = input.parentElement ;
        priceTD.innerText = newPrice ;

        //5. 更新当前行的小计这一个格子的值
        //priceTD.parentElement td的父元素是tr
        updateXJ(priceTD.parentElement);

    }
}
function updateXJ(tr){
    var price = tr.cells[1].innerText;
    var count = tr.cells[2].innerText;
    tr.cells[3].innerText = parseInt(price)* parseInt(count);
    updateZJ();
}
function updateZJ(){
    var table_fruit =  $("tbl_fruit")
    var rows = table_fruit.rows;
    var priceZJ = 0;
    for (let i = 1; i < rows.length-1; i++) {

        var cells = rows[i].cells;
        priceZJ += parseInt(cells[3].innerText);
    }
    var rowZJ = rows[rows.length-1];
    rowZJ.cells[1].innerText = priceZJ;
}
