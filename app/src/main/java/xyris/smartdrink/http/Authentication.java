package xyris.smartdrink.http;

public class Authentication
{
  private String user;
  private String password;
  private AuthenticationType authenticationType;
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setUser(String user)
  {
    this.user = user;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public AuthenticationType getAuthenticationType()
  {
    return this.authenticationType;
  }
  
  public void setAuthenticationType(AuthenticationType authenticationType)
  {
    this.authenticationType = authenticationType;
  }
  
  public String toString()
  {
    return this.user + ":" + this.password;
  }
  
  public static final class AuthenticationType
  {
    public static final String TYPE_BASIC = "Basic";
    public static final String TYPE_USER = "User";
    private String type;
    
    private AuthenticationType(String type)
    {
      this.type = type;
    }
    
    public static AuthenticationType getBasic()
    {
      return new AuthenticationType("Basic");
    }
    
    public static AuthenticationType getUser()
    {
      return new AuthenticationType("User");
    }
    
    public String toString()
    {
      return this.type;
    }
  }
}
