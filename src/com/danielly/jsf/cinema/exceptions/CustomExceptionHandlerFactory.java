package com.danielly.jsf.cinema.exceptions;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * A factory for creating CustomExceptionHandler objects.
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    /** The exception handler factory. */
    private ExceptionHandlerFactory exceptionHandlerFactory;

    /**
     * Instantiates a new custom exception handler factory.
     */
    public CustomExceptionHandlerFactory() {
    }

    /**
     * Instantiates a new custom exception handler factory.
     *
     * @param exceptionHandlerFactory the exception handler factory
     */
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    /**
     * Gets the exception handler.
     *
     * @return the exception handler
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(exceptionHandlerFactory.getExceptionHandler());
    }
}