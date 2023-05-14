package com.example.elearningmad


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest{
    @Test
    fun loginValidatorTest() {
        var email = "shan@gmail.com"
        var password = "123456"
        var result = Validator.loginValidateInput(email, password)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun registerValidatorTest() {
        var email = "shan@gmail.com"
        var password = "www"
        var result = Validator.loginValidateInput(email, password)
        assertThat(result).isEqualTo(true)
    }
}