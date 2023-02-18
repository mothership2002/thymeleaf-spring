package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Stack;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    // 항상 modelAttribute 뒤에 bindingResult 작성해야함
//    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute Item item, BindingResult br,
//                            RedirectAttributes redirectAttributes,
//                            Errors errors) {
//
//
//        if (!StringUtils.hasText(item.getItemName())) {
//            br.addError(new FieldError("item", "itemName",  "상품명은 필수"));
//        }
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            br.addError(new FieldError("item", "price",  "가격은 1,000원 에서 1,000,000원 사이만 허용"));
//        }
//
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            br.addError(new FieldError("item", "quantity", "수량은 9999이하 만 허용"));
//        }
//
//        if (item.getQuantity() != null && item.getPrice() != null) {
//            int resultPrice = item.getQuantity() * item.getPrice();
//            if (resultPrice < 10000) {
//                br.addError(new ObjectError("item", "가격 * 수량이 10,000원 미만 입니다. (현재 값 = " + resultPrice + ")"));
//            }
//        }
//
//        log.info("item = {}", item.getPrice());
//        if (br.hasErrors()) {
//            log.info("errors={}", br);
//            return "validation/v2/addForm";
//        }
//
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

//        @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item, BindingResult br,
//                            RedirectAttributes redirectAttributes,
//                            Errors errors) {
//
//
//        if (!StringUtils.hasText(item.getItemName())) {
//            br.addError(new FieldError("item", "itemName",
//                    item.getItemName(), false, null, null, "상품명은 필수"));
//        }
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            br.addError(new FieldError("item", "price",
//                    item.getPrice(), false, null, null, "가격은 1,000원 에서 1,000,000원 사이만 허용"));
//        }
//
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            br.addError(new FieldError("item", "quantity",
//                    item.getQuantity(), false, null, null, "수량은 9999이하 만 허용"));
//        }
//
//        if (item.getQuantity() != null && item.getPrice() != null) {
//            int resultPrice = item.getQuantity() * item.getPrice();
//            if (resultPrice < 10000) {
//                br.addError(new ObjectError("item", null, null,
//                        "가격 * 수량이 10,000원 미만 입니다. (현재 값 = " + resultPrice + ")"));
//            }
//        }
//
//        if (br.hasErrors()) {
//            log.info("errors={}", br);
//            return "validation/v2/addForm";
//        }
//
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item, BindingResult br,
//                            RedirectAttributes redirectAttributes) {
//
//
//        if (!StringUtils.hasText(item.getItemName())) {
//            br.addError(new FieldError("item", "itemName",
//                    item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
//        }
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            br.addError(new FieldError("item", "price",
//                    item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{"1,000", "1,000,000"}, null));
//        }
//
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            br.addError(new FieldError("item", "quantity",
//                    item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{"9999"}, null));
//        }
//
//        if (item.getQuantity() != null && item.getPrice() != null) {
//            int resultPrice = item.getQuantity() * item.getPrice();
//            if (resultPrice < 10000) {
//                br.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{ "10,000" , formatting(resultPrice)},
//                        null));
//            }
//        }
//
//        if (br.hasErrors()) {
//            log.info("errors={}", br);
//            return "validation/v2/addForm";
//        }
//
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV4(@ModelAttribute Item item, BindingResult br,
//                            RedirectAttributes redirectAttributes) {
//
//
////        ValidationUtils.rejectIfEmptyOrWhitespace(br, "itemName", "required");
//
//        if (!StringUtils.hasText(item.getItemName())) {
//            br.rejectValue("itemName", "required");
//        }
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            br.rejectValue("price", "range",
//                    new Object[]{formatting(1000), formatting(1000000)}, null);
//        }
//
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            br.rejectValue("quantity", "max",
//                    new Object[]{formatting(9999)}, null);
//        }
//
//        if (item.getQuantity() != null && item.getPrice() != null) {
//            int resultPrice = item.getQuantity() * item.getPrice();
//            if (resultPrice < 10000) {
//                br.reject("totalPriceMin", new Object[]{ formatting(10000), formatting(resultPrice)}, null);
//            }
//        }
//
////        DefaultMessageCodesResolver 의 기본 메시지 생성 규칙
//
////        Object
////        객체 오류의 경우 다음 순서로 2가지 생성
////        1.: errorCode + "." + object
////        2.: errorCode
////        예) 오류 코드: required, object: item
////        1.: required.item
////        2.: required
//
////        field
////        필드 오류의 경우 다음 순서로 4가지 메시지 코드 생성
////        1.: errorCode + "." + object + "." + field
////        2.: errorCode + "." + field
////        3.: errorCode + "." + field TYPE
////        4.: errorCode
////        예) 오류 코드: typeMismatch, object name "user", field "age", field type: int
////        1. "typeMismatch.user.age"
////        2. "typeMismatch.age"
////        3. "typeMismatch.int"
////        4. "typeMismatch"
//
//        if (br.hasErrors()) {
//            log.info("errors={}", br);
//            return "validation/v2/addForm";
//        }
//
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult br, RedirectAttributes redirectAttributes) {

        if (itemValidator.supports(item.getClass())) {
            itemValidator.validate(item, br);
        }

        if (br.hasErrors()) {
            log.info("errors={}", br);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @ResponseBody
    @GetMapping("/test/{number}")
    public String testing(@PathVariable Integer number) {
        return itemValidator.formatting(number);
    }


}

