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
                    new Object[]{formatting(1000l), formatting(1000000l)}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity", "max",
                    new Object[]{formatting(9999l)}, null);
        }

        if (item.getQuantity() != null && item.getPrice() != null) {
            Long resultPrice = (long) (item.getQuantity() * item.getPrice());
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{formatting(10000l), formatting(resultPrice)}, null);
            }
        }

    }

    public String formatting(Long number) {

//        1. 첫줄에 split 의미없는 코드임    실제로 의미없엇고
//        2. 2번째줄 Integer는 절대로 null이 나올 수 없으니 int로 변경   파서클래스
//        3. String은 불변객체라 += 사용하는거 비추, StringBuilder나 Buffer 사용    하단설명 참조

//        String            :  문자열 연산이 적고 멀티쓰레드 환경일 경우
//        StringBuffer      :  문자열 연산이 많고 멀티쓰레드 환경일 경우
//        StringBuilder     :  문자열 연산이 많고 단일쓰레드이거나 동기화를 고려하지 않아도 되는 경우


//        Stack<Character> stack = new Stack<>();
//        String string = String.valueOf(number);
//
//        for (int i = string.length() - 1; i >= 0; i--) {
//            stack.push(string.charAt(i));
//            if ((string.length() - i) % 3 == 0 && i != 0) {
//                stack.push(',');
//            }
//        }
//
//        StringBuffer result = new StringBuffer();
//
//        while (!stack.isEmpty()) {
//            result.append(stack.pop());
//        }
//
//        return result.toString();
        StringBuilder stringBuilder = new StringBuilder();

        String string = String.valueOf(number);

        for (int i = string.length() - 1; i >= 0; i--) {
            stringBuilder.append(string.charAt(i));
            if ((string.length() - i) % 3 == 0 && i != 0) {
                stringBuilder.append(',');
            }
        }

        return stringBuilder.reverse().toString();
    }

}
