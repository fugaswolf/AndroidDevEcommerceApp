package com.ehb.androiddevapp;

import com.ehb.androiddevapp.domain.EmailValidator;

import org.junit.Test;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailValidatorTest {
    /**
     * Junit test case 1
     * Correct value : any proper email address (abc@gmail.com)
     * Input : abc@gmail.com
     * Expected : Pass
     * status : Pass
     */
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("abc@gmail.com"));
    }
    /**
     * Junit test case 2
     * Correct value : any proper email address (abc@gmail.com)
     * Input : name@gmail.co.uk
     * Expected : Pass
     * status : Pass
     */
    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@gmail.co.uk"));
    }
    /**
     * Junit test case 3
     * Correct value : any proper email address (abc@gmail.com)
     * Input : name@gmail
     * Expected : Pass
     * status : Pass
     */
    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@gmail"));
    }
    /**
     * Junit test case 4
     * Correct value : any proper email address (abc@gmail.com)
     * Input : name@gmail..com
     * Expected : Pass
     * status : Pass
     */
    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@gmail..com"));
    }
    /**
     * Junit test case 5
     * Correct value : any proper email address (abc@gmail.com)
     * Input : @gmail.com
     * Expected : Pass
     * status : Pass
     */
    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("@gmail.com"));
    }
    /**
     * Junit test case 6
     * Correct value : any proper email address (abc@gmail.com)
     * Input :
     * Expected : Pass
     * status : Pass
     */
    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""));
    }
    /**
     * Junit test case 7
     * Correct value : any proper email address (abc@gmail.com)
     * Input : null
     * Expected : Pass
     * status : Pass
     */
    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null));
    }
}
