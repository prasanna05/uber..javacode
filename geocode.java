import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.net.*;
import java.io.*;
import org.json.*;
class geocode
{
public static JSONObject geocoding(String addr) throws Exception
{
    // build a URL
    String s = "http://maps.google.com/maps/api/geocode/json?" +
                    "sensor=false&address=";
    s += URLEncoder.encode(addr, "UTF-8");
    URL url = new URL(s);
 
    // read from the URL
    Scanner scan = new Scanner(url.openStream());
    String str = new String();
    while (scan.hasNext())
        str += scan.nextLine();
    scan.close();
 
    // build a JSON object
    JSONObject obj = new JSONObject(str);
    if (! obj.getString("status").equals("OK"))
    {
    JSONObject objn=new JSONObject();
        return  objn;
    }
 
    // get the first result
    JSONObject res = obj.getJSONArray("results").getJSONObject(0);
    System.out.println(res.getString("formatted_address"));
    //System.out.println(res.getJSONObject("geometry"));
    JSONObject res2=res.getJSONObject("geometry").getJSONObject("location");
   // System.out.println(res2);
    //System.out.println(res2.getString("lat"));
    //System.out.println(res2.getString("lng"));
   
    return res2;
}
public static void getrideestimate(String s1,String s2,String s3,String s4)
{
    try
    {
        //System.out.println("OO");
    String uberurl=new String("https://api.uber.com/v1.2/estimates/price?server_token=3asL0NWI8SLgpvPOHI3XK-O1TvgQUAw1OTD53LXH&");
    uberurl=uberurl+"start_latitude="+s1+"&start_longitude="+s2+"&end_latitude="+s3+"&end_longitude="+s4;
    System.out.println(uberurl);
    URL url1 = new URL(uberurl);
    Scanner scan1 = new Scanner(url1.openStream());
    String ustr = new String();
    while (scan1.hasNext())
        ustr += scan1.nextLine();
    scan1.close();
 
    // build a JSON object
    JSONObject obj = new JSONObject(ustr);
    /*if (! obj.getString("status").equals("OK"))
     * 
    {
        return;
   }*/
   
    //JSONObject res = obj.getJSONArray("prices").getJSONObject(0);
   /* System.out.println(res.getString("display_name"));
    String str1=res.getString("estimate");
    System.out.println("Rs."+str1.substring(1));*/
    JSONArray res = obj.getJSONArray("prices");
    for(int i=0;i<res.length();i++)
    {
        System.out.println(res.getJSONObject(i).getString("localized_display_name"));
        System.out.println(res.getJSONObject(i).getString("distance")+"km");
        
        System.out.println((res.getJSONObject(i).getString("duration"))+"sec");
        System.out.println("Rs."+"("+res.getJSONObject(i).getString("low_estimate")+"-"+res.getJSONObject(i).getString("high_estimate")+")");
        System.out.println("*********************************************************************");
        
    }
}
catch(Exception e)
{
    e.printStackTrace();
}
}

public static void main(String args[])
{
    //geocode gc=new geocode();
    try{
    Scanner sc=new Scanner(System.in);
    System.out.println("enter source");
    String src=sc.next();
    System.out.println("enter destination");
    String des=sc.next();
    JSONObject srccod=geocoding(src);
    //System.out.println(srccod.getString("lat"));
    String arg1=srccod.getString("lat");
    //System.out.println(srccod.getString("lng"));
    String arg2=srccod.getString("lng");
    JSONObject descod=geocoding(des);
    //System.out.println(descod.getString("lat"));
    String arg3=descod.getString("lat");
    //System.out.println(descod.getString("lng"));
    String arg4=descod.getString("lng");
    getrideestimate(arg1,arg2,arg3,arg4);
    //System.out.println(srccod);
    //System.out.println(descod);
    
}
catch(Exception e)
{
    e.printStackTrace();
}
}
}