package xyris.smartdrink.http.exceptions;

public class TimeOutException
  extends ClientException
{
  private static final long serialVersionUID = 1L;
  
  public TimeOutException(String message)
  {
    super(message);
  }
  
  public TimeOutException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
