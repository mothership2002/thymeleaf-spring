package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Stack;

@Slf4j
@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        //        ValidationUtils.rejectIfEmptyOrWhitespace(br, "itemName", "required");

        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range",
                    new Object[]{formatting(1000), formatting(1000000)}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity", "max",
                    new Object[]{formatting(9999)}, null);
        }

        if (item.getQuantity() != null && item.getPrice() != null) {
            int resultPrice = item.getQuantity() * item.getPrice();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{ formatting(10000), formatting(resultPrice)}, null);
            }
        }

    }

    public String formatting(Integer number) {

        String[] numberString = String.valueOf(number).split("");
        Integer index = 1; // 자릿수

        Stack<String> stack = new Stack<>();

        for (int i = numberString.length - 1; i >= 0; i--) {

            if (index != 4) {
                stack.push(numberString[i]);
                index++;
            }
            else {
                stack.push(",");
                index = 1;
                i++;
            }

        }

        String result = "";
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }

}
