package vn.techmaster.blogs.exception;

public class UserException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = -7647645007697684108L;
    
    public UserException(String msg){
        super(msg);
    }
}
