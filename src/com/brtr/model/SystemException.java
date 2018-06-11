package com.brtr.model;


public class SystemException
    extends
    RuntimeException
{

        private final SystemErrorCode errorCode;


        public SystemException(
            SystemErrorCode ec,
            String message,
            Throwable cause)
        {
                super(message, cause);
                errorCode = ec;
        }


        public SystemException(
            SystemErrorCode ec,
            String message)
        {
                this(ec, message, null);
        }


        public SystemException(
            SystemErrorCode ec,
            Throwable cause)
        {
                this(ec, null, cause);
        }


        public SystemErrorCode getErrorCode()
        {
                return errorCode;
        }
}
