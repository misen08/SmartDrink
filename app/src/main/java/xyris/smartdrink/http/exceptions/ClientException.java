package xyris.smartdrink.http.exceptions;

public class ClientException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  
  public ClientException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public ClientException(String message)
  {
    super(message);
  }
}
