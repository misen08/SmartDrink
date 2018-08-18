package xyris.smartdrink.http.exceptions;

public class HttpException
  extends ClientException
{
  private static final long serialVersionUID = 1L;
  private int httpCode;
  private String httpDescription;
  
  public HttpException(int httpCode, String httpDescription)
  {
    super(httpDescription);
    this.httpCode = httpCode;
    this.httpDescription = httpDescription;
  }
  
  public HttpException(int httpCode, String httpDescription, Throwable t)
  {
    super(httpDescription, t);
    this.httpCode = httpCode;
    this.httpDescription = httpDescription;
  }
  
  public int getHttpCode()
  {
    return this.httpCode;
  }
  
  public String getHttpDescription()
  {
    return this.httpDescription;
  }
}
