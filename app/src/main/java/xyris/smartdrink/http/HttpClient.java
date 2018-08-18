package xyris.smartdrink.http;


import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import xyris.smartdrink.http.exceptions.ClientException;

public class HttpClient
{
  private static final String HTTPS_PROTOCOL = "https";
  public static final String HTTP_POSTMETHOD = "POST";
  public static final String HTTP_GETMETHOD = "GET";
  private static String sessionCookie = "";
  private boolean isHostValidationInactive = false;
  
  public HttpConnection getConnection(URL url, int connectTimeOut, int readtTimeOut)
    throws ClientException
  {
    return getConnection(url, connectTimeOut, readtTimeOut, null);
  }
  
  public HttpConnection getConnection(URL url, int connectTimeOut, int readtTimeOut, Authentication authentication)
    throws ClientException
  {
    return getConnection(url, connectTimeOut, readtTimeOut, authentication, "POST");
  }
  
  public HttpConnection getConnection(URL url, int connectTimeOut, int readtTimeOut, Authentication authentication, String httpMethod)
    throws ClientException
  {
    URL urltmp;
    try
    {
      urltmp = new URL(null, url.toString());
    }
    catch (Exception e)
    {
      throw new ClientException("Error el obtener URL. " + e.getMessage(), e);
    }
    //Logger.debug("Creating connection...");
    //Logger.debug("URL=" + urltmp.toString());
    HttpConnection httpConnection;
    try
    {
      HttpURLConnection conn = (HttpURLConnection)urltmp.openConnection();
        try
        {
          Class[] parameterClass = { Integer.TYPE };
          Object[] paramValTOConn = { new Integer(connectTimeOut) };
          Object[] paramValTORead = { new Integer(readtTimeOut) };
          
          Method setConnectTimeoutMethod = conn.getClass().getMethod("setConnectTimeout", parameterClass);
          Method setReadTimeoutMethod = conn.getClass().getMethod("setReadTimeout", parameterClass);
          setConnectTimeoutMethod.invoke(conn, paramValTOConn);
          setReadTimeoutMethod.invoke(conn, paramValTORead);
          
          //Logger.debug("connectionTimeout= " + connectTimeOut + " readTimeOut=" + readtTimeOut);
        }
        catch (Exception e)
        {
          //Logger.error("Error estableciendo timeout: " + e);
          throw e;
        }

      if ((urltmp.getProtocol().equals("https")) && (this.isHostValidationInactive)) {
        disabledHostNameVerifier(conn);
      }
      conn.setRequestMethod(httpMethod);
      //Logger.debug("HTTP_METHOD=" + httpMethod);

      if (!sessionCookie.equals(""))
      {
        conn.setRequestProperty("Cookie", sessionCookie);
        //Logger.debug("Session:" + sessionCookie);
      }
      httpConnection = new HttpConnection(conn);
    }
    catch (Exception e)
    {
      throw new ClientException(e.getMessage(), e);
    }
    return httpConnection;
  }
  
  public static String getSessionCookie()
  {
    return sessionCookie;
  }
  
  public static void setSessionCookie(String sessionCookie)
  {
    sessionCookie = sessionCookie;
  }
  
  private void disabledHostNameVerifier(HttpURLConnection conn)
  {
    if ((conn instanceof HttpsURLConnection)) {
      ((HttpsURLConnection)conn).setHostnameVerifier(new HostnameVerifier()
      {
        public boolean verify(String urlHostname, SSLSession session)
        {
          return true;
        }
      });
    }
  }
  
  public boolean isHostValidationInactive()
  {
    return this.isHostValidationInactive;
  }
  
  public void setHostValidationInactive(boolean isHostValidationInactive)
  {
    this.isHostValidationInactive = isHostValidationInactive;
  }
}
