package com.labjava.skillguest.api.utils;

import com.labjava.skillguest.api.utils.exception.MyBadRequestException;
import com.labjava.skillguest.api.utils.exception.MyConflictException;
import com.labjava.skillguest.api.utils.exception.MyResourceNotFoundException;

public class RestPreconditions {

    private RestPreconditions() {
        throw new AssertionError();
    }

    public static <T> T checkNotNull(final T reference) {
        return checkNotNull(reference, null);
    }


    public static <T> T checkNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyResourceNotFoundException(message);
        }
        return reference;
    }

    public static <T> T checkRequestElementNotNull(final T reference) {
        return checkRequestElementNotNull(reference, null);
    }

    public static <T> T checkRequestElementNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyBadRequestException(message);
        }
        return reference;
    }

    public static void checkRequestState(final boolean expression) {
        checkRequestState(expression, null);
    }

    public static void checkRequestState(final boolean expression, final String message) {
        if (!expression) {
            throw new MyConflictException(message);
        }
    }

    public static void checkIfBadRequest(final boolean expression) {
        checkIfBadRequest(expression, null);
    }


    public static void checkIfBadRequest(final boolean expression, final String message) {
        if (!expression) {
            throw new MyBadRequestException(message);
        }
    }
}
