
	

//获取倍数
$.get("http://123.206.231.254/spider/getRate",function(data){
		var rate=parseFloat(data);
		
		
		//头条
//		var a1=$(".pgc-dashboard-column .pgc-dashboard-primary").eq(0).text("dfsdf");
//		console.log(a1.eq(0));

//*****************************百度百家**********************************
		var income1=$(".num.money:eq(0)");
		var income2=$(".num.money:eq(1)");
		var income3=$(".num.money:eq(2)");
		//-----------------收入广场 昨日收益------------------
		if(!isNaN(parseFloat(income1.text())))
			income1.text(fmoney(""+parseFloat(income1.text())*rate,2));
		//-----------------收入广场 7日收益-------------------
		if(!isNaN(parseFloat(income2.text())))
			income2.text(fmoney(""+parseFloat((income2.text())*rate),2));
		//-----------------收入广场 30日收益------------------
		if(!isNaN(parseFloat(income3.text())))
			income3.text(fmoney(""+parseFloat(income3.text())*rate,2));
		//-----------------移除财务报告按钮----------------
		$(".cep-btn").remove();
		
		//-------------首页  昨日收益---------------
		var a2=$(".index-top-statistics-number:eq(0)");
		a2.on('DOMNodeInserted',function(e) {
//			    alert('element now contains: '+ $(e.target).html());
			a2.off('DOMNodeInserted');
			if(!isNaN(parseFloat(a2.text())))
				a2.text(fmoney(a2.text()*rate,2));
			
		});
		setTimeout(function(){
			if(!isNaN(parseFloat(a2.text()))){
				var objEvt = $._data(a2[0], "events");
				if (objEvt && objEvt["DOMNodeInserted"]) {
					a2.off('DOMNodeInserted');
					a2.text(fmoney(a2.text()*rate,2));
				}
			}
		}, 500);
},"json");
	
	function fmoney(s, n)   
	{   
	   n = n > 0 && n <= 20 ? n : 2;   
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
	   var l = s.split(".")[0].split("").reverse(),   
	   r = s.split(".")[1];   
	   t = "";   
	   for(i = 0; i < l.length; i ++ )   
	   {   
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
	   }   
	   return t.split("").reverse().join("") + "." + r;   
	} 
	
	
	