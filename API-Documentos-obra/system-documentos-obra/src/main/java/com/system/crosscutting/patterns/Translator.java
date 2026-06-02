package com.system.crosscutting.patterns;

import com.system.crosscutting.exceptions.Main.EBusinessException;

@FunctionalInterface
public interface Translator<I, O> {
    O translate(final I input) throws EBusinessException;
}
