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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

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

    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult br,
                          RedirectAttributes redirectAttributes, Model model) {

        if (!StringUtils.hasText(item.getItemName())) {
            br.addError(new FieldError("item", "itemName",  "상품명은 필수"));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            br.addError(new FieldError("item", "price",  "가격은 1,000원 에서 1,000,000원 사이만 허용"));
        }

        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            br.addError(new FieldError("item", "quantity", "수량은 9999이하 만 허용"));
        }

        if (item.getQuantity() != null && item.getPrice() != null) {
            int resultPrice = item.getQuantity() * item.getPrice();
            if (resultPrice < 10000) {
                br.addError(new ObjectError("item", "가격 * 수량이 10,000원 미만 입니다. (현재 값 = " + resultPrice + ")"));
            }
        }

        log.info("item = {}", item.getPrice());
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

}
