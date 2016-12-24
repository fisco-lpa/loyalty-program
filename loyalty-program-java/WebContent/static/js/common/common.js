function dateFormatUtil(longTypeDate){  
    var dateTypeDate = "";  
    var date = new Date();  
    date.setTime(longTypeDate);  
    dateTypeDate += date.getFullYear();   // 年
    dateTypeDate += "-" + getMonth(date); // 月
    dateTypeDate += "-" + getDay(date);   // 日
    return dateTypeDate;  
}  

// 返回 01-12 的月份值
function getMonth(date){  
    var month = "";  
    month = date.getMonth() + 1; // getMonth()得到的月份是0-11
    if(month<10){  
        month = "0" + month;  
    }  
    return month;  
}  
          
// 返回01-30的日期
function getDay(date){  
    var day = "";  
    day = date.getDate();  
    if(day<10){  
        day = "0" + day;  
    }  
    return day;  
}  
        
function toDate(date){
	Date.prototype.format=function(fmt) {        
	    var o = {        
	    "M+" : this.getMonth()+1, // 月份
	    "d+" : this.getDate(), // 日
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, // 小时
	    "H+" : this.getHours(), // 小时
	    "m+" : this.getMinutes(), // 分
	    "s+" : this.getSeconds(), // 秒
	    "q+" : Math.floor((this.getMonth()+3)/3), // 季度
	    "S" : this.getMilliseconds() // 毫秒
	    };        
	    var week = {        
	    "0" : "\u65e5",        
	    "1" : "\u4e00",        
	    "2" : "\u4e8c",        
	    "3" : "\u4e09",        
	    "4" : "\u56db",        
	    "5" : "\u4e94",        
	    "6" : "\u516d"       
	    };        
	    if(/(y+)/.test(fmt)){        
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
	    }        
	    if(/(E+)/.test(fmt)){        
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
	    }        
	    for(var k in o){        
	        if(new RegExp("("+ k +")").test(fmt)){        
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
	        }        
	    }        
	    return fmt;        
	} 
	var t=date;
	var d=	new Date();
	d.setTime(t);
	var s=d.format('yyyy-MM-dd HH:mm:ss');
	return s;
}