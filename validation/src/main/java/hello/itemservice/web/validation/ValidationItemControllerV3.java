package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    // Bean Validation
//    @PostMapping("/add")
//    public String addItem(@Validated @ModelAttribute Item item, BindingResult br, RedirectAttributes redirectAttributes) {
//
//        objectValidator(item, br);
//
//        if (br.hasErrors()) {
//            log.info("errors={}", br);
//            return "validation/v3/addForm";
//        }
//
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v3/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItem2(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult br, RedirectAttributes redirectAttributes) {

        objectValidator(item, br);

        if (br.hasErrors()) {
            log.info("errors={}", br);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @Validated(UpdateCheck.class) @ModelAttribute Item item,
                       BindingResult br) {
        objectValidator(item, br);

        if (br.hasErrors()) {
            log.info("errors={}", br);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

    public void objectValidator(@ModelAttribute @Validated Item item, BindingResult br) {
        if (item.getQuantity() != null && item.getPrice() != null) {
            Long resultPrice = (long) (item.getQuantity() * item.getPrice());
            if (resultPrice < 10000) {
                br.reject("totalPriceMin",
                        new Object[]{itemValidator.formatting(10000l), itemValidator.formatting(resultPrice)}, null);
            }
        }
    }

}

