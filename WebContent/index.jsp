<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">

<link rel="stylesheet" type="text/css" href="css/tableDetail.css">

<title>DataTables Test</title>
</head>
<body>
	<table id="myTable" class="table">
		<thead>
			<tr>
<!-- 				<th>年级</th> -->
<!-- 				<th>班级</th> -->
<!-- 				<th>姓名</th> -->
<!-- 				<th>性别</th> -->
				<th width="25%">组织</th>
				<th width="25%">男、女个数</th>
				<th width="25%">男占比</th>
				<th width="25%">女占比</th>
			</tr>
		</thead>
		
		<tbody>			
		</tbody>
		
	</table>


<script type="text/javascript" src="js/jquery.js"></script> 
<script type="text/javascript" src="js/jquery.dataTables.js"></script> 

<script>
var oTable;
$(document).ready(function() {
    oTable = $('#myTable').dataTable({
        info : false,
        paging: false,
        processing: false,
        ordering: false,
        searching: false,
    	columns:[
    	         {data: 'grade'},
    	         {data: function ( row, type, set ) {
    	        	        return '男生有' + row['man'] + '人，女生有' + row['woman'] + '人。' ;
    	        	    }},
    	         {data: 'man'},
    	         {data: 'woman'}
    	         ],
    	 serverSide: true,
    	 ajax: {
    		 url: 'FindServlet',
    	     dataSrc: '',
    		 type: 'POST',
    		 dataType : 'json',
    		 scriptCharset: 'utf-8'
    	 },
    	 columnDefs: [ {
             targets: 0,
             "render": function ( data, type, row ) {
                    return "<span class='row-details row-details-close' data_id='"
                    	+ row['grade'] + "'></span>" + data;
              }
         } ]
    });
    
    $('.table').on('click', ' tbody td .row-details', function () { 
    	//alert('a'); 
        var nTr = $(this).parents('tr')[0];  
        if (oTable.fnIsOpen(nTr))//判断是否已打开              
        {  
        /* This row is already open - close it */  
            $(this).addClass("row-details-close").removeClass("row-details-open");  
                    oTable.fnClose( nTr );  
        }else{  
            /* Open this row */                  
            $(this).addClass("row-details-open").removeClass("row-details-close");  
            // 调用方法显示详细信息 data_id为自定义属性 存放配置ID  
            fnFormatDetails(nTr,$(this).attr("data_id"));  
        }  
    });  
});

function fnFormatDetails(nTr,pdataId){
	$.ajax({ 
        type:'post',         
        url:"FindServlet?type=detail", 
        dataSrc: '',
        data:{"pdataId":pdataId}, 
        dataType:"text", 
        async:true, 
        success:function (data,textStatus){                              
            if(textStatus=="success"){  //转换格式 组合显示内容 
                var res = eval("("+data+")"); 
                var sOut = '<table style="width:100%;">'; 
                for(var i=0;i<res.length;i++){ 
                    sOut+='<tr>'; 
                    sOut+='</td><td width="25%">&nbsp&nbsp&nbsp&nbsp&nbsp'+res[i].class+'</td>'; 
                    sOut+='<td width="25%">男生有'+res[i].man + '人, 女生有' + res[i].woman +'人</td>'; 
                    sOut+='<td width="25%">'+res[i].man+'</td>';
                    sOut+='<td width="25%">'+res[i].woman+'</td>';
                    sOut+='</tr>'; 
                             
                } 
                sOut+='</table>'; 
                oTable.fnOpen( nTr,sOut, 'details' ); 
            } 
        }, 
        error: function(){//请求出错处理 
            oTable.fnOpen( nTr,'加载数据超时~', 'details' ); 
        } 
    });
} 
</script>

</body>
</html>