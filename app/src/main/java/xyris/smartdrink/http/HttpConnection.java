package xyris.smartdrink.http;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import xyris.smartdrink.http.exceptions.ClientException;
import xyris.smartdrink.http.exceptions.HttpException;
import xyris.smartdrink.http.exceptions.TimeOutException;

public class HttpConnection
{
  public static final int HTTP_OK = 200;
  public static final int HTTP_CONFLICT = 409;
  private HttpURLConnection connection = null;
  
  public HttpConnection(HttpURLConnection conn)
  {
    this.connection = conn;
  }
  
  public void addProperty(String key, String value)
  {
    this.connection.addRequestProperty(key, value);
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    this.connection.setDoOutput(true);
    logRequestProperties();
    return this.connection.getOutputStream();
  }
  
  public String getHeader(String key)
  {
    return this.connection.getHeaderField(key);
  }
  
  private void logFields(Map fields, String messageType)
  {
    Iterator fieldKeysIterator = fields.keySet().iterator();
    while (fieldKeysIterator.hasNext())
    {
      Object fieldKey = fieldKeysIterator.next();
      Object fieldValue = fields.get(fieldKey);
      
      //Logger.debug(messageType + " header: " + fieldKey + "=" + fieldValue);
    }
  }
  
  private void logRequestProperties()
  {
    try
    {
      //logFields(this.connection.getRequestProperties(), "Request");
    }
    catch (Exception e) {}
  }
  
  private void logResponseFields()
  {
    try
    {
      //logFields(this.connection.getHeaderFields(), "Response");
    }
    catch (Exception e) {}
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    if (this.connection.getResponseCode() == 200) {
      return this.connection.getInputStream();
    }
    return this.connection.getErrorStream();
  }
  
  public void evalResponseCode()
    throws ClientException
  {
    logRequestProperties();
    int code = getResponseCode();
    
    logResponseFields();
    if (code != 200) {
      throw new HttpException(code, getResponseMessage());
    }
  }
  
  public int getResponseCode()
    throws ClientException
  {
    int code = -1;
    try
    {
      code = this.connection.getResponseCode();
    }
    catch (SocketTimeoutException e)
    {
      throw new TimeOutException(e.getMessage(), e);
    }
    catch (IOException e)
    {
      throw new ClientException(e.getMessage(), e);
    }
    return code;
  }
  
  public String getResponseMessage()
    throws ClientException
  {
    try
    {
      return this.connection.getResponseMessage();
    }
    catch (SocketTimeoutException e)
    {
      throw new TimeOutException(e.getMessage(), e);
    }
    catch (IOException e)
    {
      throw new ClientException(e.getMessage(), e);
    }
  }
  
  public void disconnect()
  {
    //Logger.debug("disconnect");
    this.connection.disconnect();
  }
  
  public byte[] send(byte[] request)
    throws ClientException
  {
    writeRequest(request);
    
    evalResponseCode();
    
    return readResponse();
  }
  
  private void writeRequest(byte[] request)
    throws ClientException
  {
    OutputStream os = null;
    try
    {
      os = getOutputStream();
      os.write(request);
      os.flush();
    }
    catch (IOException e)
    {
      throw new ClientException(e.getMessage(), e);
    }
    finally
    {
        try {
          if(os != null)
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }
  
  private byte[] readResponse()
    throws ClientException
  {
    BufferedInputStream inBuffer = null;
    ByteArrayOutputStream outBuffer = null;
    try
    {
      byte[] buffer = new byte['Ȁ'];
      outBuffer = new ByteArrayOutputStream();
      inBuffer = new BufferedInputStream(getInputStream());
      
      int leidos = 0;
      while ((leidos = inBuffer.read(buffer)) != -1) {
        outBuffer.write(buffer, 0, leidos);
      }
      return outBuffer.toByteArray();
    }
    catch (SocketTimeoutException e)
    {
      throw new TimeOutException(e.getMessage(), e);
    }
    catch (IOException e)
    {
      throw new ClientException(e.getMessage(), e);
    }
    finally
    {
        try {
            outBuffer.close();
            inBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }
}
