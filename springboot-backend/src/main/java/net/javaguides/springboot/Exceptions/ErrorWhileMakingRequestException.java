package net.javaguides.springboot.Exceptions;

public class ErrorWhileMakingRequestException  extends RuntimeException{

    public ErrorWhileMakingRequestException(String ErrorMessage){
        super(ErrorMessage);
    }

}
