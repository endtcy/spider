package com.fishroad.spider;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
 
public class MyProcessor implements PageProcessor {
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static int count =0;
    //新闻类型		
    private static String type="(news|ent|sports|money|tech|auto|lady|gz.house|travel|edu|gongyi|daxue|mobile|home)";
    
    public static Spider spider=null;
    
    private JSONObject o=null;
    @Override
    public Site getSite() {
        return site;
    }
 
    @Override
    public void process(Page page) {
        //判断链接是否符合http://www.cnblogs.com/任意个数字字母-/p/7个数字.html格式
        if(page.getUrl().regex("http://sdk.comment.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/\\w*").match()){
        	
        	o=JSONObject.parseObject(page.getRawText());
        	page.putField("item", o);
        	count ++;
        }else{
        	
        	//加入满足条件的链接
        	page.addTargetRequests(
        			page.getHtml().links().regex("(http://"+type+"\\.163\\.com(/\\w{3,}/|/\\D{2}/|(/16/|/17/))[/\\w*]+)").all());
        	page.addTargetRequests(
        			page.getHtml().links().regex("(http://"+type+"\\.163\\.com/(17|16)[/\\w*]+.html)").all());
        	if(page.getUrl().regex("http://"+type+"\\.163\\.com/(17|16)/[0-9]{4}/[0-9]{2}/\\w+.html").match()){
        		page.addTargetRequest("http://sdk.comment.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/"
        				+ page.getUrl().toString().substring(page.getUrl().toString().lastIndexOf("/")+1, page.getUrl().toString().lastIndexOf(".")));
        	}
        }
    }
 
    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();
        spider=Spider.create(new MyProcessor()).addUrl("http://news.163.com/").addPipeline(new MySQLPieline()).thread(50);
        spider.run();
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		MyProcessor.count = count;
	}
 
}
