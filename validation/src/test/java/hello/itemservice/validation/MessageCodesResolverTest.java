package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodeResolverObject() {
        // code - object - field
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String s : messageCodes) {
            System.out.println(s);
        }
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodeResolveField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for (String s : messageCodes) {
            System.out.println("messageCode = " + s);
        }

//        messageCode = required.item.itemName
//        messageCode = required.itemName
//        messageCode = required.java.lang.String
//        messageCode = required

        assertThat(messageCodes).containsExactly(
                "required.item.itemName"
                , "required.itemName"
                , "required.java.lang.String"
                , "required");
    }
}
