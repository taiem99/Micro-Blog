package vn.techmaster.blogs.exception;

public class StorageException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 2184801129207366166L;
    public StorageException(String msg){
        super(msg);
    }
    public StorageException(String msg, Throwable cause){
        super(msg, cause);
    }
}
